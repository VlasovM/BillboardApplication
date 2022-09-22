package com.billboardapplication.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class LoginResponse {

    private boolean is_successfully;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currentUser;

}
