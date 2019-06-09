# Agile Android Developer Challenge

## Objective

Develop a native application, Android or iOS, which will allow the user to search and display a GitHub profile. Including the profile name, photo, repositories and their programming languages.

The API for making calls is available at the following URL: https://api.github.com/users/[username]/repos

## Features

* <b>Home screen: </b>Composed of a text box, where the profile name should be entered, and a button to perform the search, whose result should be displayed in the profile detail screen.
* <b>Profile detail: </b>This screen should display the profile name and photo (if it is found) and a list with all its the repositories. This list should present the name of the repository and, in the subtitle of each item, its programming language. A back button should allow you to return to the home screen to perform a new search.
* <b>Error handling: </b>When any error occurs in the API call, the following message should be displayed: "A network error has occurred. Check your Internet connection and try again later.". This message should have an "OK" button that just closes it.
* <b>User not found: </b>If a user is not found, display the following message: "User not found. Please enter another name.". This message should have an "OK" button that just closes it.

## Requirements

* The application should be developed using the native tools, Android Studio + Kotlin.
* Follow as closely as possible the reference layout provided in this document.
* Layout should fit any screen size, including tablet. For this it is enough to maintain the alignment of the elements.
* You can use any library to build the application, Retrofit, Picasso and etc.

## Considerations

In addition to the implementation of all functionalities and requirements, the following aspects of the solution will be taken into account:

* Clarity and organization of the code and files.
* Testability.
* Maintainability.
* Performance.
* It is expected a "production level" quality, something you consider good enough to release in production.

## Android Reference
