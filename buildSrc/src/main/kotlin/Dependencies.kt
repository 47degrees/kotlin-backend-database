object Version {
  const val kotlin = "1.4.10"
  const val springBoot = "2.4.1"
  const val arrow = "0.12.0-SNAPSHOT"
  const val kotest = "4.3.1"
  const val kotlinx = "1.4.2"
  const val ktlint = "9.4.1"
}

object Libs {
  const val springBootStarter = "org.springframework.boot:spring-boot-starter"
  const val springBootStarterWeb = "org.springframework.boot:spring-boot-starter-web"
  const val springBootStarterValidation = "org.springframework.boot:spring-boot-starter-validation"
  const val springBootStarterJdbc = "org.springframework.boot:spring-boot-starter-jdbc"
  const val springBootConfigurationProcessor =
    "org.springframework.boot:spring-boot-configuration-processor:${Version.springBoot}"

  const val kotlinJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
  const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect"
  const val kotlinxCoroutinesJdk8 = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${Version.kotlinx}"

  const val arrowFxCoroutines = "io.arrow-kt:arrow-fx-coroutines:${Version.arrow}"

  const val springBootStarterTest = "org.springframework.boot:spring-boot-starter-test"
  const val kotestRunner = "io.kotest:kotest-runner-junit5:${Version.kotest}"
  const val kotestAssertions = "io.kotest:kotest-assertions-core:${Version.kotest}"
  const val kotestProperty = "io.kotest:kotest-property:${Version.kotest}"
}

const val implementation = "implementation"
const val testImplementation = "testImplementation"
const val runtime = "runtime"
