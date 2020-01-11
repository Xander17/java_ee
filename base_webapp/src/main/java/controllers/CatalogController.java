package controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import db.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PageController", urlPatterns = "/catalog/*")
public class CatalogController extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(CatalogController.class);
    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
        if (productRepository == null) throw new ServletException("ProductRepository not created");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Pages.defaultPage(getServletContext(), req, resp, "Каталог", "content/catalog.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Pages.defaultPage(getServletContext(), req, resp, "Каталог", "content/catalog.jsp");
    }
}
