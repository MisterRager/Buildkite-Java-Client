import java.net.URI

plugins {
    kotlin("jvm")
}

repositories {
    jcenter()
    google()
    maven {
        url = URI("https://plugins.gradle.org/m2/")
    }
}


dependencies {
    // Injection
    implementation("org.kodein.di:kodein-di-jx-inject-jvm:5.2.0")

    implementation(kotlin("stdlib"))

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.9")
    implementation("com.fasterxml.jackson.module:jackson-module-mrbean:2.9.9")

    implementation("com.squareup.retrofit2:retrofit:2.6.1")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.6.1")
    implementation("com.squareup.retrofit2:converter-jackson:2.6.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.7")

    testImplementation(project(":buildkiteclient"))

    // JUnit
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
}

group = "es.lolrav"
version = "0.0.1"
