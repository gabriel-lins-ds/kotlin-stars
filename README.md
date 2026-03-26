# Kotlin Stars

Android application that lists the most starred Kotlin repositories on
GitHub.

The app fetches repositories from the GitHub Search API and displays
them in a paginated list. Selecting a repository opens a detail screen
with additional information.

The project was built as a technical exercise and focuses on code
organization, testability, and separation of concerns.

------------------------------------------------------------------------

# Screenshots

| Repository List           | Repository Details           |
| ------------------------- | ---------------------------- |
| ![](screenshots/list.gif) | ![](screenshots/details.png) |

------------------------------------------------------------------------

# Features

-   List most starred Kotlin repositories
-   Infinite scrolling using Paging 3
-   Repository details screen
-   Offline cache using Room
-   Basic error handling and retry support

------------------------------------------------------------------------

# Tech Stack

-   Kotlin
-   Jetpack Compose
-   Paging 3
-   Room
-   Retrofit
-   Koin (Dependency Injection)
-   Coroutines / Flow
-   JUnit & Compose UI tests

------------------------------------------------------------------------

# Architecture

The project follows a layered architecture inspired by Clean
Architecture, with MVVM used on the presentation side.

The goal is to keep business logic separated from framework code and
make components easier to test.

    UI (Compose)
        │
    ViewModel
        │
    Domain
        │
    Repository Interface
        │
    Data Layer
     ├── Remote (GitHub API)
     └── Local (Room cache)

Each layer has a specific responsibility.

------------------------------------------------------------------------

# Project Structure

The project is organized using a **modular architecture** with a clear separation between features and core infrastructure. This structure promotes scalability, faster build times, and easier testability.

### **Module Tree**

```mermaid
graph TD
    %% App Entry Point
    app[":app"]

    %% Features
    feat_list[":feature:repository-list"]
    feat_details[":feature:repository-details"]

    %% Core Layer
    core_data[":core:data"]
    core_domain[":core:domain"]
    core_network[":core:network"]
    core_database[":core:database"]
    core_ui[":core:ui"]
    core_common[":core:common"]
    core_testing[":core:testing"]

    %% App Dependencies
    app --> feat_list
    app --> feat_details
    app --> core_data
    app --> core_ui
    app --> core_common
    app --> core_network
    app --> core_database

    %% Feature Dependencies
    feat_list --> core_domain
    feat_list --> core_ui
    feat_list --> core_common
    feat_list --> core_testing

    feat_details --> core_domain
    feat_details --> core_ui
    feat_details --> core_common
    feat_details --> core_testing

    %% Core Layer Internal Flow
    core_data --> core_domain
    core_data --> core_network
    core_data --> core_database
    core_data --> core_common

    %% Domain Layer (Center)
    core_domain --> core_common
```

### **Layer Responsibilities**

| Module | Responsibility |
| :--- | :--- |
| **`:app`** | The main entry point. Handles DI initialization and global navigation configuration. |
| **`:feature:*`** | Screen-specific logic and UI. Features are isolated from each other. |
| **`:core:domain`** | **Pure Kotlin module** containing Business Models, Repository Interfaces, and Use Cases. |
| **`:core:data`** | Implements Repository interfaces. Orchestrates data flow between network and local cache. |
| **`:core:network`** | Retrofit implementation, API services, and Network DTOs. |
| **`:core:database`** | Room database setup, Entities, and DAOs for local persistence. |
| **`:core:ui`** | Shared Compose components, Design System, and App Theme. |
| **`:core:common`** | Utilities, base classes, and extensions used across multiple modules. |
| **`:core:testing`** | Shared test utilities, Mocks, and Fakes for unit and instrumentation tests. |

------------------------------------------------------------------------

## UI Layer

The UI is written with Jetpack Compose.

It is responsible only for rendering state and forwarding user
interactions to the ViewModel.

    ui/
     ├── component
     ├── repositorylist
     ├── repositorydetails
     ├── navigation
     └── theme

UI components are kept small and reusable where possible.

------------------------------------------------------------------------

## ViewModels

ViewModels coordinate between the UI and the repository layer.

They expose state objects that represent what the UI should display.

Example:

    RepositoryListViewModel
    RepositoryDetailsViewModel

State is represented using sealed classes to make loading, success, and
error states explicit.

------------------------------------------------------------------------

## Domain Layer

The domain layer contains the core models used throughout the app.

    domain/
     ├── model
     ├── repository
     └── error

These classes are independent of Android framework code.

------------------------------------------------------------------------

## Data Layer

The data layer handles retrieving and caching repository data.

    data/
     ├── remote
     ├── local
     ├── mediator
     ├── paging
     ├── repository
     └── mapper

The app uses Paging 3 with RemoteMediator to combine network and local
storage.

Flow:

    GitHub API
         │
    RemoteMediator
         │
    Room Database
         │
    PagingSource
         │
    UI

This allows the list to be paginated while caching results locally.

------------------------------------------------------------------------

# Testing

The project includes both unit tests and UI tests.

Unit tests cover:

-   mappers
-   repository implementation
-   RemoteMediator
-   ViewModels
-   utilities

Compose UI tests cover:

-   reusable UI components
-   list and detail screen content

Test fixtures are used to avoid repeating setup logic.

------------------------------------------------------------------------

# Running the project

Clone the repository:

    git clone https://github.com/gabriel-lins-ds/kotlin-stars.git

Open it in Android Studio and run the `app` module.

The project uses the public GitHub API and does not require
authentication.

------------------------------------------------------------------------

# Notes

GitHub's search API limits unauthenticated requests and caps results to
1000 repositories. This application works within those limits.

------------------------------------------------------------------------

# License

This project is for demonstration purposes.
