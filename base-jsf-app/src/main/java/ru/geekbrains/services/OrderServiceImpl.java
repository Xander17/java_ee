package ru.geekbrains.services;

import ru.geekbrains.loggers.AppLogger;
import ru.geekbrains.repos.OrderRepository;
import ru.geekbrains.repos.entities.Order;
import ru.geekbrains.repos.entities.OrderItem;
import ru.geekbrains.repos.entities.Product;
import ru.geekbrains.services.entities.OrderDAO;
import ru.geekbrains.services.entities.OrderItemDAO;
import ru.geekbrains.services.entities.OrderLine;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class OrderServiceImpl implements OrderService {

    @Inject
    private OrderRepository orderRepository;

    @Interceptors(AppLogger.class)
    @Override
    public int insert(OrderDAO order) {
        return orderRepository.insert(getOrderEntity(order));
    }

    @Interceptors(AppLogger.class)
    @Override
    public void update(OrderItemDAO orderItem) {
        orderRepository.update(getOrderItemEntity(orderItem));
    }

    @Interceptors(AppLogger.class)
    @Override
    public void deleteProduct(OrderItemDAO orderItem) {
        orderRepository.deleteProduct(getOrderItemEntity(orderItem));
    }

    @Interceptors(AppLogger.class)
    @Override
    public void deleteOrder(OrderDAO order) {
        orderRepository.deleteOrder(getOrderEntity(order));
    }

    @Override
    public OrderItemDAO getOrderItem(int id) {
        return new OrderItemDAO(orderRepository.getOrderItem(id));
    }

    @Override
    public OrderDAO getOrder(int id) {
        return new OrderDAO(orderRepository.getOrder(id));
    }

    @Override
    public List<OrderLine> getOrders() {
        return orderRepository.getOrders();
    }

    private Order getOrderEntity(OrderDAO orderDAO) {
        Order order = new Order();
        order.setId(orderDAO.getId());
        List<OrderItem> list = new ArrayList<>();
        orderDAO.getList().forEach(item -> list.add(getOrderItemEntity(item)));
        order.setList(list);
        return order;
    }

    private OrderItem getOrderItemEntity(OrderItemDAO orderItemDAO) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDAO.getId());
        orderItem.setPrice(orderItemDAO.getPrice());
        Product product = new Product();
        product.setId(orderItemDAO.getProduct().getId());
        orderItem.setProduct(product);
        orderItem.setQuantity(orderItemDAO.getQuantity());
        Order order = new Order();
        order.setId(orderItemDAO.getOrder().getId());
        orderItem.setOrder(order);
        return orderItem;
    }
}
