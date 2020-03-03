package ru.geekbrains.services.entities;

import ru.geekbrains.repos.entities.User;

public class UserDAO {

    private Integer id;
    private String login;
    private String pass;
    private RoleDAO role;

    public UserDAO(User user) {
        if (user == null) return;
        this.id = user.getId();
        this.login = user.getLogin();
        this.pass = user.getPass();
        this.role = new RoleDAO(user.getRole());
    }

    public UserDAO() {
        this.role = new RoleDAO();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public RoleDAO getRole() {
        return role;
    }

    public void setRole(RoleDAO role) {
        this.role = role;
    }
}
