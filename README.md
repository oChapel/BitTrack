# 🪙 BitTrack

**BitTrack** is a simple Bitcoin expense tracker created as a test assignment.  
It allows users to top up their Bitcoin balance and record expenses throughout the day.

---

## 🚀 Features

- View and manage your Bitcoin balance
- Add deposits and spending transactions
- Bitcoin-to-USD exchange rate

---

## 🧩 Tech Stack

- **Kotlin**
- **Jetpack Compose** — UI
- **ViewModel (MVVM)** — state management
- **Hilt (Dagger)** — dependency injection
- **Room** — local caching database
- **Retrofit** — fetch Bitcoin exchange rate
- **Coroutines + Flow** — asynchronous data handling
- **Unit Tests** — ViewModels covered with tests

---

## 🏗 Architecture

The project follows a clean **MVVM architecture** with clear separation of concerns:

data/
├─ local/ (Room entities, DAO)
├─ remote/ (Retrofit service)
├─ repository/ (data sources implementation)
domain/
├─ model/ (pure Kotlin data models)
├─ usecase/ (business logic)
presentation/
├─ ui/ (Jetpack Compose screens)
├─ viewmodel/ (state management)


- **Repository Pattern** – abstracts data sources (local/remote)
- **Use Cases** – contain core app logic
- **ViewModels** – expose state as `StateFlow` for Compose UI

---

## ⚙️ Project Setup

1. Clone the repository
   ```bash
   git clone https://github.com/oChapel/BitTrack.git


Open the project in Android Studio (Giraffe or newer)

Sync Gradle

Run on device/emulator with API 24+