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


    public int insert(Order order) throws SQLException {
        int id = 0;
        try (Statement statement = connection.createStatement()) {
            statement.execute("insert into orders values()");
            ResultSet rs = statement.executeQuery("select max(id) from orders;");
            if (rs.next()) id = rs.getInt(1);
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "insert into order_items values (?, ?, ?, ?);")) {
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
        return id;
    }

    public void update(OrderItem orderItem, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "update order_items set `price` = ?, `quantity` = ? where `id` = ? and `product_id`=?;")) {
            statement.setDouble(1, orderItem.getPrice());
            statement.setInt(2, orderItem.getQuantity());
            statement.setInt(3, id);
            statement.setInt(4, orderItem.getProduct().getId());
            statement.execute();
        }
    }

    public void deleteProduct(int id, Product product) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "delete from order_items where id = ? and product_id = ?;")) {
            statement.setInt(1, id);
            statement.setInt(2, product.getId());
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


    public Order getOrder(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("select id from orders where id=?;")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) return new Order(new ArrayList<>(), -1);
        }

        List<OrderItem> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select product_id, price, quantity from order_items where id=?;")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Product product = productRepository.findById(rs.getInt(1));
                if (product.getId() == -1) continue;
                list.add(new OrderItem(product, rs.getDouble(2), rs.getInt(3)));
            }
        }
        return new Order(list, id);
    }

    public List<OrderLine> getOrders() throws SQLException {
        List<OrderLine> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select a.id, count(b.id), sum(b.price*b.quantity) from orders as a join order_items as b on a.id=b.id group by a.id;");
            while (rs.next()) {
                int count = rs.getInt(2);
                if (count > 0) list.add(new OrderLine(rs.getInt(1), count, rs.getDouble(3)));
            }
        }
        return list;
    }
}