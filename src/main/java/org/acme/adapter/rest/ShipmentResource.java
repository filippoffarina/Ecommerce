package org.acme.adapter.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.acme.application.dto.Estimate;
import org.acme.application.service.ShipmentService;
import org.acme.port.ShipmentServicePort;

@Path("/shipment")
public class ShipmentResource{

    @Inject
    ShipmentServicePort service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Estimate getEstimate(@QueryParam("quantita") int qta,@QueryParam("productId") Long productId) {

        return service.getEstimate(productId,qta);
    }
}
