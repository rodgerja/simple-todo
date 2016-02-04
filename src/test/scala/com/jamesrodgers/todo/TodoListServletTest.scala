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
      body should be("[]")
    }
  }

  test("POST should store item") {
    post("/todos", "{}") {
      status should equal(201)
    }
  }
}
