package com.fortysevendegrees.blogpost.db.model

import arrow.core.Validated
import arrow.core.invalid
import arrow.core.valid
import com.fortysevendegrees.blogpost.db.entity.TechnologyEntity
import com.fortysevendegrees.blogpost.db.exception.ApiError
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
  Technology(id, name, description, keywords)
