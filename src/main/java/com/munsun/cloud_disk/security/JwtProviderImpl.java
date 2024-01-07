package com.munsun.cloud_disk.security;

import com.munsun.cloud_disk.dto.request.LoginPasswordDtoIn;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Setter
@AllArgsConstructor
@Slf4j
@Service
public class JwtProviderImpl implements JwtProvider {
    @Value("${security.jwt.access.token}")
    private String secretKey;
    @Value("${security.jwt.access.token.expiration}")
    private Long secretKeyExpiration;
    @Value("${security.jwt.access.token.header}")
    private String secretHeader;

    private final MyUserDetailsService userDetailsService;

    @Autowired
    public JwtProviderImpl(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean validateAccessToken(String token) {
        log.info("validate token = {}", token);
        try{
            token = preparedToken(token);
            Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey)))
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired = {}", token);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt = {}", token);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt = {}", token);
        } catch (SignatureException sEx) {
            log.error("Invalid signature = {}", token);
        }
        return false;
    }

    private String preparedToken(String token) {
        return token.substring("Bearer ".length());
    }

    @Override
    public String generateAccessToken(LoginPasswordDtoIn dto) {
        log.info("generate new token");
        Date now = new Date();
        Date expiration = new Date(now.getTime() + secretKeyExpiration*1000);
        return Jwts.builder()
                .subject(dto.login())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey)))
                .compact();
    }

    public String getSecretHeader() {
        return secretHeader;
    }

    public Authentication getAuthentification(String token) {
        log.info("get authentication from token={}", token);
        token = preparedToken(token);
        var userLogin = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey)))
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
        var user = userDetailsService.loadUserByUsername(userLogin);
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

    public String resolveHeader(HttpServletRequest servletRequest) {
        log.info("Retrieving a token from request's");
        return servletRequest.getHeader(secretHeader);
    }
}
