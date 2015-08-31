/*
   Copyright 2015 Cyril Delmas

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.cdelmas.spike.vertx.car;

import io.github.cdelmas.spike.common.domain.Car;
import io.github.cdelmas.spike.common.domain.CarRepository;
import io.github.cdelmas.spike.common.hateoas.Link;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

import static io.github.cdelmas.spike.common.hateoas.Link.self;
import static io.vertx.core.json.Json.encode;
import static java.util.stream.Collectors.toList;

public class CarsResource {

    private final CarRepository carRepository;


    public CarsResource(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void all(RoutingContext routingContext) {
        List<Car> all = carRepository.all();
        HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "application/json")
                .putHeader("total-count", String.valueOf(all.size()))
                .end(encode(all.stream().map(car -> {
                    CarRepresentation carRepresentation = new CarRepresentation(car);
                    carRepresentation.addLink(self(routingContext.request().absoluteURI() + "/" + car.getId()));
                    return carRepresentation;
                }).collect(toList())));
    }

    public void create(RoutingContext routingContext) {
        Car car = Json.decodeValue(routingContext.getBodyAsString(), Car.class);
        carRepository.save(car);
        HttpServerResponse response = routingContext.response();
        response.putHeader("Location", routingContext.request().absoluteURI() + "/" + car.getId())
                .setStatusCode(201)
                .end();
    }
}
