package com.jamesrodgers.todo


case class TodoItem(
  // Id, unique identifier for each item. This should be created by the server on creation
  id: Long = 0,

  // Integer from 1 to 5 with the level of priority, where higher number means higher priority
  priority: Int,

  // Description of the task, it shouldn't be empty
  description: String,

  // Boolean to mark a task as it has been done
  isDone: Boolean = false
) 
