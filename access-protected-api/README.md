---
# Metadata required by https://learn.microsoft.com/samples/browse/
# Metadata properties: https://review.learn.microsoft.com/help/contribute/samples/process/onboarding?branch=main#add-metadata-to-readme
languages:
- java
page_type: sample
name: Java web API written in Spring Boot that calls Microsoft Graph as itself.

description: This Java API and calls Microsoft Graph as itself using the Microsoft Authentication Library (MSAL) for Java, msal4j. The code in this sample is used by one or more articles on learn.microsoft.com.

products:
- azure
- azure-active-directory
- ms-graph
urlFragment: ms-identity-docs-code-access-protected-webapi-java
---


<!-- SAMPLE ID: DOCS-CODE-008 -->
# Java Spring Boot | web API | web API that access a protected web API (Microsoft Graph)  | Microsoft identity platform

<!-- Build badges here
![Build passing.](https://img.shields.io/badge/build-passing-brightgreen.svg) ![Code coverage.](https://img.shields.io/badge/coverage-100%25-brightgreen.svg) ![License.](https://img.shields.io/badge/license-MIT-green.svg)
-->

This Java Spring Boot web API uses the Microsoft Authentication Library (MSAL) for Java to acquire an access token for Microsoft Graph, using its own identity.

```console
$ curl http://localhost:8080/api/application
{
  "@odata.context": "https://graph.microsoft.com/v1.0/$metadata#applications/$entity",
  "id": "00aa00aa-bb11-cc22-dd33-44ee44ee44ee",
  "appId": "00001111-aaaa-2222-bbbb-3333cccc4444",
  "displayName": "java-api",
  "description": null,
  "groupMembershipClaims": null,
  "identifierUris": ["api://00001111-aaaa-2222-bbbb-3333cccc4444"],
  "isDeviceOnlyAuthSupported": null,
  "isFallbackPublicClient": null,
  "notes": null,
  "publisherDomain": "contoso.onmicrosoft.com",
  "signInAudience": "AzureADMyOrg",
  "tags": [],
  "tokenEncryptionKeyId": null,
  "defaultRedirectUri": null,
  "certification": null,
  "optionalClaims": null,
  …
}
```

<!-- TODO: Link to first tutorial in series when published. -->

## Prerequisites

- A Microsoft Entra tenant and the permissions or role required for managing app registrations in the tenant.
- Java 11+
- Maven

## Setup

### 1. Register the app

First, complete the steps in [Configure an application to expose a web API](https://learn.microsoft.com/azure/active-directory/develop/quickstart-configure-app-expose-web-apis) to register the sample API.

Use these settings in your app registration.

| App registration <br/> setting | Value for this sample app                                            | Notes                                                                                       |
|:------------------------------:|:---------------------------------------------------------------------|:--------------------------------------------------------------------------------------------|
| **Name**                       | `java-api`                                                           | Suggested value for this sample. <br/> You can change the app name at any time.             |
| **Supported account types**    | **Accounts in this organizational directory only (Single tenant)**   | Suggested value for this sample.                                                            |
| **Platform type**              | _None_                                                               | No redirect URI required; don't select a platform.                                          |
| **Client secret**              | _**Value** of the client secret (not its ID)_                        | :warning: Record this value immediately! <br/> It's shown only _once_ (when you create it). |

> :information_source: **Bold text** represents a value you select in the Microsoft Entra admin center, while `code formatting` indicates text you enter in the Microsoft Entra admin center.

### 2. Update code sample with app registration values

In [_application.yml_](src/main/resources/application.yml), update the Microsoft Entra property values with those from your [app's registration in the Microsoft Entra admin center](https://learn.microsoft.com/azure/active-directory/develop/quickstart-configure-app-expose-web-apis).

```yaml
# 'Tenant ID' of your Microsoft Entra instance - this value is a GUID
tenant-id: Enter_the_Tenant_ID_Here

# 'Application (client) ID' of app registration in the Microsoft Entra admin center - this value is a GUID
client-id: Enter_the_Application_Id_Here

# Client secret 'Value' (not its ID) from 'Client secrets' in app registration in Microsoft Entra admin center
client-secret: Enter_the_Client_Secret_Value_Here

# 'Object ID' of app registration in Microsoft Entra admin center - this value is a GUID
client-object-id: Enter_the_Client_Object_ID_Here
```

### 3. Install package(s) and compile application

To install Spring Boot and MSAL libraries:

```bash
mvn install
```

## Run the application

```bash
mvn spring-boot:run
```

## Access the API

Using Postman, curl, or a similar application, issue an HTTP GET request to *http://localhost:8080/api/application*.

For example, if you use curl and everything worked, the sample you should receive a response from the API similar to this:

```console
$ curl http://localhost:8080/api/application
{
  "@odata.context": "https://graph.microsoft.com/v1.0/$metadata#applications/$entity",
  "id": "00aa00aa-bb11-cc22-dd33-44ee44ee44ee",
  "appId": "00001111-aaaa-2222-bbbb-3333cccc4444",
  "displayName": "java-api",
  "description": null,
  "groupMembershipClaims": null,
  "identifierUris": ["api://00001111-aaaa-2222-bbbb-3333cccc4444"],
  "isDeviceOnlyAuthSupported": null,
  "isFallbackPublicClient": null,
  "notes": null,
  "publisherDomain": "contoso.onmicrosoft.com",
  "signInAudience": "AzureADMyOrg",
  "tags": [],
  "tokenEncryptionKeyId": null,
  "defaultRedirectUri": null,
  "certification": null,
  "optionalClaims": null,
  ...
}
```

## About the code

This Java web API uses the Spring Boot web framework and has a single route that supports anonymous access. When its anonymous route is called, the API requests its own application object from the Microsoft Graph API.

The web API uses MSAL for Java to get an access token for Microsoft Graph by using the OAuth 2.0 client credentials flow. That is, this web API (a confidential client) uses the credentials in its app registration (and thus its own identity) when requesting the access token for Microsoft Graph. If its call to the Microsoft Graph API was successful, the Java web API then returns the results to its anonymous caller.

The web API requires no specific Microsoft Graph API permissions because applications registered with Microsoft Entra ID always have permission to access their own record, or *object*, in Microsoft Graph. To access other Microsoft Graph API endpoints, however, permissions for those endpoints must be added to the web API's registration in Microsoft Entra.

## Reporting problems

### Sample app not working?

If you can't get the sample working, you've checked [Stack Overflow](https://stackoverflow.com/questions/tagged/msal), and you've already searched the issues in this sample's repository, open an issue report the problem.

1. Search the [GitHub issues](../issues) in the repository - your problem might already have been reported or have an answer.
1. Nothing similar? [Open an issue](../issues/new) that clearly explains the problem you're having running the sample app.

### All other issues

> :warning: WARNING: Any issue in this repository _not_ limited to running one of its sample apps will be closed without being addressed.

For all other requests, see [Support and help options for developers | Microsoft identity platform](https://learn.microsoft.com/azure/active-directory/develop/developer-support-help-options).

## Contributing

If you'd like to contribute to this sample, see [CONTRIBUTING.MD](/CONTRIBUTING.md).

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information, see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.
