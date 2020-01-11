package db;

import java.util.HashMap;
import java.util.Map;

public class UserCart {
    Map<Integer, Integer> cart;

    public UserCart() {
        cart = new HashMap<>();
    }

    public void add(int id, int quantity) {
        if (quantity <= 0) return;
        cart.put(id, cart.getOrDefault(id, 0) + quantity);
    }

    public void set(int id, int quantity) {
        if (quantity < 0) return;
        if (quantity == 0) remove(id);
        else cart.put(id, quantity);
    }

    public void remove(int id) {
        cart.remove(id);
    }

    public Map<Integer, Integer> getCart() {
        return cart;
    }
}
