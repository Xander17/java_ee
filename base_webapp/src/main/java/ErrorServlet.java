import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ErrorServlet", urlPatterns = "/error")
public class ErrorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("<head><title>Ошибка</title></head>");
        getServletContext().getRequestDispatcher("/topmenu.html").include(req, resp);
        out.println("<h1 align=\"center\">Упс.. где-то ошибка</h1><p align=\"center\">Страница не найдена</p>");
    }
}
