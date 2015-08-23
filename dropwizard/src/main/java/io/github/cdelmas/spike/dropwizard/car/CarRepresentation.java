package io.github.cdelmas.spike.dropwizard.car;

import io.github.cdelmas.spike.common.domain.Car;
import io.github.cdelmas.spike.common.hateoas.Representation;

public class CarRepresentation extends Representation {

    private int id;
    private String name;

    public CarRepresentation(Car car) {
        this.id = car.getId();
        this.name = car.getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
