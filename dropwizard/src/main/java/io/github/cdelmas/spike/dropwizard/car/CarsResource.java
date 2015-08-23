package io.github.cdelmas.spike.dropwizard.car;

import io.github.cdelmas.spike.common.domain.Car;
import io.github.cdelmas.spike.common.domain.CarRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

@Path("/cars")
public class CarsResource {

    @Inject
    private CarRepository carRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        List<Car> cars = carRepository.all();
        return Response.ok(cars)
                .header("total-count", String.valueOf(cars.size()))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCar(Car car) {
        carRepository.save(car);
        return Response.noContent()
                .location(UriBuilder.fromResource(CarsResource.class).path("/{id}").build(String.valueOf(car.getId())))
                .build();
    }

}
