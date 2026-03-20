package org.acme.adapter.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.application.dto.Estimate;
import org.acme.application.service.ShipmentService;
import org.acme.domain.entity.Product;
import org.acme.domain.entity.RoleName;
import org.acme.port.AuthCheckerPort;
import org.acme.port.ProductServicePort;
import org.acme.port.ShipmentServicePort;

@Path("/shipment")
public class ShipmentResource{

    @Inject
    ShipmentServicePort service;

    @Inject
    AuthCheckerPort authChecker;

    @Inject
    ProductServicePort productService;

    @GET
    @Path("/cost")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEstimate(@QueryParam("token") String token,
                                @QueryParam("quantita") int qta,
                                @QueryParam("productId") Long productId,
                                @QueryParam("Zone") String zone) {

        Response error = authChecker.check(token, RoleName.CUSTOMER);
        if (error != null) return error;

        return Response.ok(service.getEstimate(productId, qta,zone)).build();
    }

    @GET
    @Path("/catalog")
    public Response allObjects(@QueryParam("token") String token) {

        Response error = authChecker.check(token, RoleName.CUSTOMER);
        if (error != null) return error;

        return Response.ok(productService.getAll()).build();
    }
}
