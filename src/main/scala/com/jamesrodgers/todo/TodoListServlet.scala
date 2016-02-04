package com.jamesrodgers.todo

import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json._
import org.squeryl.PrimitiveTypeMode._
import TodoListSchema.todo_items
import org.slf4j.LoggerFactory


class TodoListServlet extends ScalatraServlet with JacksonJsonSupport with JValueResult {
  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  val logger = LoggerFactory.getLogger(getClass)

  before() {
    contentType = formats("json")
  }

  get("/todos") {
      val retrievedItems = transaction(from(todo_items)(select(_)).toList)
      Ok(retrievedItems)
  }

  post("/todos") {
    val parsedItem = parsedBody.extract[TodoItem]
    val savedItem = transaction(todo_items.insert(parsedItem))

    Created(savedItem)
  }
}
