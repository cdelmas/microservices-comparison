package io.github.cdelmas.spike.dropwizard.hello;

import io.github.cdelmas.spike.common.domain.Car;

import java.util.List;

public interface CarService {

    List<Car> getAllCars();
}
