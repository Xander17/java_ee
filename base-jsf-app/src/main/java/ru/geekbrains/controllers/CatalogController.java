package ru.geekbrains.controllers;

import ru.geekbrains.db.Product;
import ru.geekbrains.db.ProductRepository;
import ru.geekbrains.db.UserCart;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class CatalogController implements Serializable {

    @Inject
    private ProductRepository productRepository;
    @Inject
    private UserCart userCart;

    private Product product;
    private List<Product> products;

    private int defaultValue;

    public void preloadProducts(ComponentSystemEvent componentSystemEvent) {
        this.products = productRepository.findAll();
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String showProduct(Product product) {
        this.product = product;
        return "product.xhtml?faces-redirect=true";
    }

    public void addToCart(Product product, String formId) {
        Integer quantity = getFormInt(formId);
        if (quantity == null || quantity <= 0) return;
        userCart.add(product.getId(), quantity);
    }

    public void addToCart(Product product) {
        addToCart(product, "quantity");
    }

    public int getDefaultValue() {
        return 1;
    }

    public void setDefaultValue(int defaultValue) {
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
