package ru.geekbrains.db;

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
    @Inject
    private CategoryRepository categoryRepository;

    @PostConstruct
    public void init() throws SQLException {
        this.connection = (Connection) servletContext.getAttribute("connection");
        DBTables.createTableIfNotExists(connection);
    }

    public void insert(Product product) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into products(`name`, `description`, `price`,`category`) values (?, ?, ?, ?);")) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, Math.round(product.getPrice() * 100) / 100.);
            statement.setInt(4, product.getCategory().getId());
            statement.execute();
        }
    }

    public void update(Product product) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "update products set `name` = ?, `description` = ?, `price` = ?, `category` = ? where `id` = ?;")) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, Math.round(product.getPrice() * 100) / 100.);
            statement.setInt(4, product.getCategory().getId());
            statement.setInt(5, product.getId());
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
                "select `id`, `name`, `description`, `price`, `category` from `products` where `id` = ?")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Product(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getDouble(4),
                        categoryRepository.findById(rs.getInt(5)));
            }
        }
        return new Product(-1, "", "", null, null);
    }

    public List<Product> findAll() throws SQLException {
        ArrayList<Product> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select `id`, `name`, `description`, `price`, `category` from `products`");
            while (rs.next()) {
                result.add(new Product(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getDouble(4),
                        categoryRepository.findById(rs.getInt(5))));
            }
        }
        return result;
    }
}
