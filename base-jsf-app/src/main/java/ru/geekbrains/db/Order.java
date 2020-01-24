package ru.geekbrains.db;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<OrderItem> list;
    private Integer id;

    public Order(List<OrderItem> list) {
        this(list,null);
    }

    public Order(List<OrderItem> list, Integer id) {
        this.list = list;
        this.id = id;
    }

    public List<OrderItem> getList() {
        return list;
    }

    public void setList(ArrayList<OrderItem> list) {
        this.list = list;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
