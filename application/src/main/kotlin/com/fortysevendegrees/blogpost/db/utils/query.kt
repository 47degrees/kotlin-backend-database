package com.fortysevendegrees.blogpost.db.utils

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.db.SqlCursor
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.db.SqlPreparedStatement
import com.squareup.sqldelight.internal.copyOnWriteList

/**
 * @param sqlQuery without ; at the end
 * @param map index starts with 0
 * @param identifier use only if performance or caching techniques are used
 * @param bind here the index starts with 1
 */
fun <T : Any> SqlDriver.query(
  sqlQuery: String,
  identifier: Int? = null,
  queries: MutableList<Query<*>> = copyOnWriteList(),
  parameterCount: Int,
  map: SqlCursor.() -> T,
  bind: SqlPreparedStatement.() -> Unit
): Query<T> =
  object : Query<T>(queries, map) {
    override fun execute(): SqlCursor =
      executeQuery(identifier, sqlQuery, parameterCount, bind)

    override fun toString(): String = sqlQuery
  }
