package com.bicycle.api.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "nameOnly", types = Bike.class)
public interface BikeProjection {
    String getName();
}