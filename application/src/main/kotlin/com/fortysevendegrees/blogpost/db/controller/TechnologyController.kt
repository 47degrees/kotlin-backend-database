package com.fortysevendegrees.blogpost.db.controller

import com.fortysevendegrees.blogpost.db.algebra.TechnologyAlgebra
import com.fortysevendegrees.blogpost.db.model.PostBodyTechnology
import com.fortysevendegrees.blogpost.db.model.Technology
import com.fortysevendegrees.blogpost.db.model.validate
import com.fortysevendegrees.blogpost.db.utils.future
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.concurrent.CompletableFuture
import javax.servlet.http.HttpServletRequest

@RestController
class TechnologyController(
  private val technologyAlgebra: TechnologyAlgebra
) {

  @PostMapping("/technology")
  fun saveTechnology(
    @RequestBody technology: PostBodyTechnology,
    request: HttpServletRequest
  ): CompletableFuture<ResponseEntity<*>> =
    future {
      technology.validate().fold(
        { it },
        {
          val savedTechnology = technologyAlgebra.save(it)
          ResponseEntity
            .created(URI("${request.requestURL}${savedTechnology.id}"))
            .body(savedTechnology)
        }
      )
    }

  @GetMapping("/technology")
  fun findAllTechnologies(): CompletableFuture<ResponseEntity<List<Technology>>> =
    future {
      ResponseEntity.ok(technologyAlgebra.findAll())
    }
}
