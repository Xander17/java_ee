package ru.geekbrains.repos;

import ru.geekbrains.repos.entities.Role;
import ru.geekbrains.repos.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class RoleRepositoryImpl implements RoleRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @Override
    public void insert(Role role) {
        em.persist(role);
    }

    @Override
    public void update(Role role) {
        em.merge(role);
    }

    @Override
    public void delete(Role role) {
        role = findById(role.getId());
        List<User> list = getUsersByCategory(role.getId());
        for (User user : list) {
            user.setRole(null);
        }
        em.remove(role);
    }

    @Override
    public Role findById(int id) {
        return em.find(Role.class, id);
    }

    @Override
    public List<Role> findAll() {
        return em.createQuery("select r from Role r", Role.class).getResultList();
    }

    @Override
    public List<User> getUsersByCategory(int id) {
        TypedQuery<User> query = em.createQuery("select u from User u where u.role.id=?1", User.class);
        return query.setParameter(1, id).getResultList();
    }
}
