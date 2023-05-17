package com.agl.authentication.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller(value = "/helloWorld")
public class HelloWorldController {

    @Get()
    public HttpResponse<String> helloWorld (Authentication authentication) {
        return HttpResponse.ok(authentication.getAttributes().get("sub").toString());
    }

}
