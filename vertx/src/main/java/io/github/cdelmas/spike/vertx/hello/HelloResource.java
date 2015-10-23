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
import io.github.cdelmas.spike.vertx.infrastructure.auth.MyUser;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.util.Arrays.asList;
import static java.util.Arrays.toString;
import static java.util.stream.Collectors.toList;

public class HelloResource {

    private final HttpClient httpClient;

    public HelloResource(HttpClient httpClient) {

        this.httpClient = httpClient;
    }

    public void hello(RoutingContext routingContext) {
        httpClient.getAbs("https://localhost:8090/cars")
                .putHeader("Accept", "application/json")
                .putHeader("Authorization", "Bearer " + routingContext.user().principal().getString("token"))
                .handler(response ->
                        response.bodyHandler(buffer -> {
                            if (response.statusCode() == 200) {
                                List<Car> cars = new ArrayList<>(asList(Json.decodeValue(buffer.toString(), Car[].class)));
                                routingContext.response()
                                        .putHeader("content-type", "test/plain")
                                        .setChunked(true)
                                        .write(cars.stream().map(Car::getName).collect(toList()).toString())
                                        .write(", and then Hello World from Vert.x-Web!")
                                        .end();
                            } else {
                                routingContext.response()
                                        .setStatusCode(response.statusCode())
                                        .putHeader("content-type", "test/plain")
                                        .setChunked(true)
                                        .write("Oops, something went wrong: " + response.statusMessage())
                                        .end();
                            }
                        }))
                .end();
    }
}
