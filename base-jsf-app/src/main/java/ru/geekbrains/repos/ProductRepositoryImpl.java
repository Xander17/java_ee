package ru.geekbrains.repos;

import ru.geekbrains.repos.entities.Product;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @TransactionAttribute
    @Override
    public void insert(Product product) {
        em.persist(product);
    }

    @TransactionAttribute
    @Override
    public void update(Product product) {
        em.merge(product);
    }

    @TransactionAttribute
    @Override
    public void delete(Product product) {
        product = findById(product.getId());
        em.remove(product);
    }

    @Override
    public Product findById(int id) {
        return em.find(Product.class, id);
    }

    @Override
    public List<Product> findAll() {
        return em.createQuery("select p from Product p", Product.class).getResultList();
    }
}
