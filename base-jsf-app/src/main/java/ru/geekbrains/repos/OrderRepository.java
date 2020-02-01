package ru.geekbrains.repos;

import ru.geekbrains.repos.entities.Order;
import ru.geekbrains.repos.entities.OrderItem;
import ru.geekbrains.services.entities.OrderLine;

import javax.ejb.Local;
import java.util.List;

@Local
public interface OrderRepository {
    int insert(Order order);

    void update(OrderItem orderItem);

    void deleteProduct(OrderItem orderItem);

    void deleteOrder(Order order);

    OrderItem getOrderItem(int id);

    Order getOrder(int id);

    List<OrderLine> getOrders();
}
