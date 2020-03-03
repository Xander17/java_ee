package ru.geekbrains.controllers;

import ru.geekbrains.services.CatalogService;
import ru.geekbrains.services.CategoryService;
import ru.geekbrains.services.OrderService;
import ru.geekbrains.services.entities.*;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class AdminController implements Serializable {
    @EJB
    private CatalogService catalogService;
    @EJB
    private OrderService orderService;
    @EJB
    private CategoryService categoryService;

    private ProductDAO product;
    private Integer categoryEditId;
    private Integer orderEditId;

    private OrderDAO orderEdit;
    private List<ProductDAO> products;
    private List<CategoryDAO> categories;
    private List<OrderLine> orderLines;

    public void preloadProducts(ComponentSystemEvent componentSystemEvent) {
        this.products = catalogService.findAll();
    }

    public void preloadCategories(ComponentSystemEvent componentSystemEvent) {
        this.categories = categoryService.findAll();
    }

    public void preloadOrders(ComponentSystemEvent componentSystemEvent) {
        this.orderLines = orderService.getOrders();
    }

    public void preloadOrder(ComponentSystemEvent componentSystemEvent) {
        this.orderEdit = orderService.getOrder(orderEditId);
    }

    public List<ProductDAO> getAllProducts() {
        return products;
    }

    public String createProduct() {
        this.product = new ProductDAO();
        return "product_edit.xhtml?faces-redirect=true";
    }

    public String editProduct(ProductDAO product) {
        this.product = product;
        return "product_edit.xhtml?faces-redirect=true";
    }

    public void deleteProduct(ProductDAO product) {
        catalogService.delete(product);
    }

    public String saveProduct() {
        if (product.getId() == null) catalogService.insert(product);
        else catalogService.update(product);
        return "products.xhtml?faces-redirect=true";
    }

    public ProductDAO getProduct() {
        return product;
    }

    public void setProduct(ProductDAO product) {
        this.product = product;
    }

    public List<CategoryDAO> getCategories() {
        return categories;
    }

    public void deleteCategory(CategoryDAO category) {
        categoryService.delete(category);
    }

    public void setEditStatus(CategoryDAO category) {
        categoryEditId = category.getId();
    }

    public void updateCategory(CategoryDAO category) {
        String newName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("category-name");
        if (newName != null && !newName.isEmpty()) {
            category.setName(newName);
            categoryService.update(category);
        }
        categoryEditId = null;
    }

    public void addCategory() {
        String newName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("newcategory");
        if (newName != null && !newName.isEmpty()) {
            categoryService.insert(new CategoryDAO(newName));
        }
    }

    public Integer getCategoryEditId() {
        return categoryEditId;
    }

    public void setCategoryEditId(Integer categoryEditId) {
        this.categoryEditId = categoryEditId;
    }

    public List<OrderLine> getOrders() {
        return orderLines;
    }

    public void deleteOrder(OrderLine orderLine) {
        OrderDAO order = orderService.getOrder(orderLine.getId());
        orderService.deleteOrder(order);
    }

    public String deleteOrder() {
        orderService.deleteOrder(orderEdit);
        return "orders.xhtml?faces-redirect=true";
    }

    public String showOrder(OrderLine orderLine) {
        this.orderEditId = orderLine.getId();
        return "order_view.xhtml?faces-redirect=true";
    }

    public List<OrderItemDAO> getOrderList() {
        return orderEdit.getList();
    }

    public int getItemsCount() {
        return orderEdit.getList().size();
    }

    public int getOrdersCount() {
        return getOrders().size();
    }

    public Double getOrderSum() {
        double sum = 0;
        for (OrderItemDAO item : orderEdit.getList()) {
            sum += item.getPrice() * item.getQuantity();
        }
        return Math.round(sum * 100) / 100.;
    }

    public void deleteOrderProduct(OrderItemDAO orderItem) {
        orderService.deleteProduct(orderItem);
    }

    public void updateQuantity(OrderItemDAO orderItem, String formId) {
        Integer quantity = getFormInt(formId);
        if (quantity == null || quantity < 0) return;
        orderItem.setQuantity(quantity);
        orderService.update(orderItem);
    }

    public OrderDAO getOrderEdit() {
        return orderEdit;
    }

    public void setOrderEdit(OrderDAO orderEdit) {
        this.orderEdit = orderEdit;
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