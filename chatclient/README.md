# Chat Client

A JavaFX-based chat client developed for a CSC 413 software development course
project.

## Features

- User login interface
- Chat room listing
- Local Demo Mode
- Mock users and messages
- Send messages locally
- Leave a chat room and log out

## Technologies

- Java
- JavaFX
- Gradle
- WebSocket
- Gson

## Local Demo Mode

The application can run without the original course server.
In Demo Mode, users can log in with any username and test the main chat room
features locally. No real server connection or account is required.

## Requirements

- Java 22 or later
- Gradle 8.8

## Run locally

Open a terminal in the project folder and run:

```cmd
gradle run
```

If Gradle is not added to the system PATH, use the Gradle executable directly:

```cmd
C:\path\to\gradle-8.8\bin\gradle.bat run
```

Replace `C:\path\to\gradle-8.8` with the Gradle installation folder on your
computer.

The application will open in a JavaFX window.

## My Contribution

My work included JavaFX user interface development and adding a local Demo
Mode so the main chat client features could be demonstrated without the
original course server.

## Project Structure

- `app/src/main/java/` — Java source code
- `app/src/main/resources/` — application resources
- `app/build.gradle` — Gradle build configuration

## Limitations

The original course server is no longer active. Therefore, the online login
and real-time chat features are not available in this portfolio version. Demo
Mode is provided for local testing.
