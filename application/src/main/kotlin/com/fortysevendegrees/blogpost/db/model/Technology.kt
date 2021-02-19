package com.fortysevendegrees.blogpost.db.model

import arrow.core.Validated
import arrow.core.invalid
import arrow.core.valid
import com.fortysevendegrees.blogpost.db.exception.ApiError
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class Technology(
  val id: Int,
  val name: String,
  val description: String,
  val keywords: List<String>
)

@JsonSerializable
data class PostBodyTechnology(
  val name: String,
  val description: String,
  val keywords: List<String>
)

fun PostBodyTechnology.validate(): Validated<ResponseEntity<ApiError>, PostBodyTechnology> =
  when {
    name.isEmpty() -> ResponseEntity.badRequest().body(ApiError(HttpStatus.BAD_REQUEST, ApiError.NAME_EMPTY)).invalid()
    else -> this.valid()
  }

fun TechnologyEntity.toDomain(): Technology =
  Technology(id.value, name, description, keywords)

// DAO
object TechnologyEntities : IntIdTable() {
  val name = varchar("name", 50).uniqueIndex()
  val description = varchar("description", 150)
  val keywords = text("keywords")
}

class TechnologyEntity(id: EntityID<Int>) : IntEntity(id) {
  companion object : IntEntityClass<TechnologyEntity>(TechnologyEntities) {
    const val SEPARATOR = ","
  }

  var name by TechnologyEntities.name
  var description by TechnologyEntities.description
  var keywords by TechnologyEntities.keywords.transform(
    toColumn = { it.joinToString(SEPARATOR) },
    toReal = { it.takeIf { it.trim().isNotEmpty() }?.split(SEPARATOR) ?: emptyList() }
  )
}
