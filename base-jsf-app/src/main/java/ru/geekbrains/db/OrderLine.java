package ru.geekbrains.db;

public class OrderLine {
    private Integer id;
    private Integer count;
    private Double sum;

    public OrderLine(Integer id, Integer count, Double sum) {
        this.id = id;
        this.count = count;
        this.sum=sum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}