package org.acme.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.application.dto.Estimate;
import org.acme.domain.entity.Product;
import org.acme.port.ShipmentServicePort;

import java.util.Date;
import java.util.Optional;

@ApplicationScoped
public class ShipmentService implements ShipmentServicePort {

    @Inject
    CalculateShipCost cost;

    @Inject
    ProductService productService;


    public Estimate getEstimate(Long productId, int qta,String zone){

        Estimate estimate = new Estimate();
        Optional<Product> p = productService.findProductById(productId);

        estimate.total = cost.shipCost(qta,zone,p.get().getPrice());
        estimate.objectCost = p.get().getPrice() * qta;
        estimate.shipCost = estimate.total - p.get().getPrice() * qta;
        estimate.courer = "GLS";
        estimate.estimatedDate = new Date();

        return estimate; // Viene trasformato in JSON automaticamente
    }

}
