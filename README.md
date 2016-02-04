# simple-todo

## Built with

- `sbt.version=0.13.9`
- `scalaVersion := "2.11.7"`
- Oracle Java SDK 1.8.0_72

## Compile and Test

`sbt test`

## Start server

Enter sbt console with `sbt`

Start jetty with `jetty:start`

## Use cases

### Add a todo item
`curl -H "Content-Type: application/json" -X POST -d '{"id": 0, "priority":3,"description":"Smallish One", "isDone": false}' http://localhost:8080/todos`

`id` must be `0` or absent.

`isDone` must be `false` or absent.

### List todo items

`curl 'http://localhost:8080/todos'`
