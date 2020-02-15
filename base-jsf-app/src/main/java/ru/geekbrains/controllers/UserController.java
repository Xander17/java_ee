package ru.geekbrains.controllers;

import ru.geekbrains.services.UserService;
import ru.geekbrains.services.entities.UserDAO;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class UserController implements Serializable {
    @EJB
    private UserService userService;

    private UserDAO user;
    private UserDAO loggedUser;
    private List<UserDAO> users;

    public void preloadUsers(ComponentSystemEvent componentSystemEvent) {
        this.users = userService.findAll();
    }

    public List<UserDAO> getAllUsers() {
        return users;
    }

    public String createUser() {
        this.user = new UserDAO();
        return "user_edit.xhtml?faces-redirect=true";
    }

    public String editUser(UserDAO user) {
        this.user = user;
        return "user_edit.xhtml?faces-redirect=true";
    }

    public void deleteUser(UserDAO user) {
        userService.delete(user);
    }

    public String saveUser() {
        if (user.getId() == null) userService.insert(user);
        else userService.update(user);
        return "users.xhtml?faces-redirect=true";
    }

    public UserDAO getUser() {
        return user;
    }

    public void setUser(UserDAO user) {
        this.user = user;
    }

    public UserDAO getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(UserDAO loggedUser) {
        this.loggedUser = loggedUser;
    }
}
