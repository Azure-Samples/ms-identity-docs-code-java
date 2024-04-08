---
languages:
- java
page_type: sample
name: Java console application written that accesses Microsoft Graph as itself
description: This Java console application contacts Microsoft Graph as itself. The code in this sample is used by one or more articles on learn.microsoft.com.
products:
- azure
- azure-active-directory
- ms-graph
urlFragment: ms-identity-docs-code-cli-access-protected-api-java
---

# Java | console app | protected web API access (Microsoft Graph) | Microsoft identity platform
<!-- SAMPLE ID: DOCS-CODE-011 -->
<!-- Build badges here
![Build passing.](https://img.shields.io/badge/build-passing-brightgreen.svg) ![Code coverage.](https://img.shields.io/badge/coverage-100%25-brightgreen.svg) ![License.](https://img.shields.io/badge/license-MIT-green.svg)
-->

This sample demonstrates a Java console application that accesses Microsoft Graph as itself by using the Microsoft Authentication Library (MSAL) for Java.

```console
user@console:~$ mvn exec:java -Dexec.mainClass=com.contoso.cli.App
Graph API call result:
{
  "@odata.context": "https://graph.microsoft.com/v1.0/$metadata#applications/$entity",
  "id": 00aa00aa-bb11-cc22-dd33-44ee44ee44ee",
  "deletedDateTime": null,
  "appId": "00001111-aaaa-2222-bbbb-3333cccc4444",
  "applicationTemplateId": null,
  "disabledByMicrosoftStatus": null,
  "createdDateTime": "2021-11-11T20:57:247",
  "displayName": "java-cli",
  "description": null,
  "groupMembershipClaims": null,
  "identifierUris": [],
  "isDeviceOnlyAuthSupported": null,
  "isFallbackPublicClient": null,
  "notes": null,
  "publisherDomain": "onmicrosoft.com",
  "signInAudience": "Azure ADMyOrg",
  "tags": [],
  "tokenEncryptionKeyId": null,
  "defaultRedirectUri": null,
  "certification": null,
  "optionalclaims": null,
  "addins": [],
  "api": {
    "acceptMappedclaims": null,
    "knownClientApplications": [],
    "requestedAccessTokenVersion": null,
    "oauth2PermissionScopes": [],
    "preAuthorizedApplications": []
  },
  "appRoles": [],
  …
}
```

> :page_with_curl: This sample application backs one or more technical articles on learn.microsoft.com. <!-- TODO: Link to first tutorial in series when published. -->

## Prerequisites

- A Microsoft Entra tenant and the permissions or role required for managing app registrations in the tenant.
- Java 11+
- Maven

## Setup

### 1. Register the app

First, complete the steps in [Register an application with the Microsoft identity platform](https://learn.microsoft.com/azure/active-directory/develop/quickstart-register-app) to register the sample app.

Use these settings in your app registration.

| App registration <br/> setting | Value for this sample app                                              | Notes                                                                                          |
|:------------------------------:|:-----------------------------------------------------------------------|:-----------------------------------------------------------------------------------------------|
| **Name**                       | `java-cli`                                                             | Suggested value for this sample. <br/> You can change the app name at any time.                |
| **Supported account types**    | **Accounts in this organizational directory only (Single tenant)**     | Suggested value for this sample.                                                               |
| **Platform type**              | _None_                                                                 | No redirect URI required; don't select a platform.                                             |
| **Client secret**              | _**Value** of the client secret (not its ID)_                          | :warning: Record this value immediately! <br/> It's shown only _once_ (when you create it).    |

> :information_source: **Bold text** in the tables above matches (or is similar to) a UI element in the Microsoft Entra admin center, while `code formatting` indicates a value you enter into a text box in the Microsoft Entra admin center.

### 2. Update code sample with app registration values

Open the [_application.properties_](src/main/resources/application.properties) file and modify the four Microsoft Entra configuration properties using the values from your [app's registration in the Microsoft Entra admin center](https://learn.microsoft.com/azure/active-directory/develop/quickstart-register-app).

```ini
# Typically https://login.microsoftonline.com/tenant-id-or-primary-domain/
authority=https://login.microsoftonline.com/Enter_the_Tenant_ID_Here

# 'Application (client) ID' of app registration in the Microsoft Entra admin center - this value is a GUID
client-id=Enter_the_Application_Id_Here

# 'Object ID' of app registration in Microsoft Entra admin center - this value is a GUID. This is used for the Graph API call, not for MSAL authentication.
client-object-id=Enter_the_Client_Object_Id_Here

# Client secret 'Value' (not its ID) from 'Client secrets' in app registration in the Microsoft Entra admin center
client-secret=Enter_the_Client_Secret_Value_Here
```

### 3. Install package(s) and compile application

To install the MSAL library:

```bash
mvn install
```

## Run the application

```bash
mvn exec:java -D exec.mainClass=com.contoso.cli.App
```

If everything worked, the sample app should produce output similar to this:

```console
Graph API call result:
{
  "@odata.context": "https://graph.microsoft.com/v1.0/$metadata#applications/$entity",
  "id": "00aa00aa-bb11-cc22-dd33-44ee44ee44ee",
  "deletedDateTime": null,
  "appId": "00001111-aaaa-2222-bbbb-3333cccc4444",
  "applicationTemplateId": null,
  "disabledByMicrosoftStatus": null,
  "createdDateTime": "2021-11-11T20:57:247",
  "displayName": "java-cli",
  "description": null,
  "groupMembershipClaims": null,
  "identifierUris": [],
  "isDeviceOnlyAuthSupported": null,
  "isFallbackPublicClient": null,
  "notes": null,
  "publisherDomain": "onmicrosoft.com",
  "signInAudience": "Azure ADMyOrg",
  "tags": [],
  "tokenEncryptionKeyId": null,
  "defaultRedirectUri": null,
  "certification": null,
  "optionalclaims": null,
  "addins": [],
  "api": {
    "acceptMappedclaims": null,
    "knownClientApplications": [],
    "requestedAccessTokenVersion": null,
    "oauth2PermissionScopes": [],
    "preAuthorizedApplications": []
  },
  "appRoles": [],
  …
}
```

## About the code

This Java console application instantiates an MSAL client and then uses it to retrieve an access token for Microsoft Graph. The application will make a Microsoft Graph request to get information about its own app registration. This is being done via the client credentials OAuth flow, meaning the application is using its own App registration as the credentials to generate the access token for Microsoft Graph. The results of the Microsoft Graph call are then returned as the results of the API call.

No specific Microsoft Graph permissions needed to be assigned to the app registration in the Microsoft Entra admin center, because app registrations can access their own records without additional permissions. To access other Microsoft Graph endpoints, the app registration for this API would require additional Microsoft Graph API permissions assigned to it.

## Reporting problems

### Sample app not working?

If you can't get the sample working, you've checked [Stack Overflow](http://stackoverflow.com/questions/tagged/msal), and you've already searched the issues in this sample's repository, open an issue report the problem.

1. Search the [GitHub issues](../issues) in the repository - your problem might already have been reported or have an answer.
1. Nothing similar? [Open an issue](../issues/new) that clearly explains the problem you're having running the sample app.

### All other issues

> :warning: WARNING: Any issue in this repository _not_ limited to running one of its sample apps will be closed without being addressed.

For all other requests, see [Support and help options for developers | Microsoft identity platform](https://learn.microsoft.com/azure/active-directory/develop/developer-support-help-options).

## Contributing

If you'd like to contribute to this sample, see [CONTRIBUTING.MD](/CONTRIBUTING.md).

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information, see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.
