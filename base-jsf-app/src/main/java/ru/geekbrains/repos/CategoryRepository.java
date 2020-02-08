package ru.geekbrains.repos;

import ru.geekbrains.repos.entities.Category;
import ru.geekbrains.repos.entities.Product;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CategoryRepository {
    void insert(Category category);

    void update(Category category);

    void delete(Category category);

    Category findById(int id);

    List<Category> findAll();

    List<Product> getProductsByCategory(int id);
}
