package ru.geekbrains.services.entities;

import ru.geekbrains.repos.entities.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private Integer id;
    private List<OrderItemDAO> list;

    public OrderDAO(Order order) {
        if(order==null) return;
        this.id = order.getId();
        this.list=new ArrayList<>();
        order.getList().forEach(item -> this.list.add(new OrderItemDAO(item)));
    }

    public OrderDAO() {
    }

    public List<OrderItemDAO> getList() {
        return list;
    }

    public void setList(List<OrderItemDAO> list) {
        this.list = list;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormattedId() {
        return String.format("%08d", id);
    }
}
