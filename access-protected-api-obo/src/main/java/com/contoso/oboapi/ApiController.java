package com.contoso.oboapi;

import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping(path = "/")
public class ApiController {

    // This annotation will trigger MSAL for Java to validate the JWT provided
    // and also will require that it contains a scope claim of "user_impersonation"
    // An invalid or missing JWT token will result in a 401, a token without
    // sufficent scope will result in a 403.
    @GetMapping(value = "/me")
    @PreAuthorize("hasAuthority('SCOPE_user_impersonation')")
    public Mono<JsonNode> me(@RegisteredOAuth2AuthorizedClient("graph") OAuth2AuthorizedClient graphMsalClient) {
        // Use the provided Spring Security provided OAuth2 client that has been
        // populated with an access token using the on-behalf-of flow, which
        // originated with the access token provided to this route with the client's
        // http request.
        final String graphAccessToken = graphMsalClient.getAccessToken().getTokenValue();

        // Access Microsoft Graph using the access token acquired above. This
        // application is simply accessing its own App registration record in Azure AD.
        final WebClient webClient = WebClient.create();

        var jsonResultFromGraph = webClient.get()
                .uri("https://graph.microsoft.com/v1.0/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + graphAccessToken)
                .retrieve()
                .bodyToMono(JsonNode.class);

        return jsonResultFromGraph;
    }
}
