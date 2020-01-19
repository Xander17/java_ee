package db;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

@Named
@SessionScoped
public class UserCart implements Serializable {
    private Map<Product, Integer> cart;

    @PostConstruct
    public void init(){
        cart = new TreeMap<>();
    }

    public void add(Product product, int quantity) {
        if (quantity <= 0) return;
        cart.put(product, cart.getOrDefault(product, 0) + quantity);
    }

    public void set(Product product, int quantity) {
        if (quantity < 0) return;
        if (quantity == 0) remove(product);
        else cart.put(product, quantity);
    }

    public void remove(Product product) {
        cart.remove(product);
    }

    public Map<Product, Integer> getCart() {
        return cart;
    }

    public void clear() {
        cart.clear();
    }
}
