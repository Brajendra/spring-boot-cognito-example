package com.example.cognito.services.impl;

import com.example.cognito.enums.UserType;
import com.example.cognito.exception.InviteUserException;
import com.example.cognito.exception.UserAlreadyExistsException;
import com.example.cognito.services.CognitoService;
import com.example.cognito.util.PasswordGenerator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
public class CognitoServiceImpl implements CognitoService {


    @Value("${aws.cognito.userpool.id}")
    private String USER_POOL_ID;

    @Autowired
    private CognitoIdentityProviderClient cognitoClient;


    public Mono<String> createUser(String emailId, UserType userType, String messageAction) {

        log.info("createUser -> (emailId) {} (userType) {}", emailId, userType);

        return Mono.create(monoSink -> {

            AdminCreateUserRequest adminCreateUserRequest =
                    AdminCreateUserRequest.builder()
                            .userPoolId(USER_POOL_ID)
                            .username(emailId)
                            .desiredDeliveryMediums(DeliveryMediumType.EMAIL)
                            .messageAction((String) messageAction)
                            .forceAliasCreation(true)
                            .temporaryPassword(PasswordGenerator.generatePasswordWithGuideline())
                            .userAttributes(buildAllAttributeType(emailId))
                            .build();

            try {
                AdminCreateUserResponse adminCreateUserResponse = cognitoClient.adminCreateUser(adminCreateUserRequest);
                addUserToGroupResult(adminCreateUserResponse.user().username(), userType).subscribe();
                monoSink.success(adminCreateUserResponse.user().username());
            } catch (UsernameExistsException e) {
                log.error("UsernameExistsException occured -> ", e);
                monoSink.error(new UserAlreadyExistsException("Found existing account in cognito with email " + emailId));
            } catch (Exception e) {
                log.error("Exception occured -> ", e);
                monoSink.error(new InviteUserException("Unable to invite account with (email) " + emailId + ", please try again later"));
            }

        });
    }

    private List<AttributeType> buildAllAttributeType(String emailId) {

        AttributeType attributeType2 = AttributeType.builder().name("email")
                .value(emailId)
                .build();
        AttributeType attributeType3 = AttributeType.builder().name("email_verified")
                .value(Boolean.toString(true))
                .build();

        return Arrays.asList(attributeType2, attributeType3);
    }


    public Mono<String> addUserToGroupResult(String username, UserType userType) {
        return Mono.fromSupplier(() -> {
            log.info("addUserToGroupResult -> (username) {} (userType) {}", username, userType);

            AdminAddUserToGroupRequest adminAddUserToGroupRequest =
                    AdminAddUserToGroupRequest.builder()
                            .groupName(userType.toString())
                            .userPoolId(USER_POOL_ID)
                            .username(username)
                            .build();

            return cognitoClient.adminAddUserToGroup(adminAddUserToGroupRequest).toString();
        });
    }
}
