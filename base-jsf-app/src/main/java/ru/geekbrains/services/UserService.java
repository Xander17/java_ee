package ru.geekbrains.services;

import ru.geekbrains.services.entities.UserDAO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserService {
    void insert(UserDAO product);

    void update(UserDAO product);

    void delete(UserDAO product);

    UserDAO findById(int id);

    List<UserDAO> findAll();
}
