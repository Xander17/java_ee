package listeners;

import db.Product;
import db.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class AppListener implements ServletContextListener {
    private Logger logger = LoggerFactory.getLogger(AppListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String connectString = servletContext.getInitParameter("dbConnectString");
        String username = servletContext.getInitParameter("dbUsername");
        String password = servletContext.getInitParameter("dbPassword");

        try {
            Connection connection = DriverManager.getConnection(connectString, username, password);
            ProductRepository productRepository = new ProductRepository(connection);
            servletContext.setAttribute("connection", connection);
            servletContext.setAttribute("productRepository", productRepository);

            if (productRepository.findAll().size() == 0) {
                productRepository.insert(new Product(-1, "Product1", "Desc1", 10.66));
                productRepository.insert(new Product(-1, "Product2", "Desc2", 102.));
                productRepository.insert(new Product(-1, "Product3", "Desc3", 1030.));
                productRepository.insert(new Product(-1, "Product4", "Desc4", 199.99));
                productRepository.insert(new Product(-1, "Product5", "Desc5", 55.50));
                productRepository.insert(new Product(-1, "Product6", "Desc6", 0.4));
                productRepository.insert(new Product(-1, "Product7", "Desc7", 1800.));
                productRepository.insert(new Product(-1, "Product8", "Desc8", 99.));
                productRepository.insert(new Product(-1, "Product9", "Desc9", 15.));
            }
        } catch (SQLException e) {
            logger.error("", e);
        }
    }
}
