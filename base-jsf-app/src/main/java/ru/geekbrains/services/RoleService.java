package ru.geekbrains.services;

import ru.geekbrains.services.entities.RoleDAO;
import ru.geekbrains.services.entities.UserDAO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface RoleService {
    void insert(RoleDAO role);

    void update(RoleDAO role);

    void delete(RoleDAO role);

    RoleDAO findById(int id);

    List<RoleDAO> findAll();

    List<UserDAO> getUsersByCategory(int id);
}
