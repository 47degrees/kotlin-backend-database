package com.fortysevendegrees.blogpost.db.algebra

import com.fortysevendegrees.blogpost.db.model.PostBodyTechnology
import com.fortysevendegrees.blogpost.db.model.Technology
import com.fortysevendegrees.blogpost.db.model.TechnologyEntity
import com.fortysevendegrees.blogpost.db.model.toDomain
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.springframework.stereotype.Component

interface TechnologyAlgebra {

  suspend fun save(element: PostBodyTechnology): Technology

  suspend fun findAll(): List<Technology>

  @Component
  companion object : TechnologyAlgebra {
    override suspend fun save(element: PostBodyTechnology): Technology =
      newSuspendedTransaction(Dispatchers.IO) {
        TechnologyEntity.new {
          name = element.name
          description = element.description
          keywords = element.keywords
        }.toDomain()
      }

    override suspend fun findAll(): List<Technology> =
      newSuspendedTransaction(Dispatchers.IO) {
        TechnologyEntity.all().map { it.toDomain() }
      }
  }
}
