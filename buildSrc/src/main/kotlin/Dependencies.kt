object Version {
  const val kotlin = "1.4.10"
  const val springBoot = "2.4.1"
  const val arrow = "0.12.0-SNAPSHOT"
  const val moshi = "1.9.2"
  const val kotshi = "2.3.0"
  const val kotest = "4.3.1"
  const val kotlinx = "1.4.2"
  const val ktlint = "9.4.1"
  const val hoplite = "1.3.9"
  const val sqlDelight = "1.4.4"
  const val flyway = "7.4.0"
}

object Libs {
  const val springBootStarter = "org.springframework.boot:spring-boot-starter"
  const val springBootStarterWeb = "org.springframework.boot:spring-boot-starter-web"
  const val springBootStarterValidation = "org.springframework.boot:spring-boot-starter-validation"
  const val springBootStarterJdbc = "org.springframework.boot:spring-boot-starter-jdbc"
  const val springBootConfigurationProcessor =
    "org.springframework.boot:spring-boot-configuration-processor:${Version.springBoot}"

  const val sqlDelightJdbcDriver = "com.squareup.sqldelight:jdbc-driver:${Version.sqlDelight}"
  const val postgresql = "org.postgresql:postgresql"
  const val flywayCore = "org.flywaydb:flyway-core"

  const val kotlinJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
  const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect"
  const val kotlinxCoroutinesJdk8 = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${Version.kotlinx}"

  const val hoplite = "com.sksamuel.hoplite:hoplite-hocon:${Version.hoplite}"

  const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Version.moshi}"
  const val moshiKotlinCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Version.moshi}"
  const val moshiAdapters = "com.squareup.moshi:moshi-adapters:${Version.moshi}"
  const val kotshiApi = "se.ansman.kotshi:api:${Version.kotshi}"
  const val kotshiCompiler = "se.ansman.kotshi:compiler:${Version.kotshi}"

  const val arrowFxCoroutines = "io.arrow-kt:arrow-fx-coroutines:${Version.arrow}"

  const val springBootStarterTest = "org.springframework.boot:spring-boot-starter-test"
}

const val implementation = "implementation"
const val testImplementation = "testImplementation"
const val runtime = "runtime"
