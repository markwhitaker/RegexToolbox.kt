![icon](artwork/RegexToolbox-icon-100.png)
# RegexToolbox.kt [![CircleCI](https://circleci.com/gh/markwhitaker/RegexToolbox.kt.svg?style=shield)](https://circleci.com/gh/markwhitaker/RegexToolbox.kt) [![Download](https://api.bintray.com/packages/markwhitaker/Maven/regextoolbox-kotlin/images/download.svg) ](https://bintray.com/markwhitaker/Maven/regextoolbox-kotlin/_latestVersion)

Regular expression tools for Kotlin developers.

## Regular expressions made easy

RegexToolbox allows you to build regular expressions in a more human-readable way using a lightweight, powerful API.
It offers a lot of benefits over using raw regex syntax in strings:

 - No knowledge of regular expression syntax is required: just use simple, intuitively-named classes, methods and functions.
 - Code is easier to read, understand and maintain.
 - Code is safer and far less prone to regular expression syntax errors and programmer errors.

It is fully documented in the [project wiki](https://github.com/markwhitaker/RegexToolbox.kt/wiki).

## New in version 2.0

### New builder syntax

Version 2.0 introduces a simplified [type-safe builder syntax](https://kotlinlang.org/docs/reference/type-safe-builders.html) for cleaner, less error-prone and more Kotliny code. Go from this:

```kotlin
val regex = RegexBuilder()
    .startGroup()
    .text()
    .digit()
    .buildRegex() // ERROR: forgot to call endGroup()
```

to this:

```kotlin
val regex = regex {
    group {
        text()
        digit()
    } // Yay! Can't forget to close the group
} // Yay! No need to call buildRegex()
```

The old syntax is still supported if that's your preference (and for consistency with the sibling projects listed at the bottom of the page).

### Quantifiers

`RegexQuantifier` is now a sealed class and its methods have become objects or classes. The old syntax is still supported but deprecated and will be removed in a future version.

|Old syntax|becomes|
|---|---|
|`RegexQuantifier.zeroOrOne()`|`RegexQuantifier.ZeroOrOne`|
|`RegexQuantifier.zeroOrMore()`|`RegexQuantifier.ZeroOrMore`|
|`RegexQuantifier.oneOrMore()`|`RegexQuantifier.OneOrMore`|
|`RegexQuantifier.exactly(1)`|`RegexQuantifier.Exactly(1)`|
|`RegexQuantifier.atLeast(1)`|`RegexQuantifier.AtLeast(1)`|
|`RegexQuantifier.noMoreThan(1)`|`RegexQuantifier.NoMoreThan(1)`|
|`RegexQuantifier.between(1,2)`|`RegexQuantifier.Between(1,2)`|
|`[quantifier].butAsFewAsPossible()`|`[quantifier].butAsFewAsPossible`|

## New in 2.2: Logging

Use the new `addLogger()` method to connect a logger of your choice and see how your regex is built, step by step. For example:

```kotlin
val regex = RegexBuilder()
    .addLogger {
        println(it)
    }
    .wordBoundary()
    .text("Regex")
    .anyOf("Builder", "Toolbox")
    .wordBoundary()
    .buildRegex()
```

or this:

```kotlin
val regex = regex {
    addLogger {
        println(it)
    }
    wordBoundary()
    text("Regex")
    anyOf("Builder", "Toolbox")
    wordBoundary()
}
```

will output this to your console:

```text
RegexBuilder: wordBoundary(): \b
RegexBuilder: text("Regex"): Regex
RegexBuilder: anyOf("Builder", "Toolbox"): (?:Builder|Toolbox)
RegexBuilder: wordBoundary(): \b
RegexBuilder: buildRegex(): \bRegex(?:Builder|Toolbox)\b
```

## Usage (Gradle)

Replace `x.y.z` with the latest version.

```implementation 'uk.co.mainwave.regextoolbox:regextoolbox-kotlin:x.y.z'```

---
![icon](https://raw.githubusercontent.com/markwhitaker/RegexToolbox.Java/master/artwork/RegexToolbox-icon-32.png) **Java developer?** Check out the Java version of this library, [RegexToolbox.Java](https://github.com/markwhitaker/RegexToolbox.Java).

![icon](https://raw.githubusercontent.com/markwhitaker/RegexToolbox.NET/master/Artwork/RegexToolbox-icon-32.png) **.NET developer?** Check out the C# version of this library, [RegexToolbox.NET](https://github.com/markwhitaker/RegexToolbox.NET).

![icon](https://raw.githubusercontent.com/markwhitaker/RegexToolbox.JS/master/artwork/RegexToolbox-icon-32.png) **Web developer?** Check out the web version of this library, [RegexToolbox.JS](https://github.com/markwhitaker/RegexToolbox.JS).

---
###### **RegexToolbox:** Now you can be a [hero](https://xkcd.com/208/) without knowing regular expressions.
