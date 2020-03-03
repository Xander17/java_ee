package ru.geekbrains.controllers;

import ru.geekbrains.services.UserCartService;
import ru.geekbrains.services.entities.ProductDAO;

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
    private UserCartService cart;

    private Integer lastOrderId;
    private int cartSize;

    public Map<ProductDAO, Integer> getCart() {
        return cart.getCart();
    }

    public int getCartSize() {
        return cart.getCartSize();
    }

    public void deleteProduct(ProductDAO product) {
        cart.remove(product.getId());
    }

    public void updateQuantity(ProductDAO product, String formId) {
        Integer quantity = getFormInt(formId);
        if (quantity == null || quantity < 0) return;
        cart.set(product.getId(), quantity);
    }

    public String order() {
        lastOrderId = cart.order();
        return "order.xhtml?faces-redirect=true";
    }

    public double getCartSum() {
        double sum = 0;
        for (Map.Entry<ProductDAO, Integer> entry : cart.getCart().entrySet()) {
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

    private Integer getFormInt(String formId) {
        String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(formId);
        if (value == null) return null;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ignored) {
        }
        return null;
    }
}
