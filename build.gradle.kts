import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version Version.kotlin apply false
  kotlin("kapt") version Version.kotlin apply false
  kotlin("plugin.spring") version Version.kotlin apply false
  kotlin("plugin.allopen") version Version.kotlin apply false
  id("org.springframework.boot") version Version.springBoot apply false
  id("org.jlleitschuh.gradle.ktlint") version Version.ktlint apply false
}

allprojects {
  apply(plugin = "org.jetbrains.kotlin.jvm")
  apply(plugin = "org.jlleitschuh.gradle.ktlint")

  group = "com.fortysevendegrees.blogpost.db"
  version = "0.0.1-SNAPSHOT"

  repositories {
    google()
    mavenCentral()
    jcenter()
    maven(url = "https://dl.bintray.com/arrow-kt/arrow-kt/")
    maven(url = "https://oss.jfrog.org/artifactory/oss-snapshot-local/") // for SNAPSHOT builds
  }

  dependencies {
    implementation(Libs.arrowFxCoroutines)
    testImplementation(Libs.kotestRunner)
    testImplementation(Libs.kotestAssertions)
    testImplementation(Libs.kotestProperty)
  }

  tasks.withType<KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs = listOf("-Xjsr305=strict")
      jvmTarget = "1.8"
    }
  }

  tasks.withType<Test> {
    useJUnitPlatform()
  }
}
