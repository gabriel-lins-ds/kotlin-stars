# Kotlin Stars

![Kotlin](https://img.shields.io/badge/Kotlin-100000?style=flat&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=flat&logo=android&logoColor=white)

Kotlin Stars is an **Android app** that fetches the most starred Kotlin repositories from GitHub. It demonstrates **modern Android development practices**, including:

- Kotlin + Coroutines + Flow  
- Jetpack Compose  
- MVVM architecture  
- Clean Architecture  
- Paging 3 with RemoteMediator  
- Room database for caching  
- Dependency Injection with Koin  
- Unit and Instrumentation testing  

---

## Features

- View a **paginated list of Kotlin repositories** from GitHub  
- **Repository details** screen with author info, stars, forks, and repository URL  
- **Offline caching** using Room and RemoteMediator  
- **Error handling** for network issues, GitHub rate limits, and empty results  
- **Automatic formatting** for large numbers (stars, forks) with `k`/`M` notation  

---

## Architecture

```text
+-------------------------+
|       UI Layer          |
|  - Compose Screens      |
|  - UI Components        |
+-------------------------+
            |
            v
+--------------------------------+
|    ViewModel Layer             |
|  - RepositoryListViewModel     |
|  - RepositoryDetailsViewModel  |
+--------------------------------+
            |
            v
+-------------------------------+
|  Repository Layer             |
|  - KotlinStarsRepositoryImpl  |
|  - RepositoryPagerFactory     |
+-------------------------------+
            |
            v
+----------------------------------------+
| Data Layer                             |
|  - Remote: KotlinStarsApi              |
|  - Local: KotlinStarsLocalDataSource   |
|  - Mediator: RepositoryRemoteMediator  |
+----------------------------------------+
```

---

## Tech Stack

- **Kotlin** – language  
- **Jetpack Compose** – UI  
- **Paging 3** – pagination with RemoteMediator  
- **Room** – local caching  
- **Koin** – dependency injection  
- **JUnit 6 + Mockk** – unit testing  
- **Coroutines Test** – coroutine testing  

---

## Project Structure

### `data`

- **Local**: `KotlinStarsLocalDataSource`, DAOs, `AppDatabase`, entities  
- **Remote**: `KotlinStarsApi`, DTOs  
- **Mediator**: `RepositoryRemoteMediator`  
- **Paging**: `RepositoryPagerFactory`  
- **Repository**: `KotlinStarsRepositoryImpl`  

### `domain`

- Models: `Repository`, `RepositoryAuthor`  
- Mappers: `RepositoryMapper`, `ThrowableMapper`  
- Repository interface: `KotlinStarsRepository`  
- Errors: `DomainError`, `DomainException`  

### `ui`

- Screens: `RepositoryListScreen`, `RepositoryDetailsScreen`  
- ViewModels: `RepositoryListViewModel`, `RepositoryDetailsViewModel`  
- Components: `ErrorView`, `EmptyRepositoryListView`, `LoadingView`, `RepositoryItem`, `InfoChip`, `GithubAuthorImage`  
- Navigation: `AppNavigation`  
- Sample Data: `RepositorySampleData`  
- Theme: `Color`, `Theme`, `Type`  

### `util` & `di`

- Utils: `FormatUtils`, `IdenticonUtils`  
- Constants: `NetworkConstants`, `UiConstants`  
- DI modules: `dataModule`, `databaseModule`, `networkModule`, `viewModelModule`  

---

## How to Run

1. Clone the repo:

```bash
git clone https://github.com/gabriel-lins-ds/kotlin-stars.git
```

2. Open in **Android Studio**  
3. Run on an emulator or device  

---

## Testing

- Unit tests: `./gradlew test`  
- Instrumentation tests: `./gradlew connectedAndroidTest`  

---

## Contributing

1. Fork the repo  
2. Create a feature branch: `git checkout -b feature/YourFeature`  
3. Commit changes: `git commit -m "Add feature"`  
4. Push: `git push origin feature/YourFeature`  
5. Open a Pull Request  

---

## License

MIT © Gabriel Lins