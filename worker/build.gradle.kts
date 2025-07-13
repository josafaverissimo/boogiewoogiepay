plugins {
  application
  id("com.gradleup.shadow") version "8.3.8"
}

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(libs.junit.jupiter)
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")

  implementation(libs.slf4j.simple)
  implementation(libs.dotenv.java)
  implementation(libs.jackson.databind)
  implementation(libs.valkey.java)

  implementation(project(":shared"))
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(24)
  }
}

application {
  mainClass = "com.josafaverissimo.worker.Worker"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

