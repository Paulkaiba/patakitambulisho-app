# Pata Kitambulisho — Android App

The Android companion to the [Pata Kitambulisho Management System](https://github.com/Paulkaiba/patakitambulisho), a platform for applying for national ID services. This app lets users register and sign in securely from their phone. It talks to the PHP backend through a JSON API.

> **Status: early stage.** The authentication flow is built. ID applications and tracking are the next milestone.

<!-- [Login](docs/screenshots/login.png)
![OTP verification](docs/screenshots/otp.png)
![Dashboard](docs/screenshots/dashboard.png)
-->

## What works today

- Register a new account, confirmed with a one-time code (OTP)
- Log in with email or mobile number and password
- Forgot password, with OTP verification and password reset
- Change password
- Resend OTP
- Dashboard screen after login

## Tech stack

- **Language:** Kotlin
- **UI:** Fragments and XML layouts with Material Components
- **Architecture:** MVVM with ViewModels, ViewModel factories and a repository layer
- **Networking:** Retrofit, Gson and OkHttp logging interceptor
- **Async:** Kotlin Coroutines, LiveData
- **Min SDK:** 24 (Android 7.0) · **Target SDK:** 36

## Project structure

```
app/src/main/java/com/example/patakitambulisho/app/
├── UIfragments/   Screens: login, register, OTP, forgot and change password, dashboard
├── ui/            ViewModels and factories for each screen
├── data/          API service, request and response models, repository
├── network/       Retrofit client
└── util/          Result wrapper
```

## Getting started

**Requirements:** Android Studio (recent version), JDK 11 or newer, and the PKMS backend running.

1. Set up and run the backend from the [web repository](https://github.com/Paulkaiba/patakitambulisho) so the `API/` endpoints are reachable.
2. Clone this repository and open it in Android Studio.
3. Set `BASE_URL` to your backend address, ending with `/patakitambulisho/API/`. It is defined in:
   - `network/ApiClient.kt`
   - `data/api/RetrofitClient.kt`

   On the Android emulator, `10.0.2.2` points to your computer. On a real phone, use your computer's local network IP, with both on the same Wi-Fi.
4. Let Gradle sync, then run the app on an emulator or device.

> The app currently allows plain HTTP (`usesCleartextTraffic`) for local development. Switch to HTTPS before any real deployment.

## Roadmap

- [ ] Submit new, replacement and stolen ID applications from the app
- [ ] Track application status
- [ ] Upload documents from the camera or gallery
- [ ] Secure token-based sessions
- [ ] Move `BASE_URL` into build config

## Author

**Paul Kaiba** — [GitHub](https://github.com/Paulkaiba)
