package com.fortysevendegrees.blogpost.db.algebra

import arrow.core.computations.nullable
import com.fortysevendegrees.blogpost.db.BlogpostDb
import com.fortysevendegrees.blogpost.db.entity.TechnologyEntity
import com.fortysevendegrees.blogpost.db.model.PostBodyTechnology
import com.fortysevendegrees.blogpost.db.model.Technology
import com.fortysevendegrees.blogpost.db.model.toDomain
import com.fortysevendegrees.blogpost.db.utils.query
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.db.SqlPreparedStatement

interface TechnologyAlgebra {

  suspend fun save(element: PostBodyTechnology): Technology?

  suspend fun findByNameOrNull(name: String): Technology?

  suspend fun findAll(): List<Technology>

  companion object {
    operator fun invoke(
      database: BlogpostDb,
      driver: SqlDriver,
      stringListAdapter: ColumnAdapter<List<String>, String>
    ) = object : TechnologyAlgebra {
      override suspend fun save(element: PostBodyTechnology): Technology? =
        saveReturnQuery(element).executeAsOneOrNull()?.toDomain()

      private fun saveReturnQuery(element: PostBodyTechnology): Query<TechnologyEntity> =
        technologyQuery(
          """
          |INSERT INTO technologyEntity(name, description, tags)
          |VALUES (?, ?, ?)
          |RETURNING *
          """.trimMargin(),
          parameterCount = 3
        ) {
          element.run {
            bindString(1, name)
            bindString(2, description)
            bindString(3, stringListAdapter.encode(tags))
          }
        }

      override suspend fun findByNameOrNull(name: String): Technology? =
        database.technologyEntityQueries.selectByName(name).executeAsOneOrNull()?.toDomain()

      override suspend fun findAll(): List<Technology> =
        database.technologyEntityQueries.selectAll().executeAsList().map(TechnologyEntity::toDomain)

      private fun technologyQuery(
        sqlQuery: String,
        parameterCount: Int,
        bind: SqlPreparedStatement.() -> Unit
      ): Query<TechnologyEntity> =
        driver.query(
          sqlQuery,
          parameterCount = parameterCount,
          map = {
            nullable.eager {
              TechnologyEntity(
                getLong(0)?.toInt()(),
                getString(1)(),
                getString(2)(),
                stringListAdapter.decode(getString(3)())
              )
            }!!
          },
          bind = bind
        )
    }
  }
}
