# hypsum

hypsum is a simple way to add placeholders to Hytale servers. It lets you show dynamic information in messages and text.

## 🏗 Project Structure

- **[`api`](api/)**: What you use to make your own placeholders.
- **[`core`](core/)**: How the placeholders are actually handled.
- **[`plugin`](plugin/)**: The main plugin you install on your server.
- **[`example-plugin`](example-plugin/)**: A simple example to show you how it works.

## 🚀 Features

- **⚡ Fast**: Works quickly without slowing down your server.
- **🛡 Safe**: No infinite loops or broken messages.
- **👤 Player Names**: Can show different things for different players.
- **⚙️ Customizable**: Change how your placeholders look (like using `{}` instead of `%%`).

## 💻 Developer Guide

### 📦 Add to your project

Add this to your `build.gradle.kts`:

```kotlin
dependencies {
    compileOnly("com.craftlyworks.hypsum:api:1.0-SNAPSHOT")
}
```

### 📝 Create a Placeholder

Here is how you add a new placeholder:

```java
import com.craftlyworks.hypsum.api.HypsumProvider;
import com.craftlyworks.hypsum.api.placeholder.Placeholder;

HypsumProvider.get().

registerPlaceholder(new Placeholder() {
    @Override
    public String getIdentifier () {
        return "my_placeholder";
    }

    @Override
    public String getValue (Player player){
        return "Hello, " + player.getDisplayName();
    }
});
```

### 🔍 Use your Placeholders

To replace placeholders in a string:

```java
String text = "Welcome %my_placeholder%!";
String result = HypsumProvider.get().process(player, text);
// Result: "Welcome Hello, PlayerName!"
```

## 🛠 Building

To build the project:

```bash
./gradlew build
```

---
© 2026 CraftlyWorks