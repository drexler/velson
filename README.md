### Velocity-JSON Transformer
[![Build Status](https://travis-ci.org/drexler/velson.svg?branch=master)](https://travis-ci.org/drexler/velson.svg?branch=master)

[AWS API Gateway](https://aws.amazon.com/documentation/apigateway/) currently limits log events to 1 KiB. Log events larger than 1024 bytes,
such as request and response bodies,will be truncated by [AWS API Gateway](https://aws.amazon.com/documentation/apigateway/) before submission to [CloudWatch](https://aws.amazon.com/cloudwatch/) Logs. Thus for large request bodies undergoing template transformation before being forwarded
to the http integration backend, debugging a faulty transform becomes guesswork if the error location is within the truncated portion. This tool
aims to provide the full transform output by mimicking how [AWS API Gateway](https://aws.amazon.com/documentation/apigateway/) transforms a JSON-formated request/response body with the Integration Request/Response Method's mapping template.

![velson](https://user-images.githubusercontent.com/1205434/30297765-e5e95418-9716-11e7-89f7-78fb3f392071.gif)

##### Features:
* Indicating error-causing line number within a mapping template if transform fails.
* Checks for malformed JSON - highlights which line and where.
* Checks for duplicate properties within generated JSON.
* Provides a well formatted JSON output.

**Note: Not all [AWS Mapping Template built-in functions and variables](http://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-mapping-template-reference.html) are currently supported.*

Built-in Functions | Description | Supported |
--- | --- |--- |
*$input.json(x)* | Evaluates a JSONPath expression and returns the results as a JSON string. |`yes`
*$input.path(x)* | Takes a JSONPath expression string (x) and returns an object representation of the result. |`yes`




##### Prerequisite
[Java 1.8](https://aws.amazon.com/documentation/apigateway/) or greater.

##### File Description
* src/main/resources
  * *sample.json* : request/response JSON-formatted body.
  * *testtemplate.vm* : Gateway Mapping template.

##### Usage
*Velson* comes with [Gradle Wrapper](https://docs.gradle.org/3.5/userguide/gradle_wrapper.html) to avoid the need for managing distributions. To see the available tasks one can execute with the wrapper, run the following within the root project folder: `./gradlew tasks`.

*Windows use: `gradlew` or `gradlew.bat` instead as the command*
- Clone the project.
- Building: `./gradlew assemble`
- Running: `./gradlew run`

If using a text editor like Atom or Visual Studio Code, there are plugins/packages that make it easy to execute these commands without resorting to
the terminal. See:

* [build-gradle](https://atom.io/packages/build-gradle) (Atom)
* [gradle](https://marketplace.visualstudio.com/items?itemName=cazzar09.Gradle) (Visual Studio Code)

##### Generating an Eclipse project
* Generate the project: `./gradlew eclipse`
* Open Eclipse with the parent directory of the *velson* project as its workspace.
* Use Eclipse's *Import Project* menu to load the project as existing Gradle Project
