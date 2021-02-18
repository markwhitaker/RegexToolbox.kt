![icon](artwork/RegexToolbox-icon-100.png)
# RegexToolbox.kt [![CircleCI](https://circleci.com/gh/markwhitaker/RegexToolbox.kt.svg?style=shield)](https://circleci.com/gh/markwhitaker/RegexToolbox.kt) [![Download from JitPack](https://jitpack.io/v/markwhitaker/RegexToolbox.kt.svg)](https://jitpack.io/#markwhitaker/RegexToolbox.kt)


Regular expression tools for Kotlin developers.

## Regular expressions made easy

RegexToolbox allows you to build regular expressions in a more human-readable way using a lightweight, powerful API.
It offers a lot of benefits over using raw regex syntax in strings:

 - No knowledge of regular expression syntax is required: just use simple, intuitively-named classes, methods and functions.
 - Code is easier to read, understand and maintain.
 - Code is safer and far less prone to regular expression syntax errors and programmer errors.

It is fully documented in the [project wiki](https://github.com/markwhitaker/RegexToolbox.kt/wiki).

## Breaking changes in 3.0

All `RegexBuilder` features that were deprecated in version 2.4 have been removed in 3.0.

|Removed|Replaced with|
|---|---|
|`RegexBuilder()` public constructor|`regex { ... }`|
|`buildRegex()`|`regex { ... }`|
|`buildPattern()`|`pattern { ... }`|
|`startGroup() ... endGroup()`|`group { ... }`|
|`startNamedGroup() ... endGroup()`|`namedGroup { ... }`|
|`startNonCapturingGroup() ... endGroup()`|`nonCapturingGroup { ... }`|
|`addLogger()`|No replacement: logging has been removed.|

In a nutshell: the old Java-style is completed removed in 3.0.
RegexToolbox.kt now exclusively uses the new [type-safe builder syntax](https://kotlinlang.org/docs/reference/type-safe-builders.html) introduced in 2.0, for example:

```kotlin
val regex = regex {
    group {
        letter()
        digit()
    } // Yay! Can't forget to close the group
} // Yay! No need to call buildRegex()
```

## Usage (Gradle)

RegexToolbox.kt is [hosted on JitPack](https://jitpack.io/#markwhitaker/RegexToolbox.kt).
Replace `x.y.z` with the latest version (shown in the JitPack badge at the top of this page).

```
repositories {
    ...
    maven { url 'https://jitpack.io' }
}

implementation 'com.github.markwhitaker:RegexToolbox.kt:x.y.z'
```

---
![icon](https://raw.githubusercontent.com/markwhitaker/RegexToolbox.Java/master/artwork/RegexToolbox-icon-32.png) **Java developer?** Check out the Java version of this library, [RegexToolbox.Java](https://github.com/markwhitaker/RegexToolbox.Java).

![icon](https://raw.githubusercontent.com/markwhitaker/RegexToolbox.NET/master/Artwork/RegexToolbox-icon-32.png) **.NET developer?** Check out the C# version of this library, [RegexToolbox.NET](https://github.com/markwhitaker/RegexToolbox.NET).

![icon](https://raw.githubusercontent.com/markwhitaker/RegexToolbox.JS/master/artwork/RegexToolbox-icon-32.png) **Web developer?** Check out the web version of this library, [RegexToolbox.JS](https://github.com/markwhitaker/RegexToolbox.JS).

---
###### **RegexToolbox:** Now you can be a [hero](https://xkcd.com/208/) without knowing regular expressions.
