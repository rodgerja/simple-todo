package com.jamesrodgers.todo

import com.mchange.v2.c3p0.ComboPooledDataSource
import org.slf4j.LoggerFactory
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.adapters.H2Adapter
import org.squeryl.{Schema, Session, SessionFactory}

trait Database {
  val logger = LoggerFactory.getLogger(getClass)

  val user = "root"
  val password = ""
  val connectionString = "jdbc:h2:mem:todo"

  var dataSource = new ComboPooledDataSource

  def configureDb() {
    dataSource.setDriverClass("org.h2.Driver")
    dataSource.setJdbcUrl(connectionString)
    dataSource.setUser(user)
    dataSource.setPassword(password)

    SessionFactory.concreteFactory = Some(() =>
      Session.create(dataSource.getConnection, new H2Adapter))
  }

  def createDb() {
    transaction {
      TodoListSchema.create
      logger.info("Created db")
    }
  }

  def closeDbConnection() {
    logger.info("Closing connection pool pool")
    dataSource.close()
  }
}

object TodoListSchema extends Schema {
  val todo_items = table[TodoItem]

  on(todo_items) { item =>
    declare(
      item.id is(unique, indexed, autoIncremented))
  }
}
