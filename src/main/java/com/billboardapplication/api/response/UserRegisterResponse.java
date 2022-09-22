package com.billboardapplication.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@Data
public class UserRegisterResponse {

    private boolean is_successfully;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

}
