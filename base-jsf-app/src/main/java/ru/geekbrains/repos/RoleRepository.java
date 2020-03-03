package ru.geekbrains.repos;

import ru.geekbrains.repos.entities.Role;
import ru.geekbrains.repos.entities.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface RoleRepository {
    void insert(Role role);

    void update(Role role);

    void delete(Role role);

    Role findById(int id);

    List<Role> findAll();

    List<User> getUsersByCategory(int id);
}
