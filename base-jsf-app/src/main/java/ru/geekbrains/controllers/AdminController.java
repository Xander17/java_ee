package ru.geekbrains.controllers;

import ru.geekbrains.db.*;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
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
    private Integer orderEditId;
    private Order orderEdit;
    private Integer editId;

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
        return "products.xhtml?faces-redirect=true";
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Category> getCategories() throws SQLException {
        return categoryRepository.findAll();
    }

    public void deleteCategory(Category category) throws SQLException {
        categoryRepository.delete(category.getId());
    }

    public void setEditStatus(Category category) {
        editId = category.getId();
    }

    public void updateCategory(Category category) throws SQLException {
        String newName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("category-name");
        if (newName != null && !newName.isEmpty()) {
            category.setName(newName);
            categoryRepository.update(category);
        }
        editId = null;
    }

    public void addCategory() throws SQLException {
        String newName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("newcategory");
        if (newName != null && !newName.isEmpty()) {
            categoryRepository.insert(new Category(newName));
        }
    }

    public Integer getEditId() {
        return editId;
    }

    public void setEditId(Integer editId) {
        this.editId = editId;
    }

    public List<OrderLine> getOrders() throws SQLException {
        return orderRepository.getOrders();
    }

    public void deleteOrder(OrderLine orderLine) throws SQLException {
        orderRepository.deleteOrder(orderLine.getId());
    }

    public String deleteOrder() throws SQLException {
        orderRepository.deleteOrder(orderEditId);
        return "orders.xhtml?faces-redirect=true";
    }

    public String showOrder(OrderLine orderLine) throws SQLException {
        this.orderEditId = orderLine.getId();
        return "order_view.xhtml?faces-redirect=true";
    }

    public List<OrderItem> getOrderList() throws SQLException {
        orderEdit = orderRepository.getOrder(orderEditId);
        return orderEdit.getList();
    }

    public int getOrdersCount() throws SQLException {
        return getOrders().size();
    }

    public Double getOrderSum() {
        double sum = 0;
        for (OrderItem item : orderEdit.getList()) {
            sum += item.getPrice() * item.getQuantity();
        }
        return Math.round(sum * 100) / 100.;
    }

    public void deleteOrderProduct(OrderItem orderItem) throws SQLException {
        orderRepository.deleteProduct(orderEditId, orderItem.getProduct());
    }

    public void updateQuantity(OrderItem orderItem, String formId) {
        String qty = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(formId);
        if (qty == null) return;
        try {
            int quantity = Integer.parseInt(qty);
            if (quantity < 0) return;
            orderItem.setQuantity(quantity);
            orderRepository.update(orderItem, orderEditId);
        } catch (NumberFormatException | SQLException ignored) {
        }
    }

    public Order getOrderEdit() {
        return orderEdit;
    }

    public void setOrderEdit(Order orderEdit) {
        this.orderEdit = orderEdit;
    }
}
