package ru.geekbrains.repos;

import ru.geekbrains.repos.entities.Product;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ProductRepository {

    void insert(Product product);

    void update(Product product);

    void delete(Product product);

    Product findById(int id);

    List<Product> findAll();
}
