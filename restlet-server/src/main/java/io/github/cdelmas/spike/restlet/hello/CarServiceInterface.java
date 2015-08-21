package io.github.cdelmas.spike.restlet.hello;

import io.github.cdelmas.spike.common.domain.Car;
import org.restlet.resource.Get;

public interface CarServiceInterface {

    @Get
    public Car[] getAllCars();
}
