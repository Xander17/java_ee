package ru.geekbrains.controllers;

import ru.geekbrains.db.Product;
import ru.geekbrains.db.UserCart;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;

@Named
@SessionScoped
public class CartController implements Serializable {

    @Inject
    private UserCart cart;

    public Map<Product, Integer> getCart() {
        return cart.getCart();
    }

    public int getCartSize() {
        return cart.getCart().size();
    }

    public void deleteProduct(Product product) {
        cart.remove(product);
    }

    public void updateQuantity(Map.Entry<Product, Integer> entry) {
        cart.set(entry.getKey(), entry.getValue());
    }

    public String order() {
        cart.clear();
        return "order.xhtml?faces-redirect=true";
    }

    public double getCartSum() {
        double sum = 0;
        for (Map.Entry<Product, Integer> entry : cart.getCart().entrySet()) {
            sum += entry.getKey().getPrice() * entry.getValue();
        }
        return Math.round(sum * 100) / 100.;
    }
}
