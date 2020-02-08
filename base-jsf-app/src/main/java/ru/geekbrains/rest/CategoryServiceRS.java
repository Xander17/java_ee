package ru.geekbrains.rest;

import ru.geekbrains.repos.entities.Category;
import ru.geekbrains.repos.entities.Product;

import javax.ejb.Local;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Local
@Path("/categories")
public interface CategoryServiceRS {
    @PUT
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    void insert(Category category);

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    void update(Category category);

    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    void delete(Category category);

    @GET
    @Path("/{id}/id")
    @Produces(MediaType.APPLICATION_JSON)
    Category findById(@PathParam("id") int id);

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    List<Category> findAll();

    @GET
    @Path("/{id}/products")
    List<Product> getProductsByCategory(@PathParam("id") int id);
}
