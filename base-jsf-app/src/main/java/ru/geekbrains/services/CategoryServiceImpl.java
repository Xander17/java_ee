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
public class CategoryServiceImpl implements CategoryService {

    @Inject
    private CategoryRepository categoryRepository;

    @Interceptors(AppLogger.class)
    @Override
    public void insert(CategoryDAO category) {
        categoryRepository.insert(getCategoryEntity(category));
    }

    @Interceptors(AppLogger.class)
    @Override
    public void update(CategoryDAO category) {
        categoryRepository.update(getCategoryEntity(category));
    }

    @Interceptors(AppLogger.class)
    @Override
    public void delete(CategoryDAO category) {
        categoryRepository.delete(getCategoryEntity(category));
    }

    @Override
    public CategoryDAO findById(int id) {
        return new CategoryDAO(categoryRepository.findById(id));
    }

    @Override
    public List<CategoryDAO> findAll() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDAO> daoList = new ArrayList<>();
        categoryList.forEach(category -> daoList.add(new CategoryDAO(category)));
        return daoList;
    }

    private Category getCategoryEntity(CategoryDAO categoryDAO) {
        Category category = new Category();
        category.setId(categoryDAO.getId());
        category.setName(categoryDAO.getName());
        return category;
    }
}
