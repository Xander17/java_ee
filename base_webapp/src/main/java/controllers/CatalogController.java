package controllers;

import db.Product;
import db.ProductRepository;
import db.UserCart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@WebServlet(name = "PageController", urlPatterns = "/catalog/*")
public class CatalogController extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(CatalogController.class);
    private ProductRepository productRepository;
    private String fullPath;

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
        if (productRepository == null) throw new ServletException("ProductRepository not created");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        fullPath = req.getContextPath() + req.getServletPath();
        try {
            String action = req.getPathInfo();
            if ("/create".equals(action)) {
                showCreatePage(req, resp);
            } else if ("/edit".equals(action)) {
                showEdit(req, resp);
            } else if ("/delete".equals(action)) {
                deleteProduct(req, resp);
            } else if ("/product".equals(action)) {
                showProduct(req, resp);
            } else if (action != null) {
                resp.sendRedirect(fullPath);
            } else {
                req.setAttribute("products", productRepository.findAll());
                Pages.defaultPageForward(getServletContext(), req, resp, "Каталог", "content/catalog.jsp");
            }
        } catch (SQLException e) {
            logger.error("", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NumberFormatException | NoSuchElementException e) {
            logger.info("", e);
            resp.sendRedirect(fullPath);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        fullPath = req.getContextPath() + req.getServletPath();
        try {
            String action = req.getPathInfo();
            if ("/create".equals(action)) {
                createProduct(req, resp);
            } else if ("/edit".equals(action)) {
                editProduct(req, resp);
            } else if ("/cart".equals(action)) {
                addToCart(req, resp);
            }
        } catch (SQLException e) {
            logger.error("", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NumberFormatException e) {
            logger.info("", e);
            resp.sendRedirect(fullPath);
        }
    }

    private void showProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = productRepository.findById(id);
        if (product.getId() == -1) throw new NoSuchElementException("Product ID " + id + " doesn't exist");
        req.setAttribute("product", product);
        Pages.defaultPageForward(getServletContext(), req, resp, product.getName(), "content/product.jsp");
    }

    private void addToCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        int qty = Integer.parseInt(req.getParameter("quantity"));
        UserCart.getSessionCart(req.getSession()).add(id, qty);
        resp.sendRedirect(fullPath);
    }

    private void editProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        Product product = new Product(
                Integer.parseInt(req.getParameter("id")),
                req.getParameter("name"),
                req.getParameter("description"),
                Double.parseDouble(req.getParameter("price")));
        productRepository.update(product);
        resp.sendRedirect(fullPath);
    }

    private void createProduct(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        Product product = new Product(
                -1,
                req.getParameter("name"),
                req.getParameter("description"),
                Double.parseDouble(req.getParameter("price")));
        productRepository.insert(product);
        resp.sendRedirect(fullPath);
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(req.getParameter("id"));
        productRepository.delete(id);
        resp.sendRedirect(fullPath);
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = productRepository.findById(id);
        if (product.getId() == -1) throw new NoSuchElementException("Product ID " + id + " doesn't exist");
        req.setAttribute("product", product);
        req.setAttribute("action", "edit");
        Pages.defaultPageForward(getServletContext(), req, resp, "Редактирование товара", "content/product_edit.jsp");
    }

    private void showCreatePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("product", new Product());
        req.setAttribute("action", "create");
        Pages.defaultPageForward(getServletContext(), req, resp, "Добавление товара", "content/product_edit.jsp");
    }
}
