import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Product page", urlPatterns = "/product")
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("<head><title>Товар</title></head>");
        out.println("<body>");
        getServletContext().getRequestDispatcher("/topmenu.html").include(req, resp);
        out.println("<h1 align=\"center\">Товар</h1>");
        out.println("</body>");
    }
}
