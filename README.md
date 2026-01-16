# hypsum

hypsum is a simple way to add placeholders to Hytale servers. It lets you show dynamic information in messages and text.

# 🏗 Project Structure

- **[`api`](api/)**: What you use to make your own placeholders.
- **[`core`](core/)**: How the placeholders are actually handled.
- **[`plugin`](plugin/)**: The main plugin you install on your server.
- **[`example-plugin`](example-plugin/)**: A simple example to show you how it works.

# 🚀 Features

- **⚡ Fast**: Works quickly without slowing down your server.
- **🛡 Safe**: No infinite loops or broken messages.
- **👤 Player Names**: Can show different things for different players.
- **⚙️ Customizable**: Change how your placeholders look (like using `{}` instead of `%%`).

# 💻 Developer Guide

The latest version of Hypsum API is `1.1`.
Add Hypsum API to your project using your build tool of choice.

## 🐘 Gradle

Add this to your `build.gradle` or `build.gradle.kts`:

### Kotlin

```kotlin
dependencies {
    compileOnly("com.craftlyworks.hypsum:api:1.1")
}
```

### Groovy

```groovy
dependencies {
    compileOnly 'com.craftlyworks.hypsum:api:1.1'
}
```

## 🦢 Maven

Add this to your `pom.xml`:

```xml

<dependency>
    <groupId>com.craftlyworks.hypsum</groupId>
    <artifactId>api</artifactId>
    <version>1.1</version>
    <scope>provided</scope>
</dependency>
```

## 📝 Create a Placeholder

Here is how you add a new placeholder:

```java
import com.craftlyworks.hypsum.api.HypsumProvider;
import com.craftlyworks.hypsum.api.placeholder.Placeholder;

HypsumApi api = HypsumProvider.get();
api.registerPlaceholder(new Placeholder() {
    @Override
    public String getIdentifier() {
        return "my_placeholder";
    }

    @Override
    public String getValue(PlayerRef player) {
        return "Hello, " + player.getDisplayName();
    }
});
```

## 🔍 Use your Placeholders

To replace placeholders in a string:

```java
String text = "Welcome %my_placeholder%!";
String result = HypsumProvider.get().process(player, text);
// Result: "Welcome Hello, PlayerName!"
```

Make sure to add Hypsum as a dependency in your plugin `manifest.json` to use these features.
If you want an optional integration, use `OptionalDependencies` instead.
```json
{
  "Dependencies": {
    "com.craftlyworks.hypsum:Hypsum": ">=1.1"
  }
}
```
Enjoy coding with Hypsum! 🔥


# 🛠 Building

To build the project, we use Gradle and Java 25+. Run this command in the root directory:

```bash
./gradlew build
```

---
© 2026 CraftlyWorks | Licensed under MIT License