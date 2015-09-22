package io.github.cdelmas.spike.sparkjava;

import io.github.cdelmas.spike.common.domain.Car;
import io.github.cdelmas.spike.common.hateoas.Representation;

public class CarRepresentation extends Representation {

    private final String name;

    public CarRepresentation(Car c) {
        this.name = c.getName();
    }

    public String getName() {
        return name;
    }
}
