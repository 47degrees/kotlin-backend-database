package com.fortysevendegrees.blogpost.db.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import se.ansman.kotshi.JsonSerializable

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

  companion object {
    const val ERROR_READING_PARAM = "Oops, there was an error reading one of the parameters"
  }

  override fun handleHttpMessageNotReadable(
    ex: HttpMessageNotReadableException,
    headers: HttpHeaders,
    status: HttpStatus,
    request: WebRequest
  ): ResponseEntity<Any> =
    ResponseEntity.badRequest().body(
      ApiError(
        HttpStatus.BAD_REQUEST,
        ERROR_READING_PARAM,
        listOf(ex.localizedMessage)
      )
    )

  @ExceptionHandler(Exception::class)
  fun handleAll(ex: Exception, request: WebRequest): ResponseEntity<Any> =
    ResponseEntity(
      ApiError(
        HttpStatus.I_AM_A_TEAPOT,
        ex.localizedMessage,
        emptyList()
      ),
      HttpStatus.I_AM_A_TEAPOT
    )
}

@JsonSerializable
data class ApiError(
  val status: HttpStatus,
  val message: String = "",
  val errors: List<String> = emptyList()
) {
  companion object {
    const val NAME_EMPTY = "Name should not be empty"
  }
}
