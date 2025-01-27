plugins {
    `java-library`
    kotlin("jvm") version "2.1.10"
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
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

fun getGitVersion() = try {
    val process = ProcessBuilder("git", "describe", "--tags", "--always").start()
    val output = process.inputStream.bufferedReader().readLine()
    process.waitFor()
    output
} catch (_: Exception) {
    "1.0.0"
}
