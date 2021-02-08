package com.fortysevendegrees.blogpost.db.config

import com.fortysevendegrees.blogpost.db.BlogpostDb
import com.fortysevendegrees.blogpost.db.algebra.TechnologyAlgebra
import com.fortysevendegrees.blogpost.db.entity.TechnologyEntity
import com.fortysevendegrees.blogpost.db.model.PostBodyTechnology
import com.sksamuel.hoplite.ConfigLoader
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.asJdbcDriver
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
        driverClassName(config.database.driver)
        url(config.database.url)
        username(config.database.username)
        password(config.database.password)
      }.build()

  @Bean
  fun provideJdbcDriver(dataSource: DataSource): SqlDriver =
    dataSource.asJdbcDriver().apply {
      BlogpostDb.Schema.create(this)
    }

  @Bean
  fun provideDatabase(driver: SqlDriver, techAdapter: TechnologyEntity.Adapter): BlogpostDb =
    BlogpostDb(driver, techAdapter)

  @Bean
  fun provideTechnologyAlgebra(
    database: BlogpostDb,
    driver: SqlDriver,
    stringListAdapter: ColumnAdapter<List<String>, String>
  ): TechnologyAlgebra =
    TechnologyAlgebra.invoke(database, driver, stringListAdapter)

  @Bean
  fun provideStringListColumnAdapter(): ColumnAdapter<List<String>, String> =
    adapter(
      encode = { it.joinToString(separator = ",") },
      decode = { it.takeIf { it.trim().isNotEmpty() }?.split(",") ?: emptyList() }
    )

  @Bean
  fun provideTechnologyKeywordAdapter(keywords: ColumnAdapter<List<String>, String>): TechnologyEntity.Adapter =
    TechnologyEntity.Adapter(keywords)

  @Bean
  fun provideMoshi(): Moshi = Moshi.Builder()
    .add(ApplicationJsonAdapterFactory.INSTANCE)
    .build()

  @Bean
  fun provideJsonAdapterTechnology(moshi: Moshi): JsonAdapter<PostBodyTechnology> =
    moshi.adapter(PostBodyTechnology::class.java)
}

inline fun <A : Any, B> adapter(
  crossinline encode: (A) -> B,
  crossinline decode: (B) -> A
): ColumnAdapter<A, B> =
  object : ColumnAdapter<A, B> {
    override fun decode(databaseValue: B): A =
      decode(databaseValue)

    override fun encode(value: A): B =
      encode(value)
  }

val config = ConfigLoader().loadConfigOrThrow<Config>("/application.conf")

data class Config(val database: Database)
data class Database(val url: String, val username: String, val password: String, val driver: String)
