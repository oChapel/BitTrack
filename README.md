# 🪙 BitTrack

**BitTrack** is a simple Bitcoin expense tracker.  
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
- **Proto DataStore** — persist BTC rate locally
- **Retrofit** — network layer
- **Coroutines + Flow** — asynchronous data handling & reactive streams
- **Unit Tests** — ViewModels covered with tests

---

## 🏗 Architecture

The project follows a clean **MVVM architecture** with clear separation of concerns:

data/

├─ local/ (Room entities, DAO, DataStore)

├─ remote/ (Retrofit service)

├─ repository/ (data sources implementation)

domain/

├─ model/ (Kotlin data models)

├─ usecase/ (business logic)

presentation/

├─ ui/ (Jetpack Compose screens)

├─ viewmodel/ (state management)


- **Repository Pattern** – abstracts data sources (local/remote)
- **Use Cases** – contain core app logic
- **ViewModels** – expose state as `StateFlow` for Compose UI

---

## ⚙️ Project Setup

### 1️⃣ Clone the repository
   ```bash
   git clone https://github.com/oChapel/BitTrack.git
   ```
### 2️⃣ Setup Secrets

Before running the project, you need to provide your CoinCap API key.
The key is used to authorize requests to the CoinCap API.

In the root directory of the project, create a file named secrets.properties:
   ```bash
   COINCAP_API_KEY=your_api_key_here
   ```

⚠️ Never commit this file to Git — it’s already ignored via .gitignore.

### 3️⃣ Run The App

1. Open the project in Android Studio (Giraffe or newer)
2. Sync Gradle
3. Run on a device or emulator (API 24+)
