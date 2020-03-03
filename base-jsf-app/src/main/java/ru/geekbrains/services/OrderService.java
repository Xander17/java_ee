package ru.geekbrains.services;

import ru.geekbrains.services.entities.OrderDAO;
import ru.geekbrains.services.entities.OrderItemDAO;
import ru.geekbrains.services.entities.OrderLine;

import javax.ejb.Local;
import java.util.List;

@Local
public interface OrderService {
    int insert(OrderDAO order);

    void update(OrderItemDAO orderItem);

    void deleteProduct(OrderItemDAO orderItem);

    void deleteOrder(OrderDAO order);

    OrderItemDAO getOrderItem(int id);

    OrderDAO getOrder(int id);

    List<OrderLine> getOrders();
}
