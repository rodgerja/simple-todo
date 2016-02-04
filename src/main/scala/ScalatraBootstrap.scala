import javax.servlet.ServletContext

import com.jamesrodgers.todo.TodoListServlet
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {

  override def init(context: ServletContext) {
    context.mount(new TodoListServlet, "/*")
  }
}
