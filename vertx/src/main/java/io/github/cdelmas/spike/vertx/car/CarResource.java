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
