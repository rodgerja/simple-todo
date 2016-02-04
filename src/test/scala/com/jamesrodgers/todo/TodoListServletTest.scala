package com.jamesrodgers.todo

import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.{write => writeJson}
import org.scalatest.{BeforeAndAfter, FunSuiteLike}
import org.scalatra.test.scalatest.ScalatraSuite

class TodoListServletTest extends ScalatraSuite with FunSuiteLike with BeforeAndAfter {
  implicit val formats = DefaultFormats
  addServlet(classOf[TodoListServlet], "/*")

  test("GET returns 200 and empty list on empty database") {
    get("/todos") {
      status should equal(200)
      body should equal("[]")
    }
  }

  test("POST should store item and assign id") {
    val newItem = TodoItem(id = 0, priority = 1, description = "Important one")
    val expectedItem = TodoItem(id = 1, priority = 1, description = "Important one", isDone = false)

    post("/todos", writeJson(newItem)) {
      status should equal(201)
      val returnedItem = parse(body).extract[TodoItem]

      expectedItem should equal(returnedItem)
    }
  }

  test("Can retrieve stored items") {
    val newItem1 = TodoItem(priority = 1, description = "Important one")
    val newItem2 = TodoItem(priority = 5, description = "Unimportant one")

    val expectedSavedItem1 = TodoItem(id = 1, priority = 1, description = "Important one", isDone = false)
    val expectedSavedItem2 = TodoItem(id = 2, priority = 5, description = "Unimportant one", isDone = false)

    post("/todos", writeJson(newItem1)) {}
    post("/todos", writeJson(newItem2)) {}

    get("/todos") {
      status should equal(200)
      val returnedItems = parse(body).extract[List[TodoItem]]
      // TODO: assert equality
    }
  }
}
