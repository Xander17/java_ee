package ru.geekbrains.services.entities;

import ru.geekbrains.repos.entities.Category;

public class CategoryDAO {
    private Integer id;
    private String name;

    public CategoryDAO(Category category) {
        if(category==null) return;
        this.id = category.getId();
        this.name = category.getName();
    }

    public CategoryDAO() {
    }

    public CategoryDAO(String name) {
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
