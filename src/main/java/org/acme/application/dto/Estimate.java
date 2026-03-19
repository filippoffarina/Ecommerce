package org.acme.application.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.Date;

@RegisterForReflection
public class Estimate {

    public double total;
    public double objectCost;
    public double shipCost;
    public String courer;
    public Date estimatedDate;
}
