import javax.servlet.ServletContext

import com.jamesrodgers.todo.{ TodoListServlet, Database }
import org.scalatra._

class ScalatraBootstrap extends LifeCycle with Database {

  override def init(context: ServletContext) {
    configureDb()
    createDb()
    context.mount(new TodoListServlet, "/*")
  }

  override def destroy(context: ServletContext) {
    closeDbConnection()
  }
}
