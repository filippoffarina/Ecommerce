package org.acme.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.application.dto.Estimate;
import org.acme.domain.entity.Product;
import org.acme.port.ShipmentServicePort;

import java.util.Date;

@ApplicationScoped
public class ShipmentService implements ShipmentServicePort {

    @Inject
    CalculateShipCost cost;

    private Date date = new Date();

    public Estimate getEstimate(Long productId, int qta){

        Estimate estimate = new Estimate();
        Product p = Product.findById(productId);

        estimate.total = cost.shipCost(qta,"Italy",p.price);
        estimate.objectCost = p.price * qta;
        estimate.shipCost = estimate.total - p.price * qta;
        estimate.courer = "GLS";
        estimate.estimatedDate = date;

        return estimate; // Viene trasformato in JSON automaticamente
    }

}
