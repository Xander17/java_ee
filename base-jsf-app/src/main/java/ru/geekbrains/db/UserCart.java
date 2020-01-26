package ru.geekbrains.db;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class UserCart implements Serializable {
    @Inject
    private ProductRepository productRepository;
    @Inject
    private OrderRepository orderRepository;

    private HashMap<Integer, Integer> cart;

    @PostConstruct
    public void init() {
        cart = new HashMap<>();
    }

    public void add(int id, int quantity) {
        if (quantity <= 0) return;
        cart.put(id, cart.getOrDefault(id, 0) + quantity);
    }

    public void set(int id, Integer quantity) {
        if (quantity <= 0) remove(id);
        else cart.put(id, quantity);
    }

    public void remove(int id) {
        cart.remove(id);
    }

    public Map<Product, Integer> getCart() {
        Map<Product, Integer> productCart = new TreeMap<>();
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            Product product = productRepository.findById(entry.getKey());
            if (product.getId() >= 0) productCart.put(product, entry.getValue());
        }
        return productCart;
    }

    public int getCartSize() {
        return cart.size();
    }

    public void clear() {
        cart.clear();
    }

    public int order() {
        Order order = new Order();
        List<OrderItem> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            Product product = productRepository.findById(entry.getKey());
            if (product.getId() >= 0) list.add(new OrderItem(order, product, product.getPrice(), entry.getValue()));
        }
        order.setList(list);
        clear();
        return orderRepository.insert(order);
    }
}
