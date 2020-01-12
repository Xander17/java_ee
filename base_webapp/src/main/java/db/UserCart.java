package db;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class UserCart {
    private Map<Integer, Integer> cart;
    private HttpSession session;

    public static UserCart getSessionCart(HttpSession session) {
        UserCart cart = (UserCart) session.getAttribute("cart");
        if (cart == null) {
            cart = new UserCart();
            cart.session = session;
            cart.updateSession();
        }
        return cart;
    }

    private UserCart() {
        cart = new TreeMap<>();
    }

    public void add(int id, int quantity) {
        if (quantity <= 0) return;
        cart.put(id, cart.getOrDefault(id, 0) + quantity);
        updateSession();
    }

    public void set(int id, int quantity) {
        if (quantity < 0) return;
        if (quantity == 0) remove(id);
        else cart.put(id, quantity);
        updateSession();
    }

    public void remove(int id) {
        cart.remove(id);
        updateSession();
    }

    public Map<Integer, Integer> getCart() {
        return cart;
    }

    public void clear(){
        cart.clear();
        updateSession();
    }

    private void updateSession() {
        session.setAttribute("cart", this);
        session.setAttribute("cartSize", cart.size());
    }
}
