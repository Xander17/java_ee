package ru.geekbrains.services;

import ru.geekbrains.loggers.AppLogger;
import ru.geekbrains.repos.RoleRepository;
import ru.geekbrains.repos.entities.Role;
import ru.geekbrains.repos.entities.User;
import ru.geekbrains.services.entities.RoleDAO;
import ru.geekbrains.services.entities.UserDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class RoleServiceImpl implements RoleService {
    @Inject
    private RoleRepository roleRepository;

    @Interceptors(AppLogger.class)
    @Override
    public void insert(RoleDAO role) {
        roleRepository.insert(getRoleEntity(role));
    }

    @Interceptors(AppLogger.class)
    @Override
    public void update(RoleDAO role) {
        roleRepository.update(getRoleEntity(role));
    }

    @Interceptors(AppLogger.class)
    @Override
    public void delete(RoleDAO role) {
        roleRepository.delete(getRoleEntity(role));
    }

    @Override
    public RoleDAO findById(int id) {
        return new RoleDAO(roleRepository.findById(id));
    }

    @Override
    public List<RoleDAO> findAll() {
        List<Role> roleList = roleRepository.findAll();
        List<RoleDAO> daoList = new ArrayList<>();
        roleList.forEach(role -> daoList.add(new RoleDAO(role)));
        return daoList;
    }

    private Role getRoleEntity(RoleDAO roleDAO) {
        Role role = new Role();
        role.setId(roleDAO.getId());
        role.setName(roleDAO.getName());
        return role;
    }

    public List<UserDAO> getUsersByCategory(int id) {
        List<User> userList = roleRepository.getUsersByCategory(id);
        List<UserDAO> daoList = new ArrayList<>();
        userList.forEach(user -> daoList.add(new UserDAO(user)));
        return daoList;
    }
}
