package com.example.cognito.services;

import com.example.cognito.enums.UserType;
import reactor.core.publisher.Mono;


public interface CognitoService {

    Mono<String> createUser(String emailId, UserType userType, String messageAction);
}
