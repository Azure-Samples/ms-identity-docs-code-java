package com.contoso.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class ApiController {

    // This annotation will trigger MSAL for Java to validate the JWT provided
    // and also will require that it contains a scope claim of "Greeting.Read"
    // An invalid or missing JWT token will result in a 401, a token without
    // sufficent scope will result in a 403.
    // The SCOPE_ prefix is a Spring Boot + Microsoft Entra package implementation
    // detail. There is also a ROLE_ prefix.
    @GetMapping(value = "/hello", produces = "text/plain")
    @PreAuthorize("hasAuthority('SCOPE_Greeting.Read')")
    public String hello() {
        return "Hello, world. You were able to access this because you provided a valid access token with the Greeting.Read scope as a claim.";
    }
}
