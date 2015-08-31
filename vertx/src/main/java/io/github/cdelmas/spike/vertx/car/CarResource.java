package io.github.cdelmas.spike.vertx.car;

import io.github.cdelmas.spike.common.domain.Car;
import io.github.cdelmas.spike.common.domain.CarRepository;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.util.Optional;

import static io.github.cdelmas.spike.common.hateoas.Link.self;

public class CarResource {

    private final CarRepository carRepository;

    public CarResource(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void byId(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        String idParam = routingContext.request().getParam("id");
        if (idParam == null) {
            response.setStatusCode(400).end();
        } else {
            Optional<Car> car = carRepository.byId(Integer.parseInt(idParam));
            if (car.isPresent()) {
                CarRepresentation carRepresentation = new CarRepresentation(car.get());
                carRepresentation.addLink(self(routingContext.request().absoluteURI()));
                response.putHeader("content-type", "application/json")
                        .end(Json.encode(carRepresentation));
            } else {
                response.setStatusCode(404).end();
            }
        }
    }
}
