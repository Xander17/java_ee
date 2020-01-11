package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private Connection connection;

    public ProductRepository(Connection connection) throws SQLException {
        this.connection = connection;
        createTableIfNotExists(connection);
    }
    public void insert(Product product) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into products(`name`, `description`, `price`) values (?, ?, ?);")) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.execute();
        }
    }

    public void update(Product product) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "update products set `name` = ?, `description` = ?, `price` = ? where `id` = ?;")) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
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