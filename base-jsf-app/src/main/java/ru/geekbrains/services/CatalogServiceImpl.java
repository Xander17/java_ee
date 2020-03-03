package ru.geekbrains.services;

import ru.geekbrains.loggers.AppLogger;
import ru.geekbrains.repos.ProductRepository;
import ru.geekbrains.repos.entities.Category;
import ru.geekbrains.repos.entities.Product;
import ru.geekbrains.services.entities.ProductDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CatalogServiceImpl implements CatalogService {

    @Inject
    private ProductRepository productRepository;

    @Interceptors(AppLogger.class)
    @Override
    public void insert(ProductDAO product) {
        productRepository.insert(getProductEntity(product));
    }

    @Interceptors(AppLogger.class)
    @Override
    public void update(ProductDAO product) {
        productRepository.update(getProductEntity(product));
    }

    @Interceptors(AppLogger.class)
    @Override
    public void delete(ProductDAO product) {
        productRepository.delete(getProductEntity(product));
    }

    @Override
    public ProductDAO findById(int id) {
        return new ProductDAO(productRepository.findById(id));
    }

    @Override
    public List<ProductDAO> findAll() {
        List<Product> productList = productRepository.findAll();
        List<ProductDAO> daoList = new ArrayList<>();
        productList.forEach(product -> daoList.add(new ProductDAO(product)));
        return daoList;
    }

    private Product getProductEntity(ProductDAO productDAO) {
        Product product = new Product();
        product.setId(productDAO.getId());
        product.setName(productDAO.getName());
        product.setDescription(productDAO.getDescription());
        product.setPrice(productDAO.getPrice());
        Category category = new Category();
        category.setId(productDAO.getCategory().getId());
        product.setCategory(category);
        return product;
    }
}
