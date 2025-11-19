# MapApp

Kotlin Multiplatform приложение с поддержкой Google Maps для Android и iOS.

## Требования

### Для iOS разработки
- Xcode 14.0 или выше
- CocoaPods (для управления зависимостями iOS)

## Установка CocoaPods

CocoaPods необходим для сборки iOS версии приложения.

### Вариант 1: Автоматическая установка (рекомендуется)

Запустите скрипт из корня проекта:
```bash
./install-cocoapods.sh
```

### Вариант 2: Установка через Homebrew
```bash
brew install cocoapods
```

### Вариант 3: Установка через gem
```bash
sudo gem install cocoapods
```

Или без sudo (в user scope):
```bash
gem install cocoapods --user-install
export PATH="$HOME/.gem/ruby/[VERSION]/bin:$PATH"
```

### Проверка установки
```bash
pod --version
```

### Настройка проекта

После установки CocoaPods добавьте путь к исполняемому файлу в `local.properties`:
```bash
echo "kotlin.apple.cocoapods.bin=$(which pod)" >> local.properties
```

## Сборка проекта

### Android
```bash
./gradlew :composeApp:assembleDebug
```

### iOS
```bash
./gradlew :composeApp:podInstall
```

## Структура проекта

- `composeApp/` - основной модуль приложения
  - `src/androidMain/` - Android-специфичный код
  - `src/iosMain/` - iOS-специфичный код
  - `src/commonMain/` - общий код для всех платформ
- `presentation/` - модуль презентационного слоя - Compose Multiplatform with Google Maps

A Kotlin Multiplatform application built with Compose Multiplatform that displays Google Maps on both Android and iOS platforms. Users can add markers by entering latitude/longitude coordinates.

## Features

- ✅ **Multi-module Clean Architecture** - Separated into core, domain, data, and presentation layers
- ✅ **MVVM Architecture** - ViewModels with StateFlow for state management
- ✅ **Koin Dependency Injection** - Clean DI setup
- ✅ **Voyager Navigation** - Type-safe navigation between screens
- ✅ **Google Maps Integration** - Native Google Maps on both Android and iOS
- ✅ **Marker Management** - Add origin and multiple route markers
- ✅ **Coordinate Validation** - Robust validation of latitude/longitude inputs

## Architecture Overview

The project follows Clean Architecture principles with clear separation of concerns:

```
├── domain/         # Business logic layer
│   ├── model/      # Domain models (RouteMarker)
│   ├── repository/ # Repository interfaces
│   └── usecase/    # Use cases (AddMarkerUseCase, SetOriginUseCase, etc.)
├── data/           # Data layer
│   └── repository/ # Repository implementations
└── presentation/   # UI layer
    ├── di/         # Koin dependency injection modules
    ├── ui/
    │   ├── content/    # Composable UI components
    │   ├── screen/     # Voyager screens
    │   ├── state/      # UI state models
    │   └── viewmodel/  # ViewModels
└── composeApp/     # App entry point and platform-specific code
```

### Architecture Layers

1. **Core**: Contains shared data models and utilities used across all layers
2. **Domain**: Contains business logic, use cases, and repository interfaces
3. **Data**: Implements repository interfaces with in-memory storage
4. **Presentation**: Contains UI components, ViewModels, and navigation

### Key Components

- **RouteViewModel**: Manages route markers state and coordinates with the domain layer
- **EditRouteViewModel**: Handles input validation and coordinate entry
- **RouteRepository**: Interface for managing route data
- **Use Cases**: Encapsulate business logic (AddMarkerUseCase, SetOriginUseCase, GetRouteMarkersUseCase)

## How to Run

### Prerequisites

- Android Studio Hedgehog or later
- Xcode 15+ (for iOS)
- CocoaPods installed (`sudo gem install cocoapods`)
- Google Maps API key for both platforms

### Android Setup

1. **Get Google Maps API Key**:
   - Go to [Google Cloud Console](https://console.cloud.google.com/)
   - Create a new project or select existing one
   - Enable "Maps SDK for Android"
   - Create an API key
   - Add the key to `composeApp/src/androidMain/AndroidManifest.xml`:
     ```xml
     <meta-data
         android:name="com.google.android.geo.API_KEY"
         android:value="YOUR_API_KEY_HERE" />
     ```

2. **Run on Android**:
   ```bash
   ./gradlew :composeApp:installDebug
   ```
   Or use Android Studio to run the app on an emulator or device.

### iOS Setup

1. **Get Google Maps API Key**:
   - In Google Cloud Console, enable "Maps SDK for iOS"
   - Create an iOS API key (or use the same key)
   - Add the key to `iosApp/iosApp/Info.plist`:
     ```xml
     <key>GMSApiKey</key>
     <string>YOUR_API_KEY_HERE</string>
     ```

2. **Install CocoaPods Dependencies**:
   ```bash
   cd iosApp
   pod install
   cd ..
   ```

3. **Run on iOS**:
   - Open `iosApp/iosApp.xcworkspace` in Xcode
   - Select a simulator or device
   - Build and run (⌘R)

### Building from Command Line

**Android**:
```bash
./gradlew :composeApp:assembleDebug
```

**iOS** (requires Xcode):
```bash
./gradlew :composeApp:embedAndSignAppleFrameworkForXcode
```

## Usage

1. **View Map**: The app starts on the Map screen showing Google Maps
2. **Add Marker**: Tap the "+" floating action button to open the Edit Route screen
3. **Enter Coordinates**: 
   - Enter latitude (e.g., 1.35)
   - Enter longitude (e.g., 103.87)
   - Optionally check "Set as origin" to mark it as the starting point
   - Tap "Add Marker" or "Set Origin"
4. **View Markers**: All markers appear on the map with their coordinates
5. **Navigate Back**: Tap "Back to Map" to return to the map view

## Technical Stack

- **Kotlin Multiplatform**: Shared business logic across platforms
- **Compose Multiplatform**: Shared UI code
- **Koin**: Dependency injection
- **Voyager**: Navigation library
- **Coroutines & Flow**: Asynchronous programming and reactive state
- **Google Maps SDK**: 
  - Android: Maps Compose library
  - iOS: Google Maps SDK via CocoaPods

## Project Structure

```
MapApp/
├── domain/                  # Domain module
├── data/                    # Data module
├── presentation/            # Presentation module
├── composeApp/              # Main app module
│   ├── src/
│   │   ├── commonMain/      # Shared code
│   │   ├── androidMain/     # Android-specific
│   │   └── iosMain/         # iOS-specific
└── iosApp/                  # iOS app wrapper
```

## Dependencies

Key dependencies are managed in `gradle/libs.versions.toml`:
- Compose Multiplatform
- Koin 3.5.6
- Voyager 1.1.0
- Google Maps SDK
- Coroutines

## Notes

- The app uses in-memory storage for markers (data is lost on app restart)
- Coordinate validation ensures values are within valid ranges (-90 to 90 for latitude, -180 to 180 for longitude)
- The origin marker is displayed differently from regular markers
- Camera automatically centers on the origin marker if available

## License

This project is a sample implementation for educational purposes.
