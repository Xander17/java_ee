package ru.geekbrains.controllers;

import ru.geekbrains.db.*;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class AdminController implements Serializable {
    @Inject
    private ProductRepository productRepository;
    @Inject
    private CategoryRepository categoryRepository;
    @Inject
    private OrderRepository orderRepository;

    private Product product;
    private Integer categoryEditId;
    private Integer orderEditId;

    private Order orderEdit;
    private List<Product> products;
    private List<Category> categories;
    private List<OrderLine> orderLines;

    public void preloadProducts(ComponentSystemEvent componentSystemEvent) {
        this.products = productRepository.findAll();
    }

    public void preloadCategories(ComponentSystemEvent componentSystemEvent) {
        this.categories = categoryRepository.findAll();
    }

    public void preloadOrders(ComponentSystemEvent componentSystemEvent) {
        this.orderLines = orderRepository.getOrders();
    }

    public void preloadOrder(ComponentSystemEvent componentSystemEvent) {
        this.orderEdit = orderRepository.getOrder(orderEditId);
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public String createProduct() {
        this.product = new Product();
        return "product_edit.xhtml?faces-redirect=true";
    }

    public String editProduct(Product product) {
        this.product = product;
        return "product_edit.xhtml?faces-redirect=true";
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public String saveProduct() {
        if (product.getId() == null) productRepository.insert(product);
        else productRepository.update(product);
        return "products.xhtml?faces-redirect=true";
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

    public void setEditStatus(Category category) {
        categoryEditId = category.getId();
    }

    public void updateCategory(Category category) {
        String newName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("category-name");
        if (newName != null && !newName.isEmpty()) {
            category.setName(newName);
            categoryRepository.update(category);
        }
        categoryEditId = null;
    }

    public void addCategory() {
        String newName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("newcategory");
        if (newName != null && !newName.isEmpty()) {
            categoryRepository.insert(new Category(newName));
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
        Order order = orderRepository.getOrder(orderLine.getId());
        orderRepository.deleteOrder(order);
    }

    public String deleteOrder() {
        orderRepository.deleteOrder(orderEdit);
        return "orders.xhtml?faces-redirect=true";
    }

    public String showOrder(OrderLine orderLine) {
        this.orderEditId = orderLine.getId();
        return "order_view.xhtml?faces-redirect=true";
    }

    public List<OrderItem> getOrderList() {
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
        for (OrderItem item : orderEdit.getList()) {
            sum += item.getPrice() * item.getQuantity();
        }
        return Math.round(sum * 100) / 100.;
    }

    public void deleteOrderProduct(OrderItem orderItem) {
        orderRepository.deleteProduct(orderItem);
    }

    public void updateQuantity(OrderItem orderItem, String formId) {
        Integer quantity = getFormInt(formId);
        if (quantity == null || quantity < 0) return;
        orderItem.setQuantity(quantity);
        orderRepository.update(orderItem);
    }

    public Order getOrderEdit() {
        return orderEdit;
    }

    public void setOrderEdit(Order orderEdit) {
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
