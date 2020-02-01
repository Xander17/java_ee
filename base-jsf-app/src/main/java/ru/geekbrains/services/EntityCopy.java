package ru.geekbrains.services;

import ru.geekbrains.repos.entities.Category;
import ru.geekbrains.repos.entities.Order;
import ru.geekbrains.repos.entities.OrderItem;
import ru.geekbrains.repos.entities.Product;
import ru.geekbrains.services.entities.CategoryDAO;
import ru.geekbrains.services.entities.OrderDAO;
import ru.geekbrains.services.entities.OrderItemDAO;
import ru.geekbrains.services.entities.ProductDAO;

import java.util.ArrayList;
import java.util.List;

class EntityCopy {
    static Product product(ProductDAO productDAO) {
        Product product = new Product();
        product.setId(productDAO.getId());
        product.setName(productDAO.getName());
        product.setDescription(productDAO.getDescription());
        product.setPrice(productDAO.getPrice());
        Category category = new Category();
        category.setId(productDAO.getCategory().getId());
        product.setCategory(category);
        return product;
    }

    static Order order(OrderDAO orderDAO) {
        Order order = new Order();
        order.setId(orderDAO.getId());
        List<OrderItem> list = new ArrayList<>();
        orderDAO.getList().forEach(item -> list.add(orderItem(item)));
        order.setList(list);
        return order;
    }

    static OrderItem orderItem(OrderItemDAO orderItemDAO) {
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

    static Category category(CategoryDAO categoryDAO) {
        Category category = new Category();
        category.setId(categoryDAO.getId());
        category.setName(categoryDAO.getName());
        return category;
    }
}
