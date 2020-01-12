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
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Cart page", urlPatterns = "/cart/*")
public class CartController extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(CartController.class);
    private ProductRepository productRepository;
    private UserCart cart;
    private String fullPath;

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
        if (productRepository == null) throw new ServletException("ProductRepository not created");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        fullPath = req.getContextPath() + req.getServletPath();
        cart = UserCart.getSessionCart(req.getSession());
        try {
            String action = req.getPathInfo();
            if ("/order".equals(action)) {
                showOrderPage(req, resp);
            } else if ("/delete".equals(action)) {
                deleteProduct(req, resp);
            } else if (action != null) {
                resp.sendRedirect(fullPath);
            } else {
                loadCartInfo(req, resp);
            }
        } catch (SQLException e) {
            logger.error("", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NumberFormatException e) {
            logger.info("", e);
            resp.sendRedirect(fullPath);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        fullPath = req.getContextPath() + req.getServletPath();
        cart = UserCart.getSessionCart(req.getSession());
        try {
            String action = req.getPathInfo();
            if ("/update".equals(action)) {
                updateQuantity(req, resp);
            }
        } catch (NumberFormatException e) {
            logger.info("", e);
            resp.sendRedirect(fullPath);
        }
    }

    private void loadCartInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        Map<Product, Integer> productCart = null;
        Map<Integer, Integer> mapCart = cart.getCart();
        if (mapCart.size() > 0) {
            productCart = new HashMap<>();
            for (Map.Entry<Integer, Integer> entry : mapCart.entrySet()) {
                Product product = productRepository.findById(entry.getKey());
                if (product.getId() != -1) productCart.put(product, entry.getValue());
            }
        }
        req.setAttribute("products", productCart);
        Pages.defaultPageForward(getServletContext(), req, resp, "Корзина", "content/cart.jsp");
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        cart.remove(id);
        resp.sendRedirect(fullPath);
    }

    private void updateQuantity(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        int qty = Integer.parseInt(req.getParameter("quantity"));
        if (qty <= 0) cart.remove(id);
        else cart.set(id, qty);
        resp.sendRedirect(fullPath);
    }

    private void showOrderPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        cart.clear();
        Pages.defaultPageForward(getServletContext(), req, resp, "Заказ", "content/order.jsp");
    }
}
