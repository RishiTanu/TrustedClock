# TrustedTimeDemo

A small Android Jetpack Compose project that demonstrates how to get **reliable time** on the client side even if the user changes the device time.

This project uses Google’s **TrustedTime API** (Play Services) and follows a clean **MVVM** architecture (ViewModel + Repository + DataSource).

---
## Problem

`System.currentTimeMillis()` depends on the device’s system clock.  
If the user manually changes the date/time from Settings, the time becomes incorrect.

Many apps (banking, offers, logs, games, etc.) need a trustworthy time that cannot be easily manipulated.

## Solution

This app uses Google’s **TrustedTime API**:

- Play Services periodically syncs with Google’s servers in the background.
- After the first successful sync, the app can get correct time even offline.
- The trusted time remains correct even if the user changes the device time.

---
## Features

- Shows **Trusted Time** and **Device Time** side by side
- Shows the difference in milliseconds
- Updates every second
- Clean MVVM structure

---
## Tech Stack

- Kotlin
- Jetpack Compose
- ViewModel + StateFlow
- Coroutines
- Google Play Services TrustedTime API

---
## Project Structure

```
app/src/main/java/com/example/trustedtimedemo/
│
├── data/
│   ├── datasource/
│   │   └── TimeDataSource.kt          # Talks to TrustedTimeClient
│   └── repository/
│       └── TimeRepository.kt          # Prepares data for ViewModel
│
├── ui/
│   ├── MainViewModel.kt               # Holds UI state & updates time
│   └── MainScreen.kt                  # Compose UI
│
├── TrustedTimeApplication.kt          # Initializes TrustedTimeClient
└── MainActivity.kt
```

---
## How It Works (Step by Step)

### 1. Add Dependency

```kotlin
implementation("com.google.android.gms:play-services-time:16.0.1")
```

This is required to use the TrustedTime API.

### 2. Application Class

We create the `TrustedTimeClient` early in the app lifecycle and store it.

```kotlin
TrustedTime.createClient(this).addOnCompleteListener { task ->
    if (task.isSuccessful) {
        trustedTimeClient = task.result
    }
}
```

- Called every time the app process starts.
- Play Services decides internally whether a network sync is needed.
- We only store the client object (not the time).


```kotlin
fun getTrustedTimeMillis(): Long? {
    return application.trustedTimeClient?.computeCurrentUnixEpochMillis()
}
```

### 4. Repository

Collects trusted time + device time and calculates the difference.

### 5. ViewModel
- Uses `viewModelScope` + coroutine.
- Updates the UI state every 1 second using `delay(1000)`.
- Exposes data as `StateFlow`.

### 6. Compose UI
Observes the ViewModel state and displays:

- Trusted Time
- Device Time
- Difference

---

## Key Concepts
| Concept | Explanation |
|---------|-------------|
| TrustedTime API | Official Google way to get reliable time |
| Offline support | After first sync, time can be calculated without network |
| After boot | Needs internet at least once after device restart |
| Background sync | Play Services handles network calls, not your app |
| Client reuse | Create client once and reuse it |
| MVVM | DataSource → Repository → ViewModel → UI |

---

## How to Run
1. Clone the repository
2. Open in Android Studio
3. Sync Gradle
4. Run on a device/emulator with Google Play Services
5. Change device time from Settings and observe the difference

---

## Limitations

- Requires Google Play Services
- Needs internet at least once after boot


---

## What You Will Learn

- How to use TrustedTime API
- Clean MVVM architecture in a small project
- ViewModel + StateFlow + Coroutines
- Separating UI, business logic, and data source

---
## Author
Feel free to fork, star, and use this project for your own learning.
