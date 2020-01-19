package controllers;

import db.Product;
import db.ProductRepository;
import db.UserCart;

import javax.enterprise.context.SessionScoped;
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

    public String createProduct() {
        this.product = new Product();
        return "product_edit.xhtml?faces-redirect=true";
    }

    public List<Product> getAllProducts() throws SQLException {
        return productRepository.findAll();
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

    public void addToCart(Product product) {
        userCart.add(product, 1);
    }
}
