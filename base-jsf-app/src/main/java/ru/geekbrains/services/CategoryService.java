package ru.geekbrains.services;

import ru.geekbrains.services.entities.CategoryDAO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CategoryService {
    void insert(CategoryDAO category);

    void update(CategoryDAO category);

    void delete(CategoryDAO category);

    CategoryDAO findById(int id);

    List<CategoryDAO> findAll();
}
