package ru.geekbrains.controllers;

import ru.geekbrains.services.RoleService;
import ru.geekbrains.services.entities.RoleDAO;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class RoleController implements Serializable {
    @EJB
    private RoleService roleService;

    private Integer roleEditId;
    private List<RoleDAO> roles;

    public void preloadRoles(ComponentSystemEvent componentSystemEvent) {
        this.roles = roleService.findAll();
    }

    public void deleteRole(RoleDAO role) {
        roleService.delete(role);
    }

    public void setEditStatus(RoleDAO role) {
        roleEditId = role.getId();
    }

    public void updateRole(RoleDAO role) {
        String newName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("category-name");
        if (newName != null && !newName.isEmpty()) {
            role.setName(newName);
            roleService.update(role);
        }
        roleEditId = null;
    }

    public void addRole() {
        String newName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("newcategory");
        if (newName != null && !newName.isEmpty()) {
            roleService.insert(new RoleDAO(newName));
        }
    }

    public Integer getRoleEditId() {
        return roleEditId;
    }

    public void setRoleEditId(Integer roleEditId) {
        this.roleEditId = roleEditId;
    }

    public List<RoleDAO> getRoles() {
        return roles;
    }
}