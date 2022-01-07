<!-- Keeping yaml frontmatter commented out for now
---
# Metadata required by https://docs.microsoft.com/samples/browse/
# Metadata properties: https://review.docs.microsoft.com/help/contribute/samples/process/onboarding?branch=main#add-metadata-to-readme
languages:
- java
page_type: sample
name: "Java web API written in Spring Boot that calls Microsoft Graph as itself."

description: "This Java API and calls Microsoft Graph as itself using the Microsoft Authentication Library (MSAL) for Java, msal4j. The code in this sample is used by one or more articles on docs.microsoft.com."

products:
- azure
- azure-active-directory
- ms-graph
urlFragment: ms-identity-docs-code-webapi-java

---
-->

# Java Spring Boot | web API | protected web API access (Microsoft Graph)  | Microsoft identity platform

<!-- Build badges here
![Build passing.](https://img.shields.io/badge/build-passing-brightgreen.svg) ![Code coverage.](https://img.shields.io/badge/coverage-100%25-brightgreen.svg) ![License.](https://img.shields.io/badge/license-MIT-green.svg)
-->

This Java Spring Boot web API uses the Microsoft Authentication Library (MSAL) for Java to acquire an access token for Microsoft Graph, using its own identity.

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

First, complete the steps in [Configure an application to expose a web API](https://docs.microsoft.com/azure/active-directory/develop/quickstart-configure-app-expose-web-apis) to register the sample API.

Use these settings in your app registration.

| App registration <br/> setting | Value for this sample app                                            | Notes                                                                                       |
|:------------------------------:|:---------------------------------------------------------------------|:--------------------------------------------------------------------------------------------|
| **Name**                       | `java-api`                                                           | Suggested value for this sample. <br/> You can change the app name at any time.             |
| **Supported account types**    | **Accounts in this organizational directory only (Single tenant)**   | Suggested value for this sample.                                                            |
| **Platform type**              | _None_                                                               | No redirect URI required; don't select a platform.                                          |
| **Client secret**              | _**Value** of the client secret (not its ID)_                        | :warning: Record this value immediately! <br/> It's shown only _once_ (when you create it). |

> :information_source: **Bold text** represents a value you select in the Azure portal, while `code formatting` indicates text you enter in the Azure portal.

### 2. Update code sample with app registration values

In [_application.yml_](src/main/resources/application.yml), update the Azure AD property values with those from your [app's registration in the Azure portal](https://docs.microsoft.com/azure/active-directory/develop/quickstart-configure-app-expose-web-apis).

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

Using Postman, curl, or a similar application, issue an HTTP GET request to *http://localhost:8080/api/application*.

For example, if you use curl and everything worked, the sample you should receive a response from the API similar to this:

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
  ...
}
```

## About the code

This Java web API uses the Spring Boot web framework and has a single route that supports anonymous access. When its anonymous route is called, the API requests its own application object from the Microsoft Graph API.

The web API uses MSAL for Java to get an access token for Microsoft Graph by using the OAuth 2.0 client credentials flow. That is, this web API (a confidential client) uses the credentials in its app registration (and thus its own identity) when requesting the access token for Microsoft Graph. If its call to the Microsoft Graph API was successful, the Java web API then returns the results to its anonymous caller.

The web API requires no specific Microsoft Graph API permissions because applications registered with Azure Azure AD always have permission to access their own record, or *object*, in Microsoft Graph. To access other Microsoft Graph API endpoints, however, permissions for those endpoints must be added to the web API's registration in Azure AD.

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
