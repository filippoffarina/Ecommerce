package org.acme.adapter.rest;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.domain.entity.Product;

import java.util.List;

@Path("/ecommerce")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EcommerceResource {

    @POST
    @Transactional
    public Response addObjects(Product p){
        p.persist();
        return Response.ok(p).status(201).build();
    }

    @GET
    public List<Product> allObjects() {

        return Product.listAll(); // Recupera tutto dal DB

    }


}
