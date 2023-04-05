<!-- Keeping yaml frontmatter commented out for now
---
# Metadata required by https://docs.microsoft.com/samples/browse/
# Metadata properties: https://review.docs.microsoft.com/help/contribute/samples/process/onboarding?branch=main#add-metadata-to-readme
languages:
- Java
  page_type: sample
  name: "Android application written in Java that enables a user to sign in and make a request to Microsoft Graph"
  description: "This Android application written in Java enables a user to sign in and make a request to Microsoft Graph. The code in this sample is used by one or more articles on docs.microsoft.com."
  products:
- azure
- azure-active-directory
- ms-graph
  urlFragment: ms-identity-docs-code-android-java
---
-->

<!-- SAMPLE ID: DOCS-CODE-034 -->

# Java | mobile app | user sign-in, protected web API access (Microsoft Graph) | Microsoft identity platform

<!-- Build badges here
![Build passing.](https://img.shields.io/badge/build-passing-brightgreen.svg) ![Code coverage.](https://img.shields.io/badge/coverage-100%25-brightgreen.svg) ![License.](https://img.shields.io/badge/license-MIT-green.svg)
-->

This sample demonstrates an Android application written in Java that is both protected by Microsoft identity platform and accesses Microsoft Graph as the user by using the Microsoft Authentication Library (MSAL).

![A screenshot showing the sample application running on an Android emulator.](./app.png)

> :page_with_curl: This sample application backs one or more technical articles on docs.microsoft.com. <!-- TODO: Link to first tutorial in series when published. -->

## Prerequisites

- Azure Active Directory (Azure AD) tenant and the permissions or role required for managing app registrations in the tenant.
- Java
- Android Studio (Bumblebee - 2021.1.1 Patch 2) and an emulator (Android 11+)

## Setup

### 1. Register the app

First, complete the steps in [Register an application with the Microsoft identity platform](https://docs.microsoft.com/azure/active-directory/develop/quickstart-register-app) to register the sample app.

Use these settings in your app registration.

| App registration <br/> setting | Value for this sample app                                                    | Notes                                                                                              |
|:------------------------------:|:-----------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------|
| **Name**                       | `MSAL Demo`                                                                  | Suggested value for this sample. <br/> You can change the app name at any time.                    |
| **Supported account types**    | **Accounts in this organizational directory only (Single tenant)**           | Suggested value for this sample.                                                                   |
| **Platform type**              | **Android**                                                                  | Required value for this sample. <br/> Enables the required and optional settings for the app type. |
| **Configure your Android app** | **Package Name**: `com.example.msaldemo`<br/>**Signature Hash**: _Use provided value_ <br/>**Redirect URI**: _Use provided value_                                                      | Package name is required for this sample, other values are optional and can be changed at any time.  Record these values for use in Android application.                                                                    |

> :information_source: **Bold text** in the tables above matches (or is similar to) a UI element in the Azure portal, while `code formatting` indicates a value you enter into a text box in the Azure portal.

### 2. Update code sample with app registration values

Open the [_AndroidManifest.xml_](app/src/main/AndroidManifest.xml) file and modify the Azure Active Directory configuration property using the values from your [app's registration in the Azure portal](https://docs.microsoft.com/azure/active-directory/develop/quickstart-register-app).

```yaml
<!-- android:path is the value in the Redirect URI Signature hash on Azure Active Directory.-->
android:path="/<signature hash>"
```

Open the [_msal_auth_config.json_](app/src/main/res/raw/msal_auth_config.json) file and modify the Azure Active Directory configuration property using the values from your [app's registration in the Azure portal](https://docs.microsoft.com/azure/active-directory/develop/quickstart-register-app).

```json
// <client_id> is the 'Application (client) ID' of app registration in Azure portal - this value is a GUID
"client_id" : "<client_id>",

// <redirect_uri> is the 'Redirect URI' of app registration in Azure portal
"redirect_uri" : "<redirect_uri>",

// <tenant_id> is the' Directory (tenant) ID' of app registration in Azure portal - this value is a GUID
"tenant_id": "<tenant_id>"
```

### 3. Run the application

In Android Studio, press `Shift` + `F10` or select _Run 'app'_ from the navigation menu.

If everything worked, the sample app should produce output similar to this:

![A screenshot showing the sample application running on an Android emulator.](./app.png)

## About the code

This sample demonstrates how to obtain a token and make a call to the Graph API in Single Account Mode.  The MSAL library is used to request authentication via a SingleAccountPublicClientApplication.  Once the user is authenticated, an HTTP request to Graph is issued by way of Volley.  The results of the call to Graph are displayed in the application.

## Reporting problems

### Sample app not working?

If you can't get the sample working, you've checked [Stack Overflow](https://stackoverflow.com/questions/tagged/msal), and you've already searched the issues in this sample's repository, open an issue report the problem.

1. Search the [GitHub issues](../issues) in the repository - your problem might already have been reported or have an answer.
1. Nothing similar? [Open an issue](../issues/new) that clearly explains the problem you're having running the sample app.

### All other issues

> :warning: WARNING: Any issue in this repository _not_ limited to running one of its sample apps will be closed without being addressed.

For all other requests, see [Support and help options for developers | Microsoft identity platform](https://docs.microsoft.com/azure/active-directory/develop/developer-support-help-options).

## Contributing

If you'd like to contribute to this sample, see [CONTRIBUTING.MD](/CONTRIBUTING.md).

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information, see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.
