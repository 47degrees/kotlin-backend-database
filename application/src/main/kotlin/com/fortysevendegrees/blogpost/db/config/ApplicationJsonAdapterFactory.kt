package com.fortysevendegrees.blogpost.db.config

import com.squareup.moshi.JsonAdapter
import se.ansman.kotshi.KotshiJsonAdapterFactory

@KotshiJsonAdapterFactory
abstract class ApplicationJsonAdapterFactory : JsonAdapter.Factory {
  companion object {
    val INSTANCE: ApplicationJsonAdapterFactory = KotshiApplicationJsonAdapterFactory
  }
}
