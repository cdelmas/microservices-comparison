package io.github.cdelmas.spike.sparkjava;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdelmas.spike.common.domain.Car;
import io.github.cdelmas.spike.common.domain.CarRepository;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static io.github.cdelmas.spike.common.hateoas.Link.self;
import static java.util.stream.Collectors.toList;

public class CarsResource {

    private final CarRepository carRepository;
    private final ObjectMapper objectMapper;

    public CarsResource(CarRepository carRepository, ObjectMapper objectMapper) {
        this.carRepository = carRepository;
        this.objectMapper = objectMapper;
    }

    public List<CarRepresentation> all(Request request, Response response) {
        List<Car> cars = carRepository.all();
        response.header("count", String.valueOf(cars.size()));
        response.type("application/json");
        return cars.stream().map(c -> {
                    CarRepresentation carRepresentation = new CarRepresentation(c);
                    carRepresentation.addLink(self(request.url() + "/" + c.getId()));
                    return carRepresentation;
                }
        ).collect(toList());
    }

    public CarRepresentation byId(Request request, Response response) {
        Optional<Car> car = carRepository.byId(Integer.parseInt(request.params(":id")));
        return car.map(c -> {
                    response.type("application/json");
                    CarRepresentation carRepresentation = new CarRepresentation(c);
                    carRepresentation.addLink(self(request.url()));
                    return carRepresentation;
                }
        ).orElseGet(() -> {
            response.status(404);
            return null;
        });
    }

    public String createCar(Request request, Response response) {
        Car car;
        try {
            car = objectMapper.readValue(request.body(), Car.class);
            carRepository.save(car);
            response.header("Location", request.url() + "/" + car.getId());
            response.status(201);
        } catch (IOException e) {
            response.status(400);
        }
        return "";
    }
}
