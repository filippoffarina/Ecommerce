package org.acme.port;

import org.acme.application.dto.Estimate;

public interface ShipmentServicePort {
    public Estimate getEstimate(Long productID,int qta,String zone);
}
