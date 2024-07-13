plugins {
    `java-library`
    kotlin("jvm") version "2.0.0"
}

val junitVersion = "4.13.2"

repositories {
    mavenCentral()
}

group = "uk.co.mainwave.regextoolbox"
version = getGitVersion()

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("junit:junit:$junitVersion")
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

fun getGitVersion(): String {
    try {
        val process = ProcessBuilder("git", "describe", "--tags", "--always").start()
        val output = process.inputStream.bufferedReader().readLine()
        process.waitFor()
        return output
    } catch (e: Exception) {
        return "1.0.0"
    }
}
