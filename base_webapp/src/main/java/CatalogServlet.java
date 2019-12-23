import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Catalog page", urlPatterns = "/catalog")
public class CatalogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("<head><title>Каталог</title></head>");
        out.println("<body>");
        getServletContext().getRequestDispatcher("/topmenu.html").include(req, resp);
        out.println("<h1 align=\"center\">Каталог</h1>");
        out.println("</body>");
    }
}