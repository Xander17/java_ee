package ru.geekbrains.services;

import ru.geekbrains.services.entities.ProductDAO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CatalogService {
    void insert(ProductDAO product);

    void update(ProductDAO product);

    void delete(ProductDAO product);

    ProductDAO findById(int id);

    List<ProductDAO> findAll();
}
