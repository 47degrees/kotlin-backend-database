plugins {
  idea
  kotlin("kapt")
  kotlin("plugin.spring")
  kotlin("plugin.allopen")
  id("org.springframework.boot")
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

  // Exposed
  implementation(Libs.exposedCore)
  implementation(Libs.exposedDao)
  implementation(Libs.exposedJdbc)

  // Postgresql
  runtimeOnly(Libs.postgresql)

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
