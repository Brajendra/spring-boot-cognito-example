package com.example.cognito.advice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public enum Errors implements Serializable {

    UserAlreadyExists(1000, "User Already Exists"),
    UserInvitationFailed(1001, "User Invitation Failed");

    private int code;
    private String message;
    private String reason;

    Errors(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Errors setReason(String reason) {
        this.reason = reason;
        return this;
    }

}
