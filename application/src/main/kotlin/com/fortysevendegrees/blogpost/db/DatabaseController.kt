package com.fortysevendegrees.blogpost.db

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DatabaseController {

  @RequestMapping("/")
  fun helloFolks(): String = "Hello folks!!"
}
