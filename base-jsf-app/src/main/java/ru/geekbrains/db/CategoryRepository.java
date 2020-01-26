package ru.geekbrains.db;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Named
@ApplicationScoped
public class CategoryRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @Transactional
    public void insert(Category category) {
        em.persist(category);
    }

    @Transactional
    public void update(Category category) {
        em.merge(category);
    }

    @Transactional
    public void delete(Category category) {
        category=findById(category.getId());
            for (Product product : category.getProducts()) {
                product.setCategory(null);
            }
        em.remove(category);
    }

    public Category findById(int id) {
        return em.find(Category.class, id);
    }

    public List<Category> findAll() {
        return em.createQuery("select c from Category c", Category.class).getResultList();
    }
}
