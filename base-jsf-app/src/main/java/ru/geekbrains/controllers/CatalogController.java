package ru.geekbrains.controllers;

import ru.geekbrains.services.CatalogService;
import ru.geekbrains.services.UserCartService;
import ru.geekbrains.services.entities.ProductDAO;

import javax.ejb.EJB;
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

    @EJB
    private CatalogService catalogService;
    @Inject
    private UserCartService userCart;

    private ProductDAO product;
    private List<ProductDAO> products;

    private int defaultValue;

    public void preloadProducts(ComponentSystemEvent componentSystemEvent) {
        this.products = catalogService.findAll();
    }

    public List<ProductDAO> getAllProducts() {
        return products;
    }

    public ProductDAO getProduct() {
        return product;
    }

    public void setProduct(ProductDAO product) {
        this.product = product;
    }

    public String showProduct(ProductDAO product) {
        this.product = product;
        return "product.xhtml?faces-redirect=true";
    }

    public void addToCart(ProductDAO product, String formId) {
        Integer quantity = getFormInt(formId);
        if (quantity == null || quantity <= 0) return;
        userCart.add(product.getId(), quantity);
    }

    public void addToCart(ProductDAO product) {
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
