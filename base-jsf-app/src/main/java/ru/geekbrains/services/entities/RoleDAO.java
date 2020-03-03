package ru.geekbrains.services.entities;

import ru.geekbrains.repos.entities.Role;

public class RoleDAO {
    private Integer id;
    private String name;

    public RoleDAO(Role role) {
        if (role == null) return;
        this.id = role.getId();
        this.name = role.getName();
    }

    public RoleDAO() {
    }

    public RoleDAO(String name) {
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
