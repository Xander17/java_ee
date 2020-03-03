package ru.geekbrains.soap;

import ru.geekbrains.repos.entities.Product;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface ProductRepositoryWS {
    @WebMethod
    void insert(Product product);

    @WebMethod
    void update(Product product);

    @WebMethod
    void delete(Product product);

    @WebMethod
    Product findById(int id);

    @WebMethod
    List<Product> findAll();
}
