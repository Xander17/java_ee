package ru.geekbrains.services;

import ru.geekbrains.loggers.AppLogger;
import ru.geekbrains.repos.CategoryRepository;
import ru.geekbrains.repos.entities.Category;
import ru.geekbrains.services.entities.CategoryDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CategoryService {

    @Inject
    private CategoryRepository categoryRepository;

    @Interceptors(AppLogger.class)
    public void insert(CategoryDAO category) {
        categoryRepository.insert(EntityCopy.category(category));
    }

    @Interceptors(AppLogger.class)
    public void update(CategoryDAO category) {
        categoryRepository.update(EntityCopy.category(category));
    }

    @Interceptors(AppLogger.class)
    public void delete(CategoryDAO category) {
        categoryRepository.delete(EntityCopy.category(category));
    }

    public CategoryDAO findById(int id) {
        return new CategoryDAO(categoryRepository.findById(id));
    }

    public List<CategoryDAO> findAll() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDAO> daoList = new ArrayList<>();
        categoryList.forEach(category -> daoList.add(new CategoryDAO(category)));
        return daoList;
    }
}
