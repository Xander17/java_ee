package ru.geekbrains.controllers;

import ru.geekbrains.db.Product;
import ru.geekbrains.db.UserCart;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;

@Named
@SessionScoped
public class CartController implements Serializable {

    @Inject
    private UserCart cart;

    private Integer lastOrderId;

    public Map<Product, Integer> getCart() throws SQLException {
        return cart.getCart();
    }

    public int getCartSize() throws SQLException {
        return cart.getCart().size();
    }

    public void deleteProduct(Product product) {
        cart.remove(product.getId());
    }

    public void updateQuantity(Product product, String qtyId) {
        String qty = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(qtyId);
        if (qty == null) return;
        try {
            int quantity = Integer.parseInt(qty);
            if (quantity < 0) return;
            cart.set(product.getId(), quantity);
        } catch (NumberFormatException ignored) {
        }
    }

    public String order() throws SQLException {
        lastOrderId = cart.order();
        return "order.xhtml?faces-redirect=true";
    }

    public double getCartSum() throws SQLException {
        double sum = 0;
        for (Map.Entry<Product, Integer> entry : cart.getCart().entrySet()) {
            sum += entry.getKey().getPrice() * entry.getValue();
        }
        return Math.round(sum * 100) / 100.;
    }

    public String getLastOrderId() {
        return String.format("%08d", lastOrderId);
    }

    public void setLastOrderId(Integer lastOrderId) {
        this.lastOrderId = lastOrderId;
    }
}
