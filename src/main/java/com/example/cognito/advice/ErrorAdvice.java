package com.example.cognito.advice;

import com.example.cognito.exception.InviteUserException;
import com.example.cognito.exception.UserAlreadyExistsException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class ErrorAdvice {

    @ExceptionHandler(value = InviteUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Errors inviteUserException(final Exception e) {
        log.error("inviteUserException -> {}", e.getMessage());
        return Errors.UserInvitationFailed.setReason(e.getMessage());
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Errors userAlreadyExistsException(final Exception e) {
        log.error("userAlreadyExistsException -> {}", e.getMessage());
        return Errors.UserAlreadyExists.setReason(e.getMessage());
    }
}
