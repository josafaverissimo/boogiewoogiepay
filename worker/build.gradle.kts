import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes

plugins {
  application
  id("com.gradleup.shadow") version "8.3.8"
  id("org.graalvm.buildtools.native") version "0.10.6"
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
    vendor = JvmVendorSpec.ORACLE
  }
}

application {
  mainClass = "com.josafaverissimo.worker.Worker"
}

graalvmNative {
  agent {
    enabled.set(true)
    metadataCopy {
      inputTaskNames.add("run")
      outputDirectories.add("src/main/resources/META-INF/native-image/com.josafaverissimo.worker")
      mergeWithExisting.set(true)
    }
  }
}

tasks.named<Test>("test") {
  useJUnitPlatform()
}

tasks.register("copyMetadata") {
  val agentOutput = file("build/native/agent-output")
  val destinationFile = file("src/main/resources/META-INF/native-image/reachability.json")

  doLast {
    val latestFile = agentOutput.walkTopDown()
    .filter { it.isFile && it.name == "reachability-metadata.json" }
    .maxByOrNull {
      Files.readAttributes(it.toPath(), BasicFileAttributes::class.java).lastModifiedTime()
    }

    if (latestFile != null) {
      println("Copying reachability-metadata.json from: ${latestFile.absolutePath}")
      destinationFile.parentFile.mkdirs()
      latestFile.copyTo(destinationFile, overwrite = true)
      println("Paste into: ${destinationFile.absolutePath}")
    } else {
      println("No reachability-metadata.json file found in build/native/agent-output")
    }
  }
}
