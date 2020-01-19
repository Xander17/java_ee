package db;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class ProductRepository {
    private Connection connection;

    @Inject
    private ServletContext servletContext;

    @PostConstruct
    public void init() throws SQLException {
        this.connection = (Connection) servletContext.getAttribute("connection");
        createTableIfNotExists(connection);
        if (findAll().size() == 0) {
            insert(new Product(-1, "Product1", "Desc1", 10.66));
            insert(new Product(-1, "Product2", "Desc2", 102.));
            insert(new Product(-1, "Product3", "Desc3", 1030.));
            insert(new Product(-1, "Product4", "Desc4", 199.99));
            insert(new Product(-1, "Product5", "Desc5", 55.50));
            insert(new Product(-1, "Product6", "Desc6", 0.4));
            insert(new Product(-1, "Product7", "Desc7", 1800.));
            insert(new Product(-1, "Product8", "Desc8", 99.));
            insert(new Product(-1, "Product9", "Desc9", 15.));
        }
    }

    public void insert(Product product) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into products(`name`, `description`, `price`) values (?, ?, ?);")) {
            String name = product.getName();
            String desc = product.getDescription();
            Double price = product.getPrice();
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, Math.round(product.getPrice() * 100) / 100.);
            statement.execute();
        }
    }

    public void update(Product product) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "update products set `name` = ?, `description` = ?, `price` = ? where `id` = ?;")) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, Math.round(product.getPrice() * 100) / 100.);
            statement.setInt(4, product.getId());
            statement.execute();
        }
    }

    public void delete(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "delete from products where id = ?;")) {
            statement.setInt(1, id);
            statement.execute();
        }
    }

    public Product findById(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "select `id`, `name`, `description`, `price` from `products` where `id` = ?")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4));
            }
        }
        return new Product(-1, "", "", null);
    }

    public List<Product> findAll() throws SQLException {
        ArrayList<Product> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select `id`, `name`, `description`, `price` from `products`");
            while (rs.next()) {
                result.add(new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4)));
            }
        }
        return result;
    }

    private void createTableIfNotExists(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute("create table if not exists `products` (\n" +
                    "`id` int auto_increment primary key,\n" +
                    "`name` varchar(25),\n" +
                    "`description` varchar(25),\n" +
                    "`price` decimal(19, 2) \n" +
                    ");");
        }
    }
}
