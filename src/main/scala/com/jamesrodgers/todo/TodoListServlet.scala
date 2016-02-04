package com.jamesrodgers.todo

import org.json4s.{DefaultFormats, Formats, MappingException}
import org.scalatra._
import org.scalatra.json._
import org.squeryl.PrimitiveTypeMode._
import TodoListSchema.todo_items
import org.slf4j.LoggerFactory
import scala.util.{Failure, Success, Try}


class TodoListServlet extends ScalatraServlet with JacksonJsonSupport with JValueResult {
  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  val logger = LoggerFactory.getLogger(getClass)

  before() {
    contentType = formats("json")
  }

  get("/todos") {
    Try {
      transaction {
        from(todo_items)(select(_)).toList
      }
    } match {
      case Failure(error) => InternalServerError(error)
      case Success(todo_items) => Ok(todo_items)
    }
  }

  post("/todos") {
    val parsedItem = parsedBody.extract[TodoItem]
    val savedItem = transaction(todo_items.insert(parsedItem))

    Created(savedItem)
  }

  post("/todos") {
    val newItemTry = for {
      parsedItem <- Try(parsedBody.extract[TodoItem])
      validatedItem <- Try(parsedItem.validateNew())
      savedItem <- Try(transaction(todo_items.insert(validatedItem)))
    } yield savedItem

    newItemTry match {
      case Failure(badItemFields: ItemFieldsException) => BadRequest(badItemFields)
      case Failure(parseError: MappingException) => BadRequest(parseError)
      case Failure(dbError) => InternalServerError(dbError)
      case Success(todo) => Created(todo)
    }
  }
}
