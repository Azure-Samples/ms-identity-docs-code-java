<!-- Keeping yaml frontmatter commented out for now
---
# Metadata required by https://docs.microsoft.com/samples/browse/
# Metadata properties: https://review.docs.microsoft.com/help/contribute/samples/process/onboarding?branch=main#add-metadata-to-readme
languages:
- java
page_type: sample
name: "Java console application written that accesses Microsoft Graph as itself"
description: "This Java console application contacts Microsoft Graph as itself. The code in this sample is used by one or more articles on docs.microsoft.com."
products:
- azure
- azure-active-directory
- ms-graph
urlFragment: ms-identity-docs-code-webapp-java
---
-->

# Java | console app | protected web API access (Microsoft Graph) | Microsoft identity platform

<!-- Build badges here
![Build passing.](https://img.shields.io/badge/build-passing-brightgreen.svg) ![Code coverage.](https://img.shields.io/badge/coverage-100%25-brightgreen.svg) ![License.](https://img.shields.io/badge/license-MIT-green.svg)
-->

This sample demonstrates a Java console application that accesses Microsoft Graph as itself by using the Microsoft Authentication Library (MSAL) for Java.

![A screenshot showing the output of executing the command above, which is JSON from a Graph API call.](./output.png)

> :page_with_curl: This sample application backs one or more technical articles on docs.microsoft.com. <!-- TODO: Link to first tutorial in series when published. -->

## Prerequisites

- Azure Active Directory (Azure AD) tenant and the permissions or role required for managing app registrations in the tenant.
- Java 11+
- Maven

## Setup

### 1. Register the app

First, complete the steps in [Register an application with the Microsoft identity platform](https://docs.microsoft.com/azure/active-directory/develop/quickstart-register-app) to register the sample app.

Use these settings in your app registration.

| App registration <br/> setting | Value for this sample app                                                    | Notes                                                                                              |
|:------------------------------:|:-----------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------|
| **Name**                       | `java-cli`                                                                   | Suggested value for this sample. <br/> You can change the app name at any time.                    |
| **Supported account types**    | **Accounts in this organizational directory only (Single tenant)**           | Suggested value for this sample.                                                                   |
| **Platform type**              | _None_                                                                       | Required value for this sample. |
| **Client secret**              | _**Value** of the client secret (not its ID)_                                | :warning: Record this value immediately! <br/> It's shown only _once_ (when you create it).        |

> :information_source: **Bold text** in the tables above matches (or is similar to) a UI element in the Azure portal, while `code formatting` indicates a value you enter into a text box in the Azure portal.

### 2. Update code sample with app registration values

Open the [_application.properties_](src/main/resources/application.properties) file and modify the four Azure Active Directory configuration properties using the values from your [app's registration in the Azure portal](https://docs.microsoft.com/azure/active-directory/develop/quickstart-register-app).

```ini
# Typically https://login.microsoftonline.com/tenant-id-or-primary-domain/
authority=value-here

# 'Application (client) ID' of app registration in Azure portal - this value is a GUID
client-id=value-here

# 'Object ID' of app registration in Azure portal - this value is a GUID
client-object-id=value-here

# Client secret 'Value' (not its ID) from 'Client secrets' in app registration in Azure portal
client-secret=value-here
```

### 3. Install package(s) and compile application

To install the MSAL library:

```bash
mvn install
```

## Run the application

```bash
mvn exec:java -Dexec.mainClass=com.contoso.cli.App
```

If everything worked, the sample app should produce output similar to this:

![A screenshot showing the output of executing the command above, which is JSON from a Graph API call.](./output.png)

## About the code

This Java application is a console application that instantiates an MSAL client, uses it to retrieve an access token for Microsoft Graph, presenting its own credentials. It then uses the access token to call Microsoft graph and return the results of the API call.

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