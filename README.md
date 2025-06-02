# Android Conference App – Hotwire Native

This is the Android native app built with [Hotwire Native](https://native.hotwired.dev/overview/how-it-works), serving as a client for our existing [Conference App](https://github.com/TelosLabs/rails-world).

---

## 🚀 Features

- Powered by **Hotwire Native** for seamless hybrid navigation  
- Lightweight, fast, and easy to maintain  
- Simple setup with minimal code changes  

---

## 🛠 Setup Instructions

### 1. Set your Web App URL

Update the base URL of your web application so that the native client knows where to load content from.

#### 🔧 `MainActivity.kt`

Find this line:

```kotlin
const val baseURL = "http://localhost:5000/"
```

Replace it with your actual URL:

```kotlin
const val baseURL = "https://your-app.com"
```

---

### 2. Run the app

Select a simulator or a physical device in Android Studio and press `Ctrl + R` to run the app.

---

## 📦 Dependencies

- [hotwire-native-android](https://github.com/hotwired/hotwire-native-android)
- Android Studio Ladybug or Later

---

Built with ❤️ using Turbo Native
