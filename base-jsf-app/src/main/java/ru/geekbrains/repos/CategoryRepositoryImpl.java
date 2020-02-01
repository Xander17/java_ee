package ru.geekbrains.repos;

import ru.geekbrains.repos.entities.Category;
import ru.geekbrains.repos.entities.Product;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CategoryRepositoryImpl implements CategoryRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @TransactionAttribute
    @Override
    public void insert(Category category) {
        em.persist(category);
    }

    @TransactionAttribute
    @Override
    public void update(Category category) {
        em.merge(category);
    }

    @TransactionAttribute
    @Override
    public void delete(Category category) {
        category = findById(category.getId());
        for (Product product : category.getProducts()) {
            product.setCategory(null);
        }
        em.remove(category);
    }

    @Override
    public Category findById(int id) {
        return em.find(Category.class, id);
    }

    @Override
    public List<Category> findAll() {
        return em.createQuery("select c from Category c", Category.class).getResultList();
    }
}
