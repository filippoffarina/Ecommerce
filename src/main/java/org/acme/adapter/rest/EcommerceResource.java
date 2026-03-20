package org.acme.adapter.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.domain.entity.Product;
import org.acme.domain.entity.RoleName;
import org.acme.port.AuthCheckerPort;

import java.util.List;

@Path("/ecommerce")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EcommerceResource {

    @Inject
    AuthCheckerPort authChecker;

    @POST
    @Transactional
    public Response addObjects(@QueryParam("token") String token,
                               Product p) {

        Response error = authChecker.check(token, RoleName.PRODUCER);
        if (error != null) return error;

        p.persist();
        return Response.ok(p).status(201).build();
    }

    @GET
    public Response allObjects(@QueryParam("token") String token) {

        Response error = authChecker.check(token, RoleName.PRODUCER);
        if (error != null) return error;

        return Response.ok(Product.listAll()).build();
    }


}
