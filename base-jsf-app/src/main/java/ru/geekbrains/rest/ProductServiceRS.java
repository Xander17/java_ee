package ru.geekbrains.rest;

import ru.geekbrains.repos.entities.Product;

import javax.ejb.Local;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Local
@Path("/products")
public interface ProductServiceRS {
    @PUT
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    void insert(Product product);

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    void update(Product product);

    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    void delete(Product product);

    @GET
    @Path("/{id}/id")
    @Produces(MediaType.APPLICATION_JSON)
    Product findById(@PathParam("id") int id);

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    List<Product> findAll();
}
