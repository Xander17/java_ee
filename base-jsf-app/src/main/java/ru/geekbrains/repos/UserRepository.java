package ru.geekbrains.repos;

import ru.geekbrains.repos.entities.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserRepository {

    void insert(User user);

    void update(User user);

    void delete(User user);

    User findById(int id);

    List<User> findAll();
}
