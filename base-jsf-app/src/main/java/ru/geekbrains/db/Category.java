package ru.geekbrains.db;

public class Category {
    private Integer id;
    private String name;

    public Category() {
        this(null,null);
    }

    public Category(String name) {
        this(null,name);
    }

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
