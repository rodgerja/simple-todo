package com.jamesrodgers.todo

import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.{write => writeJson}
import org.scalatest.{BeforeAndAfter, FunSuiteLike}
import org.scalatra.test.scalatest.ScalatraSuite

class TodoListServletTest extends ScalatraSuite with FunSuiteLike with BeforeAndAfter with Database {
  implicit val formats = DefaultFormats
  addServlet(classOf[TodoListServlet], "/*")

  before {
    configureDb()
      createDb()
  }

  test("GET returns 200 and empty list on empty database") {
    get("/todos") {
      status should equal(200)
      body should equal("[]")
    }
  }

  test("POST should store item and assign id and default 'isDone' to false") {
    val newItem = TodoItem(id = 0, priority = 1, description = "Important one")
    val expectedItem = TodoItem(id = 1, priority = 1, description = "Important one", isDone = false)

    post("/todos", writeJson(newItem)) {
      status should equal(201)
      val returnedItem = parse(body).extract[TodoItem]
      itemsAreEqual(expectedItem, returnedItem) should be(true)
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

      itemsAreEqual(expectedSavedItem1, returnedItems(0)) should be(true)
      itemsAreEqual(expectedSavedItem2, returnedItems(1)) should be(true)
    }
  }

  // TODO: priority constraints

  def itemsAreEqual(item1: TodoItem, item2: TodoItem): Boolean = {
    item1.id == item2.id &&
    item1.priority == item2.priority &&
    item1.description == item2.description &&
    item1.isDone == item2.isDone
  }
}
