package controllers;

import db.Product;
import db.UserCart;

import javax.annotation.PostConstruct;
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

    public UserCart getCart() {
        return cart;
    }

    public int getCartSize() {
        return cart.getCart().size();
    }

    public void deleteProduct(Product product) {
        cart.remove(product);
    }

    public void addToCart(Product product,int quantity){
        cart.add(product,quantity);
    }

    public String updateQuantity(Product product) {
        int quantity = cart.getCart().get(product);
        if (quantity <= 0) cart.remove(product);
        else cart.set(product, quantity);
        return "index.xhtml?faces-redirect=true";
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
