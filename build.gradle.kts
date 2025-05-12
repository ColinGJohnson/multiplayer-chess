plugins {
    id("java")
    id("application")
    kotlin("jvm") version "2.1.10"
}

group = "dev.cgj"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    // CLI arguments
    implementation("info.picocli:picocli:4.7.7")

    // Logging
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("org.slf4j:slf4j-simple:2.0.9")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}