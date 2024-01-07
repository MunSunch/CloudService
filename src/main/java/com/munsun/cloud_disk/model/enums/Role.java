package com.munsun.cloud_disk.model.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of("read")),
    ADMIN(Set.of("read", "write"));

    private Set<String> permissions;

    Role(Set<String> permissions) {
        this.permissions = permissions;
    }

    public Collection<GrantedAuthority> getPermissions() {
        return permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
