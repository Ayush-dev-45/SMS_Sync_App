# SMS Sync App

An Android app that reads and syncs SMS messages in the background every 15 minutes using **WorkManager**, following **MVVM architecture** and built with **Jetpack Compose**.

---

## Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **MVVM Architecture**
- **WorkManager**
- **Retrofit (API Communication)**

---

## Features

- Background syncing of SMS messages every 15 minutes
- Permission-gated UI: shows PermissionScreen if SMS permission not granted
- Responsive and minimal UI with SMS cards using Compose
- API integration using Retrofit
---

## 📁 Project Structure & Responsibilities

| File / Directory       | Responsibility                                                                                           |
|------------------------|----------------------------------------------------------------------------------------------------------|
| `MainActivity.kt`      | Entry point of the app. Sets up the UI and theme.                                                        |
| `SmsPermissionGate.kt` | Checks and requests SMS permissions. Loads either the `PermissionScreen` or `SmsScreen` accordingly.     |
| `PermissionScreen.kt`  | Shown when SMS permission is not granted. Prompts the user to allow access.                              |
| `SmsScreen.kt`         | Displays the list of SMS messages in a card layout. Uses Compose's `LazyColumn`.                         |
| `SmsItem.kt`           | Composable for rendering each SMS in a stylized card view.                                               |
| `SmsViewModel.kt`      | Handles logic for loading SMS from repository and exposes them as `StateFlow` to the UI.                 |
| `SmsRepository.kt`     | Retrieves SMS messages from the system content provider.                                                 |
| `SmsData.kt`           | Data model class representing a single SMS message.                                                      |
| `SmsSyncWorker.kt`     | A `CoroutineWorker` that performs the periodic background sync of SMS data.                              |
| `RetrofitInstance.kt`  | Configures and exposes a singleton Retrofit API client.                                                  |
| `ApiService.kt`        | Defines the API interface for syncing SMS data to a backend server.                                      |
| `work/Scheduler.kt`    | (Optional best practice) You may move `scheduleSmsSyncWorker()` here for cleaner separation of concerns. |

## Permissions Used

- `android.permission.READ_SMS`  
  Used to fetch SMS messages for syncing.
---

## 📦 How It Works

1. **App Launch**:
    - If SMS permission is granted → `SmsScreen` is shown.
    - If not → `PermissionScreen` is shown.

2. **WorkManager**:
    - Background task scheduled every 15 minutes to sync SMS.
    - Resilient to app kills and device restarts (as per WorkManager's reliability).

## To-Do (Optional Enhancements)

- Enable push notification on successful sync (currently disabled)

---