package org.acme.adapter.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.domain.entity.Product;
import org.acme.domain.entity.RoleName;
import org.acme.port.AuthCheckerPort;
import org.acme.port.ProductServicePort;

import java.util.List;
import java.util.Optional;

@Path("/ecommerce")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EcommerceResource {

    @Inject
    AuthCheckerPort authChecker;

    @Inject
    ProductServicePort productService;

    @POST
    @Path("/addProduct")
    public Response addObjects(@QueryParam("token") String token,
                               Product p) {

        Response error = authChecker.check(token, RoleName.PRODUCER);
        if (error != null) return error;

        Product product = productService.addProduct(p);
        return Response.ok(product).status(201).build();
    }

    @DELETE
    @Path("/deleteProduct")
    public Response delete(@QueryParam("token") @DefaultValue("") String token,
                           @QueryParam("Id") Long id) {

        Response error = authChecker.check(token, RoleName.PRODUCER);
        if (error != null) return error;

        Boolean deleted = productService.deleteProduct(id);
        return deleted
                ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/updateProduct")
    public Response updateProduct(@QueryParam("token") @DefaultValue("") String token,
                            @QueryParam("Id") Long id,
                            @QueryParam("name") String name,
                            @QueryParam("sector") String sector,
                            @QueryParam("price") int price
                            ) {

        Response error = authChecker.check(token, RoleName.PRODUCER);
        if (error != null) return error;

        Optional<Product> err = productService.updateProduct(id,name,sector,price);
        return err.isPresent()
                ? Response.status(Response.Status.BAD_REQUEST).entity(err.get()).build()
                : Response.ok("Ruolo aggiunto con successo").build();
    }

    @GET
    @Path("/seeProduct")
    public Response allObjects(@QueryParam("token") String token) {

        Response error = authChecker.check(token, RoleName.PRODUCER);
        if (error != null) return error;

        return Response.ok(productService.getAll()).build();
    }

    @GET
    @Path("/search/Id")
    public Response findById(@QueryParam("token") @DefaultValue("") String token,
                             @QueryParam("Id") Long id) {

        Response error = authChecker.check(token, RoleName.PRODUCER);
        if (error != null) return error;

        return productService.findProductById(id)
                .map(u -> Response.ok(u).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/search/name")
    public Response findByName(@QueryParam("token") @DefaultValue("") String token,
                             @QueryParam("name") String name) {

        Response error = authChecker.check(token, RoleName.PRODUCER);
        if (error != null) return error;

        return productService.findProductByName(name)
                .map(u -> Response.ok(u).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/search/sector")
    public Response findBySector(@QueryParam("token") @DefaultValue("") String token,
                               @QueryParam("sector") String sector) {

        Response error = authChecker.check(token, RoleName.PRODUCER);
        if (error != null) return error;

        return Response.ok(productService.findProductBySector(sector)).build();
    }




}
