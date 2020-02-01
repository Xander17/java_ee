package ru.geekbrains.services;

import ru.geekbrains.loggers.AppLogger;
import ru.geekbrains.repos.OrderRepository;
import ru.geekbrains.services.entities.OrderDAO;
import ru.geekbrains.services.entities.OrderItemDAO;
import ru.geekbrains.services.entities.OrderLine;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

@Stateless
public class OrderService {

    @Inject
    private OrderRepository orderRepository;

    @Interceptors(AppLogger.class)
    public int insert(OrderDAO order) {
        return orderRepository.insert(EntityCopy.order(order));
    }

    @Interceptors(AppLogger.class)
    public void update(OrderItemDAO orderItem) {
        orderRepository.update(EntityCopy.orderItem(orderItem));
    }

    @Interceptors(AppLogger.class)
    public void deleteProduct(OrderItemDAO orderItem) {
        orderRepository.deleteProduct(EntityCopy.orderItem(orderItem));
    }

    @Interceptors(AppLogger.class)
    public void deleteOrder(OrderDAO order) {
        orderRepository.deleteOrder(EntityCopy.order(order));
    }

    public OrderItemDAO getOrderItem(int id) {
        return new OrderItemDAO(orderRepository.getOrderItem(id));
    }

    public OrderDAO getOrder(int id) {
        return new OrderDAO(orderRepository.getOrder(id));
    }

    public List<OrderLine> getOrders() {
        return orderRepository.getOrders();
    }
}
