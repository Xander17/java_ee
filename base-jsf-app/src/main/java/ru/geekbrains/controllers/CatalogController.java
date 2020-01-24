package ru.geekbrains.controllers;

import ru.geekbrains.db.Product;
import ru.geekbrains.db.ProductRepository;
import ru.geekbrains.db.UserCart;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@Named
@SessionScoped
public class CatalogController implements Serializable {

    @Inject
    private ProductRepository productRepository;
    @Inject
    private UserCart userCart;

    private Product product;
    private int defaultValue;

    public List<Product> getAllProducts() throws SQLException {
        return productRepository.findAll();
    }

    public String createProduct() {
        this.product = new Product();
        return "product_edit.xhtml?faces-redirect=true";
    }

    public String editProduct(Product product) {
        this.product = product;
        return "product_edit.xhtml?faces-redirect=true";
    }

    public void deleteProduct(Product product) throws SQLException {
        productRepository.delete(product.getId());
    }

    public String saveProduct() throws SQLException {
        if (product.getId() == null) productRepository.insert(product);
        else productRepository.update(product);
        return "index.xhtml?faces-redirect=true";
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

    public void addToCart(Product product, String qtyId) {
        String qty = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(qtyId);
        if (qty == null) return;
        try {
            int quantity = Integer.parseInt(qty);
            if (quantity <= 0) return;
            userCart.add(product.getId(), quantity);
        } catch (NumberFormatException ignored) {
        }
    }

    public void addToCart(Product product) {
        addToCart(product, "quantity");
    }

    public int getDefaultValue() {
        return 1;
    }

    public void setDefaultValue(int defaultValue) {
    }
}
