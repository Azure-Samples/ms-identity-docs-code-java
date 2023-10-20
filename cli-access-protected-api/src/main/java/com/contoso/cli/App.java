package com.contoso.cli;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;

/**
 * This application represents a confidential client application that is going
 * to be calling Microsoft Graph as itself. This is a confidential client in
 * that the application will need its own identity and security token to perform
 * the work, and it is not doing this on behalf of an enterprise user.
 * 
 * An example of this might be a nightly job that runs a query against a
 * database, and based on the results sends an email to multiple people in the
 * organization.
 */
public class App {

    public static void main(String[] args) throws IOException {

        // All of your Microsoft Entra ID configurations are stored in the application.properties
        // file. Load those up for easy access.
        final Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));

        // This scenario uses a confidential client application MSAL client. The
        // lifecycle of this client should be scoped to the application's lifecycle
        // as it contains a token and metadata cache that will prevent unnecessary
        // requests to Microsoft Entra ID on subsequent usage.
        final ConfidentialClientApplication msalClient = ConfidentialClientApplication.builder(
                properties.getProperty("client-id"),
                ClientCredentialFactory.createFromSecret(properties.getProperty("client-secret")))
                .authority(properties.getProperty("authority"))
                .build();

        // Acquire a token from Microsoft Entra ID for this client to access Microsoft Graph based
        // on the permissions granted this application in its Microsoft Entra ID App registration.
        // The client credential flow will automatically attempt to use or renew any cached
        // tokens, without the need to call acquireTokenSilently first.
        final ClientCredentialParameters parameters = ClientCredentialParameters
                .builder(Collections.singleton("https://graph.microsoft.com/.default"))
                .build();
        final CompletableFuture<IAuthenticationResult> authResult = msalClient.acquireToken(parameters);
        final IAuthenticationResult tokenResult = authResult.join();
        final String graphAccessToken = tokenResult.accessToken();

        // Access Microsoft Graph using the access token acquired above. This
        // application is simply accessing its own App registration record in Microsoft Entra ID.
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://graph.microsoft.com/v1.0/applications/" + properties.getProperty("client-object-id")))
                .header("Authorization", "Bearer " + graphAccessToken)
                .GET()
                .build();

        final String graphJson = HttpClient.newHttpClient()
                .sendAsync(request, BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();

        // Output the results from the Microsoft Graph call to the console, formatted
        // to be more readable.
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode resultsAsJson = mapper.readTree(graphJson);
        System.out.println("Graph API call result: \n"
                + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultsAsJson));

        System.exit(0);
    }
}
