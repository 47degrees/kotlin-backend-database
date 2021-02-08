import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  idea
  kotlin("kapt")
  kotlin("plugin.spring")
  kotlin("plugin.allopen")
  id("org.springframework.boot")
  id("com.squareup.sqldelight")
}

sqldelight {
  database("BlogpostDb") {
    // Package name used for the generated BlogpostDb.kt
    packageName = "com.fortysevendegrees.blogpost.db"
    dialect = "postgresql"
  }
}

// Generates database interface at compile-time
tasks.withType<KotlinCompile> {
  dependsOn("generateMainBlogpostDbInterface")
}

// Do not check ktlint for SqlDelight generated code
ktlint {
  filter {
    exclude { projectDir.toURI().relativize(it.file.toURI()).path.contains("/generated/") }
  }
}

dependencies {
  implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))

  implementation(Libs.kotlinxCoroutinesJdk8)

  implementation(Libs.kotlinJdk8)
  implementation(Libs.kotlinReflect)

  implementation(Libs.springBootStarter)
  implementation(Libs.springBootStarterWeb)
  kapt(Libs.springBootConfigurationProcessor)
  implementation(Libs.springBootStarterValidation)
  implementation(Libs.springBootStarterJdbc)

  // Arrow
  implementation(Libs.arrowFxCoroutines)

  // SqlDelight
  implementation(Libs.sqlDelightJdbcDriver)

  // Postgresql
  runtimeOnly(Libs.postgresql)

  // Flyway
  implementation(Libs.flywayCore)

  // Type-safe Config
  implementation(Libs.hoplite)

  // Moshi + Kotshi
  implementation(Libs.moshiKotlin)
  kapt(Libs.moshiKotlinCodegen)
  implementation(Libs.moshiAdapters)
  implementation(Libs.kotshiApi)
  kapt(Libs.kotshiCompiler)

  testImplementation(Libs.springBootStarterTest) {
    exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    exclude(module = "junit")
    exclude(module = "mockito-core")
  }
}
