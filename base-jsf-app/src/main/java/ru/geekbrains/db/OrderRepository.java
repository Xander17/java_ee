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
public class OrderRepository {
    private Connection connection;

    @Inject
    private ServletContext servletContext;
    @Inject
    private ProductRepository productRepository;

    @PostConstruct
    public void init() throws SQLException {
        this.connection = (Connection) servletContext.getAttribute("connection");
    }


    public void insert(Order order) throws SQLException {
        int id = 0;
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select max(id) from orders;");
            if (rs.next()) id = rs.getInt(1);
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "insert into orders values (?, ?, ?, ?);")) {
            List<OrderItem> list = order.getList();
            for (int i = 0; i < list.size(); i++) {
                OrderItem item = list.get(i);
                statement.setInt(1, id);
                statement.setInt(2, item.getProduct().getId());
                statement.setDouble(3, Math.round(item.getPrice()));
                statement.setInt(4, item.getQuantity());
                statement.execute();
            }
        }
    }

    public void update(OrderItem orderItem, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "update orders set `price` = ?, `quantity` = ? where `id` = ?;")) {
            statement.setDouble(1, orderItem.getPrice());
            statement.setInt(2, orderItem.getQuantity());
            statement.setInt(3, id);
            statement.execute();
        }
    }

    public void deleteProduct(int id, Product product) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "delete from orders where id = ? and product_id = ?;")) {
            statement.setInt(1, id);
            statement.setInt(1, product.getId());
            statement.execute();
        }
    }

    public void deleteOrder(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "delete from orders where id = ?;")) {
            statement.setInt(1, id);
            statement.execute();
        }
    }


    private List<OrderItem> getOrderList(int id) throws SQLException {
        List<OrderItem> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("product_id, price, quantity from orders where id=?;")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Product product = productRepository.findById(rs.getInt(1));
                if (product.getId() == -1) continue;
                list.add(new OrderItem(product, rs.getDouble(2), rs.getInt(3)));
            }
        }
        return list;
    }

    public List<Order> getOrders() throws SQLException {
        List<Order> list = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select distinct id from `orders`;");
            while (rs.next()) indexes.add(rs.getInt(1));
        }
        if (indexes.size() == 0) return null;
        for (Integer index : indexes) {
            list.add(new Order(getOrderList(index), index));
        }
        return list;
    }

}
