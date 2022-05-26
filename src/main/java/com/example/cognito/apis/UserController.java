package com.example.cognito.apis;

import com.example.cognito.models.Response;
import com.example.cognito.models.User;
import com.example.cognito.services.CognitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CognitoService cognitoService;

    @PostMapping("/signup")
    public Mono<Response> createUser(@RequestBody User user) {

        return cognitoService.createUser(user.getEmail(), user.getType(), null)
                .map(data -> Response.builder().data(data).build());
    }


    @GetMapping
    public Mono<Response> get(Principal user) {

        return Mono.just(Response.builder().data(user.getName()).build());
    }

    @GetMapping("/adminType")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Mono<Response> getAdminUser(Principal user) {

        return Mono.just(Response.builder().data("User with Admin Type").build());
    }

    @GetMapping("/userType")
    @PreAuthorize("hasAuthority('USER')")
    public Mono<Response> getUser(Principal user) {
        return Mono.just(Response.builder().data("User With User Type").build());
    }

    @GetMapping("/userOrAdminType")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Mono<Response> getUserorAdmin(Principal user) {
        return Mono.just(Response.builder().data("User either With User Type or Admin Type").build());
    }
}
