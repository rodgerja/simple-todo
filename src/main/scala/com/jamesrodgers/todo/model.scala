package com.jamesrodgers.todo

import org.squeryl.KeyedEntity


case class TodoItem(
  // Id, unique identifier for each item. This should be created by the server on creation
  id: Long = 0,

  // Integer from 1 to 5 with the level of priority, where higher number means higher priority
  priority: Int,

  // Description of the task, it shouldn't be empty
  description: String,

  // Boolean to mark a task as it has been done
  isDone: Boolean = false
) extends KeyedEntity[Long] {

  def validateNew(): TodoItem = {
    if (id != 0)
      throw new ItemFieldsException("Unsaved todo items must have id equal to 0")
    if (priority < 1 || priority > 5)
      throw new ItemFieldsException("Priority must be between 1 and 5 inclusive")
    if (isDone) throw new ItemFieldsException("Unsaved todo items must have 'isDone' set to false")

    return this
  }
}

case class ItemFieldsException(msg: String) extends Exception(msg)
