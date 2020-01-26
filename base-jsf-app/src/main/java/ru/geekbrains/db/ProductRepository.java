package ru.geekbrains.db;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Named
@ApplicationScoped
public class ProductRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @Transactional
    public void insert(Product product) {
        em.persist(product);
    }

    @Transactional
    public void update(Product product) {
        em.merge(product);
    }

    @Transactional
    public void delete(Product product) {
        product = findById(product.getId());
        em.remove(product);
    }

    public Product findById(int id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAll() {
        return em.createQuery("select p from Product p", Product.class).getResultList();
    }
}
