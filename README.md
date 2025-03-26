<p align="center">
  <img src="banner.png" alt="GitHubExplorer Banner" width="100%" />
</p>

# GitHubExplorer 📱

A modern, **scalable Android application** built with **Jetpack Compose** and **Clean Architecture**, designed to explore GitHub user profiles. Built with production-level modularity and clean code practices.

---

## 🚀 Features

- 🔍 Search GitHub users
- 👤 View user details and followers
- 🧱 Clean Architecture: domain, data, and presentation layers
- 🧩 Scalable and modular project structure
- 🎨 100% Jetpack Compose UI
- 🌐 GitHub REST API integration
- 🧪 Unit and UI test ready
- ⚙️ CI/CD-friendly structure

---

## 🧠 Architecture

```
app/
core/
  ├── ui/
  ├── network/
  └── utils/
feature/
  ├── users/
  │   ├── data/
  │   ├── domain/
  │   └── presentation/
  └── profile/
      ├── data/
      ├── domain/
      └── presentation/
```
- **Feature Modules**: Each feature (like `users`, `profile`) has its own `data`, `domain`, and `presentation` layers.
- **Core Modules**: Contains shared components like UI elements, networking, and utilities.
- **App Module**: The entry point that wires everything together.

## 🛠 Tech Stack

- **Kotlin**  
- **Jetpack Compose**  
- **Coroutines & Flow**  
- **Hilt (DI)**  
- **Retrofit**  
- **Coil (Image Loading)**  
- **Jetpack Navigation**  
- **Material 3**

---

## 📦 Modularization

- `app` – Main entry point  
- `core-ui` – Shared Compose UI elements  
- `core-network` – Networking logic  
- `core-utils` – Constants, helpers  
- `feature-users` – GitHub users list  
- `feature-profile` – User detail screen  

Modularization allows:  
- Better scalability and testability  
- Feature isolation  
- Faster CI builds

---

## 🧪 Testing

- ✅ Unit tests  
- ✅ UI tests (Compose)  
- ✅ Mockable architecture  
- 🧪 Easy to integrate test coverage tools  

---

## 🔧 CI/CD (GitHub Actions)

Sample workflow `.github/workflows/android.yml` includes:
- ✅ Gradle caching  
- ✅ Build Debug APK  
- ✅ Run unit tests

---

## 📈 Roadmap

- [x] GitHub user list  
- [x] User profile details
- [ ] GitHub user search
- [ ] Favorite users feature  
- [ ] Dark mode  
- [x] Paging + Caching  
- [ ] Full test coverage  

---

## 🙌 Contributing

Contributions are welcome!  
Feel free to fork, open issues, or submit pull requests.

---

## 📄 License

Licensed under the MIT License.  
See the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Created by
Built with lovers (GT)
