package org.acme.adapter.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.application.dto.Estimate;
import org.acme.application.service.ShipmentService;
import org.acme.domain.entity.RoleName;
import org.acme.port.AuthCheckerPort;
import org.acme.port.ShipmentServicePort;

@Path("/shipment")
public class ShipmentResource{

    @Inject
    ShipmentServicePort service;

    @Inject
    AuthCheckerPort authChecker;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEstimate(@QueryParam("token") String token,
                                @QueryParam("quantita") int qta,
                                @QueryParam("productId") Long productId) {

        Response error = authChecker.check(token, RoleName.CUSTOMER);
        if (error != null) return error;

        return Response.ok(service.getEstimate(productId, qta)).build();
    }
}
