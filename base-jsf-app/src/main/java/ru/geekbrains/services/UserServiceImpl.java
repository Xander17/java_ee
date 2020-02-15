package ru.geekbrains.services;

import ru.geekbrains.loggers.AppLogger;
import ru.geekbrains.repos.UserRepository;
import ru.geekbrains.repos.entities.Role;
import ru.geekbrains.repos.entities.User;
import ru.geekbrains.services.entities.UserDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;

    @Interceptors(AppLogger.class)
    @Override
    public void insert(UserDAO user) {
        userRepository.insert(getUserEntity(user));
    }

    @Interceptors(AppLogger.class)
    @Override
    public void update(UserDAO user) {
        userRepository.update(getUserEntity(user));
    }

    @Interceptors(AppLogger.class)
    @Override
    public void delete(UserDAO user) {
        userRepository.delete(getUserEntity(user));
    }

    @Override
    public UserDAO findById(int id) {
        return new UserDAO(userRepository.findById(id));
    }

    @Override
    public List<UserDAO> findAll() {
        List<User> productList = userRepository.findAll();
        List<UserDAO> daoList = new ArrayList<>();
        productList.forEach(product -> daoList.add(new UserDAO(product)));
        return daoList;
    }

    private User getUserEntity(UserDAO userDAO) {
        User user = new User();
        user.setId(userDAO.getId());
        user.setLogin(userDAO.getLogin());
        user.setPass(userDAO.getPass());
        Role role = new Role();
        role.setId(userDAO.getRole().getId());
        user.setRole(role);
        return user;
    }
}
