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
public class CategoryRepository {
    private Connection connection;

    @Inject
    private ServletContext servletContext;

    @PostConstruct
    public void init() throws SQLException {
        this.connection = (Connection) servletContext.getAttribute("connection");
        DBTables.createTableIfNotExists(connection);
    }

    public void insert(Category category) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into categories(`name`) values (?);")) {
            statement.setString(1, category.getName());
            statement.execute();
        }
    }

    public void update(Category category) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "update categories set `name` = ? where `id` = ?;")) {
            statement.setString(1, category.getName());
            statement.setInt(2, category.getId());
            statement.execute();
        }
    }

    public void delete(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "delete from categories where id = ?;")) {
            statement.setInt(1, id);
            statement.execute();
        }
    }

    public Category findById(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "select `name` from `categories` where `id` = ?")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new Category(id, rs.getString(1));
            }
        }
        return new Category(-1, null);
    }

    public List<Category> findAll() throws SQLException {
        ArrayList<Category> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select `id`, `name` from `categories`");
            while (rs.next()) {
                result.add(new Category(rs.getInt(1), rs.getString(2)));
            }
        }
        return result;
    }
}
