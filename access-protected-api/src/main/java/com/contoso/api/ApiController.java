package com.contoso.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping(path = "/api")
public class ApiController {

    @Value("${azure.activeDirectory.client-object-id}")
    private String myAppRegistrationObjectId;

    // An anonymous API route that will reach out to Microsoft Graph to get App
    // registration information about itself.
    @GetMapping(value = "/application")
    public Mono<JsonNode> me(@RegisteredOAuth2AuthorizedClient("graph") OAuth2AuthorizedClient graphMsalClient) {
        // Use the provided Spring Security provided OAuth2 client that has been
        // populated with an access token using the client credential flow. This means
        // the API is contacting graph "as itself," not based on the caller. This access
        // token will have whatever permissions have been granted it in the API's App
        // registration in the Azure AD portal.
        //
        // This access token comes from the Spring Security token cache, and this line
        // results in the same access token being used on subsequent invocations of this
        // API. Nearing access token expiration, the access token will be refreshed by the
        // Spring Security framework.
        final String graphAccessToken = graphMsalClient.getAccessToken().getTokenValue();

        // Access Microsoft Graph using the access token acquired above. This
        // application is simply accessing its own App registration record in Azure AD.
        var jsonResultFromGraph = WebClient.create().get()
                .uri("https://graph.microsoft.com/v1.0/applications/" + myAppRegistrationObjectId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + graphAccessToken)
                .retrieve()
                .bodyToMono(JsonNode.class);

        return jsonResultFromGraph;
    }
}
