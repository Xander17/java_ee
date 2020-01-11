package listeners;

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
        } catch (SQLException e) {
            logger.error("", e);
        }
    }
}
