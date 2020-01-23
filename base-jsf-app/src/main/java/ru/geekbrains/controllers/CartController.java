package ru.geekbrains.controllers;

import ru.geekbrains.db.Product;
import ru.geekbrains.db.UserCart;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
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

    public void updateQuantity(Product product, String qtyId) {
        String qty = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(qtyId);
        if (qty == null) return;
        try {
            int quantity = Integer.parseInt(qty);
            if (quantity < 0) return;
            cart.set(product, quantity);
        } catch (NumberFormatException ignored) {
        }
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