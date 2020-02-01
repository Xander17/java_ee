package ru.geekbrains.services;

import ru.geekbrains.loggers.AppLogger;
import ru.geekbrains.repos.ProductRepository;
import ru.geekbrains.repos.entities.Product;
import ru.geekbrains.services.entities.ProductDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CatalogService {

    @Inject
    private ProductRepository productRepository;

    @Interceptors(AppLogger.class)
    public void insert(ProductDAO product) {
        productRepository.insert(EntityCopy.product(product));
    }

    @Interceptors(AppLogger.class)
    public void update(ProductDAO product) {
        productRepository.update(EntityCopy.product(product));
    }

    @Interceptors(AppLogger.class)
    public void delete(ProductDAO product) {
        productRepository.delete(EntityCopy.product(product));
    }

    public ProductDAO findById(int id) {
        return new ProductDAO(productRepository.findById(id));
    }

    public List<ProductDAO> findAll() {
        List<Product> productList = productRepository.findAll();
        List<ProductDAO> daoList = new ArrayList<>();
        productList.forEach(product -> daoList.add(new ProductDAO(product)));
        return daoList;
    }
}
