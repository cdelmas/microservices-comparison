package io.github.cdelmas.spike.springboot.car;

import io.github.cdelmas.spike.common.domain.Car;
import org.springframework.hateoas.ResourceSupport;

public class CarRepresentation extends ResourceSupport {

    private final String name;

    public CarRepresentation(Car car) {
        this.name = car.getName();
    }
}
