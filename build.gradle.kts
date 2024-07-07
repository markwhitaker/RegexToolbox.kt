import java.io.BufferedReader
import java.io.InputStreamReader

plugins {
    `java-library`
    kotlin("jvm") version "2.0.0"
}

val junitVersion = "4.13.2"

repositories {
    mavenCentral()
}

group = "uk.co.mainwave.regextoolbox"
version = getVersionFromGit()

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("junit:junit:$junitVersion")
}

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(11))
    }
}

fun getVersionFromGit(): String {
    val processBuilder = ProcessBuilder(listOf("git", "describe", "--tags", "--always"))
    try {
        val process = processBuilder.start()
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val output = StringBuilder()
        output.append(reader.readLine())
        process.waitFor()
        return output.toString()
    } catch (e: Exception) {
        return "1.0.0"
    }
}
