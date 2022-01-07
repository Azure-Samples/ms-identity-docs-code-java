<!-- Keeping yaml frontmatter commented out for now
---
# Metadata required by https://docs.microsoft.com/samples/browse/
# Metadata properties: https://review.docs.microsoft.com/help/contribute/samples/process/onboarding?branch=main#add-metadata-to-readme
languages:
- java
page_type: sample
name: "Java API written in Spring Boot that both calls Microsoft Graph as itself."
description: "This Java API and calls Microsoft Graph as itself using the Microsoft Authentication Library (MSAL) for Java. The code in this sample is used by one or more articles on docs.microsoft.com."
products:
- azure
- azure-active-directory
- ms-graph
urlFragment: ms-identity-docs-code-webapp-java
---
-->

# Java Spring Boot | web API | protected web API access (Microsoft Graph)  | Microsoft identity platform

<!-- Build badges here
![Build passing.](https://img.shields.io/badge/build-passing-brightgreen.svg) ![Code coverage.](https://img.shields.io/badge/coverage-100%25-brightgreen.svg) ![License.](https://img.shields.io/badge/license-MIT-green.svg)
-->

This sample demonstrates a Java Spring Boot web API that calls Microsoft Graph as itself, using the Microsoft Authentication Library (MSAL) for Java.

```console
$ curl http://localhost:8080/api/application
{
  "@odata.context": "https://graph.microsoft.com/v1.0/$metadata#applications/$entity",
  "id": "04487cab-91cf-46d8-b4ed-1a428388bdd2",
  "appId": "de5dcdc6-8dfe-4048-aff6-c615dea4ee3d",
  "displayName": "java-api",
  "description": null,
  "groupMembershipClaims": null,
  "identifierUris": ["api://de5dcdc6-8dfe-4048-aff6-c615dea4ee3d"],
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

- Azure Active Directory (Azure AD) tenant and the permissions or role required for managing app registrations in the tenant.
- Java 8+
- Maven

## Setup

### 1. Register the app

First, complete the steps in [Register an application with the Microsoft identity platform](https://docs.microsoft.com/azure/active-directory/develop/quickstart-register-app) to register the sample app.

Use these settings in your app registration.

| App registration <br/> setting | Value for this sample app                                            | Notes                                                                                       |
|:------------------------------:|:---------------------------------------------------------------------|:--------------------------------------------------------------------------------------------|
| **Name**                       | `java-api`                                                           | Suggested value for this sample. <br/> You can change the app name at any time.             |
| **Supported account types**    | **Accounts in this organizational directory only (Single tenant)**   | Suggested value for this sample.                                                            |
| **Platform type**              | _None_                                                               | No redirect URI required; don't select a platform.                                          |
| **Client secret**              | _**Value** of the client secret (not its ID)_                        | :warning: Record this value immediately! <br/> It's shown only _once_ (when you create it). |

> :information_source: **Bold text** in the tables above matches (or is similar to) a UI element in the Azure portal, while `code formatting` indicates a value you enter into a text box in the Azure portal.

### 2. Update code sample with app registration values

Open the [_application.yml_](src/main/resources/application.yml) file and modify the three Azure Active Directory configuration properties using the values from your [app's registration in the Azure portal](https://docs.microsoft.com/azure/active-directory/develop/quickstart-register-app).

```yaml
# 'Tenant ID' of your Azure AD instance - this value is a GUID
tenant-id: value-here

# 'Application (client) ID' of app registration in Azure portal - this value is a GUID
client-id: value-here

# Client secret 'Value' (not its ID) from 'Client secrets' in app registration in Azure portal
client-secret: value-here

# 'Object ID' of app registration in Azure portal - this value is a GUID
client-object-id: value-here
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

Open postman, curl, or similar and make an HTTP GET request to **http://localhost:8080/api/application**. If everything worked, the sample app should produce output similar to this:

```console
$ curl http://localhost:8080/api/application
{
  "@odata.context": "https://graph.microsoft.com/v1.0/$metadata#applications/$entity",
  "id": "04487cab-91cf-46d8-b4ed-1a428388bdd2",
  "appId": "de5dcdc6-8dfe-4048-aff6-c615dea4ee3d",
  "displayName": "java-api",
  "description": null,
  "groupMembershipClaims": null,
  "identifierUris": ["api://de5dcdc6-8dfe-4048-aff6-c615dea4ee3d"],
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

## About the code

This Java API uses the Spring Boot web framework. The app has a single, anonymous route. When that route is called, the application will make a Microsoft Graph request to get information about its own app registration. This is being done via the client credentials OAuth flow, meaning the application is using its own App registration as the credentials to generate the access token for Microsoft Graph. The results of the Microsoft Graph call are then returned as the results of the API call.

No specific Microsoft Graph permissions needed to be assigned to the app registration in the Azure AD portal, because app registrations can access their own records without additional permissions. To access other Microsoft Graph endpoints, the app registration for this API would require additional Microsoft Graph API permissions assigned to it.

## Reporting problems

### Sample app not working?

If you can't get the sample working, you've checked [Stack Overflow](http://stackoverflow.com/questions/tagged/msal), and you've already searched the issues in this sample's repository, open an issue report the problem.

1. Search the [GitHub issues](../issues) in the repository - your problem might already have been reported or have an answer.
1. Nothing similar? [Open an issue](../issues/new) that clearly explains the problem you're having running the sample app.

### All other issues

> :warning: WARNING: Any issue in this repository _not_ limited to running one of its sample apps will be closed without being addressed.

For all other requests, see [Support and help options for developers | Microsoft identity platform](https://docs.microsoft.com/azure/active-directory/develop/developer-support-help-options).

## Contributing

If you'd like to contribute to this sample, see [CONTRIBUTING.MD](/CONTRIBUTING.md).

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information, see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.