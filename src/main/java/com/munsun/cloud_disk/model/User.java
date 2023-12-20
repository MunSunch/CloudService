package com.munsun.cloud_disk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private String login;
    private String password;
    private Role role;
}
