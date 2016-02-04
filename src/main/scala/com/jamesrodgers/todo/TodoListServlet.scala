package com.jamesrodgers.todo

import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json._


class TodoListServlet extends ScalatraServlet with JacksonJsonSupport with JValueResult {
  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  get("/todos") {
    Ok("[]")
  }

  post("/todos") {
    Created()
  }
}
