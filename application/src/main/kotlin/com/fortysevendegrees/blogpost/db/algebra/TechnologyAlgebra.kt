package com.fortysevendegrees.blogpost.db.algebra

import com.fortysevendegrees.blogpost.db.BlogpostDb
import com.fortysevendegrees.blogpost.db.entity.TechnologyEntity
import com.fortysevendegrees.blogpost.db.model.PostBodyTechnology
import com.fortysevendegrees.blogpost.db.model.Technology
import com.fortysevendegrees.blogpost.db.model.toDomain

interface TechnologyAlgebra {

  suspend fun save(element: PostBodyTechnology): Unit

  suspend fun findByNameOrNull(name: String): Technology?

  suspend fun findAll(): List<Technology>

  companion object {
    operator fun invoke(database: BlogpostDb) = object : TechnologyAlgebra {
      override suspend fun save(element: PostBodyTechnology): Unit =
        database.technologyEntityQueries.insert(
          name = element.name,
          description = element.description,
          keywords = element.keywords
        )

      override suspend fun findByNameOrNull(name: String): Technology? =
        database.technologyEntityQueries.selectByName(name).executeAsOneOrNull()?.toDomain()

      override suspend fun findAll(): List<Technology> =
        database.technologyEntityQueries.selectAll().executeAsList().map(TechnologyEntity::toDomain)
    }
  }
}
