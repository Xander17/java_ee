package ru.geekbrains.repos;

import ru.geekbrains.repos.entities.Category;
import ru.geekbrains.repos.entities.Product;
import ru.geekbrains.rest.CategoryServiceRS;
import ru.geekbrains.soap.CategoryRepositoryWS;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@WebService(endpointInterface = "ru.geekbrains.soap.CategoryRepositoryWS", serviceName = "CategoryService")
public class CategoryRepositoryImpl implements CategoryRepository, CategoryRepositoryWS, CategoryServiceRS {

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
        List<Product> list = getProductsByCategory(category.getId());
        for (Product product : list) {
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

    @Override
    public List<Product> getProductsByCategory(int id) {
        TypedQuery<Product> query = em.createQuery("select p from Product p where p.category.id=?1", Product.class);
        return query.setParameter(1, id).getResultList();
    }
}
