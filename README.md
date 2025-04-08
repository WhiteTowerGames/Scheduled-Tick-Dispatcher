# ScheduledTickDispatcherLib

**ScheduledTickDispatcherLib** is a lightweight **library mod** built on top of the **Fabric API** for `1.21.4` and `1.21.5`, providing a clean, secure, and memory-safe way to schedule ticking tasks in Minecraft.

Designed for mod developers, it allows you to register both **immediate** and **delayed** tasks that automatically **unregister themselves** upon completion (or allow you to **unregister them manually**) — eliminating memory leaks and ensuring optimal performance.

## ✨ Features

- 🕰️ **Scheduled Execution** – Register tasks to run after a set number of ticks.
- 🔁 **Tick Phases** – Hook into precise lifecycle events (e.g. `START_SERVER_TICK`, `END_CLIENT_WORLD_TICK`) for exact control.
- ⏱️ **Delayed Tasks** – Schedule delayed executions that begin after a custom tick offset.
- 🔗 **Task Chaining** – Utilities for chaining tasks together for serialized execution.
- 🧹 **Self-Cleanup** – Tasks automatically remove themselves after completion to prevent memory bloat.
- 🔐 **Security Limits** – Built-in caps on task registration per phase as well as limits to task duration and delays to avoid abuse and performance issues.

## 🔧 Installation

Add the library as a dependency via Maven:

```groovy
repositories {
    maven {
        url 'https://your.maven.repo.url'
    }
}

dependencies {
    modImplementation 'com.whitetowergames:scheduled-tick-dispatcher-lib:1.0.0'
}
```
Then refresh your project dependencies and you're good to go!

⚠️Keep in mind that modImplementation is strictly a dev-only dependency. If you wish to distribute a mod that depends on this library, use: 
```groovy 
include modImplementation. . .
``` 
instead!

## 🧠 Usage

> **ScheduledTickDispatcher** contains rich **javadoc** all over the place, documenting every class and method, as well as an example mod in the repository.
For more in-depth explanation to cover most use cases, please view the Wiki.

## 🛡️ Built-in Security

To ensure stability and prevent abuse:

- ✅ A **hard cap** of 30 tasks per phase is enforced.
- ✅ Internal logging tracks misuse and provides context for debugging.
- ✅ Tasks unregister **automatically** when complete.

## 📜 License

This project is licensed under **Creative Commons 4.0**. No strings attached. See the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

PRs welcome! Fork, branch, improve, and submit to your heart's content. However, please take care to keep changes modular and clean.
