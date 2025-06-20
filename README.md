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