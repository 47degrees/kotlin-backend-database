package com.fortysevendegrees.blogpost.db.config

import com.fortysevendegrees.blogpost.db.model.PostBodyTechnology
import com.fortysevendegrees.blogpost.db.model.TechnologyEntities
import com.sksamuel.hoplite.ConfigLoader
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.sql.DataSource

@Configuration
@ComponentScan
class AppConfig : WebMvcConfigurer {

  @Bean
  fun provideDataSource(): DataSource =
    DataSourceBuilder.create()
      .apply {
        driverClassName(config.databaseConfig.driver)
        url(config.databaseConfig.url)
        username(config.databaseConfig.username)
        password(config.databaseConfig.password)
      }.build()

  @Bean
  fun provideDatabase(dataSource: DataSource): Database =
    Database.connect(dataSource)
      .also { transaction { SchemaUtils.create(TechnologyEntities) } }

  @Bean
  fun provideMoshi(): Moshi = Moshi.Builder()
    .add(ApplicationJsonAdapterFactory.INSTANCE)
    .build()

  @Bean
  fun provideJsonAdapterTechnology(moshi: Moshi): JsonAdapter<PostBodyTechnology> =
    moshi.adapter(PostBodyTechnology::class.java)
}

val config = ConfigLoader().loadConfigOrThrow<Config>("/application.conf")

data class Config(val databaseConfig: DatabaseConfig)
data class DatabaseConfig(val url: String, val username: String, val password: String, val driver: String)
