import java.net.URI

plugins {
    kotlin("jvm") version "1.9.21"
    id("maven-publish")
}

apply {
    plugin("maven-publish")
}

group = "com.github.abdullahkhan118"
version = "1.0.0"

repositories {
    mavenCentral()
    maven { url = URI.create("https://jitpack.io") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
    api("com.github.abdullahkhan118:KotlinExtensions:1.0.8")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}


afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["java"])
                groupId = "com.github.abdullahkhan118"
                artifactId = "kotlin.extensions"
                version = "1.0.0"
            }
        }
        repositories {
            mavenLocal()
        }
    }
}