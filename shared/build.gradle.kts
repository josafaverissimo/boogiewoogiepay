plugins {
  application
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
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(24)
  }
}

application {
  mainClass = "com.josafaverissimo.shared.App"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
