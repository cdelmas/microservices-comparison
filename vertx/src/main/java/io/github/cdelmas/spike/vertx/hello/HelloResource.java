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
package io.github.cdelmas.spike.vertx.hello;

import io.github.cdelmas.spike.common.domain.Car;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class HelloResource {

    private final RemoteCarService carService;

    public HelloResource(RemoteCarService carService) {
        this.carService = carService;
    }

    public void hello(RoutingContext routingContext) {
        List<Car> cars = carService.all();

        HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "test/plain");
        Buffer responseBuffer = Buffer.buffer();
        responseBuffer
                .appendString(cars.stream().map(Car::getName).collect(toList()).toString())
                .appendString(", and then Hello World from Vert.x-Web!");
        response.end(responseBuffer);
    }
}
