package controllers;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Pages {
    private Pages() {
    }

    public static void defaultPageForward(ServletContext servletContext, HttpServletRequest req, HttpServletResponse resp, String title, String contentJSP) throws ServletException, IOException {
        req.setAttribute("content", contentJSP);
        req.setAttribute("pageTitle", title);
        servletContext.getRequestDispatcher("/default_page.jsp").forward(req, resp);
    }
}
