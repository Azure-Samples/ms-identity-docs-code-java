package com.contoso.webapp;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;


@Controller
public class IndexController {

    private final RestTemplate restClient;

    public IndexController(RestTemplateBuilder restTemplateBuilder) {
        this.restClient = restTemplateBuilder.build();
    }

    @GetMapping(value = { "/" })
    public String indexPage(Model model) {
        return "index";
    }

    @GetMapping("/graph")
    public String graphPage(Model model, OAuth2AuthenticationToken authentication,
            @RegisteredOAuth2AuthorizedClient("graph") OAuth2AuthorizedClient msalGraphClient) {
        model.addAttribute("userName", authentication.getName());

        // Call to Microsoft Graph using the access_token found within the provided authorized client
        RequestEntity<Void> request = RequestEntity.get("https://graph.microsoft.com/v1.0/me")
                .header("Authorization", "Bearer " + msalGraphClient.getAccessToken().getTokenValue()).build();
        ResponseEntity<String> result = this.restClient.exchange(request, String.class);

        // Format the json result for display
        String responseJson = "";
        try {
            responseJson = new ObjectMapper().readTree(result.getBody()).toPrettyString();
        } catch (Exception e) {
            responseJson = e.getMessage();
        }

        model.addAttribute("jsonBody", responseJson);

        return "graph";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('APPROLE_admin')")
    public String adminPage(Model model, OAuth2AuthenticationToken authentication,
            @AuthenticationPrincipal OidcUser principal) {
        model.addAttribute("userName", authentication.getName());

        // Get the id_token's claims from the principal
        Map<String, Object> idTokenClaims = principal.getIdToken().getClaims();

        // Format the claims for display
        String responseJson = "";
        try {
            responseJson = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .writeValueAsString(idTokenClaims);
        } catch (Exception e) {
            responseJson = e.getMessage();
        }

        model.addAttribute("jsonBody", responseJson);

        return "admin";
    }
}
