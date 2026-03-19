package org.acme.application.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CalculateShipCost {

    public double shipCost(int numOfObjects, String zone,int price){

        if("Italy".equals(zone))
            return 5.00 + numOfObjects*1.5 + price*numOfObjects ;

        return 10 + numOfObjects*2 + price*numOfObjects;

    }
}
