package com.agl.authentication.controller;

import com.agl.authentication.service.User;
import com.agl.authentication.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;

@Controller(value = "/authentication")
@Secured(SecurityRule.IS_ANONYMOUS)
public class AuthenticationController {

    @Inject
    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @Post("/register")
    public HttpResponse<?> register(@Body RegisterRequest registerRequest) {
        userService.register(registerRequest.getUsername(), registerRequest.getPassword());
        return HttpResponse.noContent();
    }
}
