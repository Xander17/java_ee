package ru.geekbrains.soap;

import ru.geekbrains.repos.entities.Category;

import javax.ejb.Local;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@Local
@WebService
public interface CategoryRepositoryWS {
    @WebMethod
    void insert(Category category);

    @WebMethod
    void update(Category category);

    @WebMethod
    void delete(Category category);

    @WebMethod
    Category findById(int id);

    @WebMethod
    List<Category> findAll();
}
