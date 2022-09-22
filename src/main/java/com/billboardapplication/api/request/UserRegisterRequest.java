package com.billboardapplication.api.request;

import lombok.Data;

@Data
public class UserRegisterRequest {

    private String name;

    private String email;

    private String password;

    private String role;

}
