<!-- PROJECT LOGO -->
<br />
<div align="center">
  <h3 align="center">NoSyntax Foundation For Android</h3>
  <p align="center">
    Template used as a foundation in the NoSyntax app builder.
    <br />
    <a href="https://github.com/aelrahmanashraf/nosyntax-foundation-android"><strong>Explore Demo »</strong></a>
    <br />
    <br />
    <a href="https://github.com/aelrahmanashraf/nosyntax-foundation-android/issues">Report Bug</a>
    ·
    <a href="https://github.com/aelrahmanashraf/nosyntax-foundation-android/issues">Request Feature</a>
  </p>
</div>

<!-- ABOUT THE PROJECT -->
## About The Project

Foundation Android template designed for [nosyntax.io][nosyntax-url], a no-code app builder. If you're a seasoned developer curious about the technical workings of this service, feel free to dive in. For non-technical enthusiasts, give our builder a try and save valuable time.

Reasons to use nosyntax's builder:
* **No-Code Development:** Create apps effortlessly with nosyntax, no coding required.
* **Easy Customization:** Personalize your app easily with an intuitive interface.
* **Quick Prototyping:** Swiftly prototype and iterate using nosyntax's modular architecture.
* **Seamless Integration:** Enhance functionality effortlessly with pre-built modules and libraries.

### Built With

We crafted the nosyntax foundation with care, using the newest tech to improve the development process.

[![Android Studio][android-studio-badge]][android-studio-url]
[![Kotlin][kotlin-badge]][kotlin-url]
[![Jetpack Compose][jetpack-compose-badge]][jetpack-compose-url]
[![Gradle][gradle-badge]][gradle-url]
[![Apache Groovy][groovy-badge]][groovy-url]
[![Jenkins][jenkins-badge]][jenkins-url]

<!-- GETTING STARTED -->
## Getting Started

This section is for tech enthusiasts who want to learn how our service works or are interested in an educational guide.

### Prerequisites

* Java 17+
* The latest stable Android Studio (for easy install use [JetBrains Toolbox][jetbrains-toolbox-url])

### Installation

1. Fork and Clone the Repository:

   ```sh
   git clone https://github.com/aelrahmanashraf/nosyntax-android-template/
   ```

2. Open the Project with Android Studio:
    * Launch Android Studio.
    * Select "Open an existing Android Studio project."
    * Navigate to the directory where you cloned the repository and choose the project folder.
3. Synchronization:
    * Allow Android Studio to sync the project files.
    * Ensure any required dependencies are downloaded.

Once these steps are completed, you'll have the template ready for use in Android Studio.

### Usage

Before you build and run the template, execute this command to set it up first.
* For Linux and macOS:

  ```sh
  ./init.sh
  ```
* For Windows:

  ```bash
  init.bat
  ```

<!-- REST API -->
## REST API

We use REST to enable dynamic/remote configuration for various features such as theme, components, and pages, except some options like dynamically changing the package name, which is not feasible in Android.

### Getting Started

To make a REST API request, use the POST method along with the URL pointing to the API service and one or more HTTP request headers.

Here's the main API endpoint:

```http
POST https://api.nosyntax.io/
```

### Versioning

Our API version is included at the end of the API endpoint. Currently, the latest version is v1.0.

The constructed URL looks like:

```http
POST https://api.nosyntax.io/v1.0
```

### Authentication

We use Bearer Authorization to authenticate all requests, in addition to the Project Access Token which is crucial for retrieving dynamic app configurations and more.

You can locate these tokens on the project settings page.

When making API requests, ensure you include the Bearer token in the `Authorization` header. Also, remember to provide your Access Token as a parameter in the POST request.

Example using cURL:

```bash
curl -X POST https://api.nosyntax.io/v1.0/app_config \
     -H "Authorization: Bearer AUTH_TOKEN" \
     -H "Content-Type: application/x-www-form-urlencoded" \
     -d "access_token=ACCESS_TOKEN"
```

**Sample Response**

```
{
  "app": {
    "id": "com.example.android",
    "name": "Playground",
    "category": "Demo",
    "description": "Lorem Ipsum is simply dummy text."
  }
}
```

### Responses

HTTP status codes returned by the nosyntax REST API.

**Successful requests**

| Status Code | Description                                       |
|:------------|:--------------------------------------------------|
| 200         | `SUCCESS. The request succeeded.`                 |

**Failed requests**

| Status Code | Description                                                     |
|:------------|:----------------------------------------------------------------|
| 400         | `INVALID_REQUEST. Invalid scope requested.`                     |
| 401         | `INVALID_TOKEN. Missing or invalid bearer authorization.`       |
|             | `INVALID_TOKEN. Missing or invalid access token.`               |
| 404         | `RESOURCE_NOT_FOUND. The specified resource does not exist.`    |
| 500         | `INTERNAL_SERVER_ERROR. An internal server error has occurred.` |
| 503         | `SERVICE_UNAVAILABLE. Service Unavailable.`                     |

<!-- CONTACT -->
## Contact

If you have any questions or need assistance, please feel free to contact us at [support@nosyntax.io][nosyntax-email].

<!-- CONTRIBUTING -->
## Contributing

Join us in shaping the future of no-code app development. If you have any suggestions to make this template better, please open an issue with the tag "enhancement".

Don't forget to give the project a star! Thanks again!

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[nosyntax-url]: https://nosyntax.io
[nosyntax-email]: mailto:support@nosyntax.io
[android-studio-badge]: https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white
[android-studio-url]: https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white
[kotlin-badge]: https://img.shields.io/badge/Kotlin-7f6ee3?style=for-the-badge&logo=kotlin&logoColor=white
[kotlin-url]: https://kotlinlang.org/
[jetpack-compose-badge]: https://img.shields.io/badge/Jetpack%20Compose-3a83f9?style=for-the-badge&logo=jetpack-compose&logoColor=white
[jetpack-compose-url]: https://developer.android.com/jetpack/compose
[gradle-badge]: https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white
[gradle-url]: https://gradle.org/
[groovy-badge]: https://img.shields.io/badge/Apache%20Groovy-4298B8.svg?style=for-the-badge&logo=Apache+Groovy&logoColor=white
[groovy-url]: https://groovy-lang.org/
[jenkins-badge]: https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white
[jenkins-url]: https://www.jenkins.io/
[jetbrains-toolbox-url]: https://www.jetbrains.com/toolbox-app/