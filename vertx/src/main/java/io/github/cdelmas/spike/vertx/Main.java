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
package io.github.cdelmas.spike.vertx;

import com.fasterxml.jackson.databind.DeserializationFeature;
import io.github.cdelmas.spike.common.domain.Car;
import io.github.cdelmas.spike.common.domain.CarRepository;
import io.github.cdelmas.spike.common.persistence.InMemoryCarRepository;
import io.github.cdelmas.spike.vertx.car.CarResource;
import io.github.cdelmas.spike.vertx.car.CarsResource;
import io.github.cdelmas.spike.vertx.hello.HelloResource;
import io.github.cdelmas.spike.vertx.infrastructure.auth.BearerAuthHandler;
import io.github.cdelmas.spike.vertx.infrastructure.auth.FacebookOauthTokenVerifier;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.Json;
import io.vertx.core.net.JksOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.AuthHandler;
import io.vertx.ext.web.handler.BodyHandler;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) {
        // TODO start a vertx instance
        // deploy verticles / one per resource in this case

        Json.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        Vertx vertx = Vertx.vertx();

        HttpClientOptions clientOptions = new HttpClientOptions()
                .setSsl(true)
                .setTrustStoreOptions(new JksOptions()
                        .setPath(System.getProperty("javax.net.ssl.trustStore"))
                        .setPassword(System.getProperty("javax.net.ssl.trustStorePassword")));
        HttpClient httpClient = vertx.createHttpClient(clientOptions);


        Router router = Router.router(vertx);
        AuthHandler auth = new BearerAuthHandler(new FacebookOauthTokenVerifier());
        router.route("/*").handler(auth);

        HelloResource helloResource = new HelloResource(httpClient);
        router.get("/hello").produces("text/plain").handler(routingContext ->
                helloResource.hello(
                        routingContext::user,
                        cars ->
                                routingContext.response()
                                        .putHeader("content-type", "test/plain")
                                        .setChunked(true)
                                        .write(cars.stream().map(Car::getName).collect(toList()).toString())
                                        .write(", and then Hello World from Vert.x-Web!")
                                        .end()));

        CarRepository carRepository = new InMemoryCarRepository();
        CarsResource carsResource = new CarsResource(carRepository);
        router.route("/cars*").handler(BodyHandler.create());
        router.get("/cars").produces("application/json").handler(carsResource::all);
        router.post("/cars").consumes("application/json").handler(carsResource::create);

        CarResource carResource = new CarResource(carRepository);
        router.get("/cars/:id").produces("application/json").handler(carResource::byId);

        HttpServerOptions serverOptions = new HttpServerOptions()
                .setSsl(true)
                .setKeyStoreOptions(new JksOptions()
                        .setPath(System.getProperty("javax.net.ssl.keyStorePath"))
                        .setPassword(System.getProperty("javax.net.ssl.keyStorePassword")))
                .setPort(8090);
        HttpServer server = vertx.createHttpServer(serverOptions);
        server.requestHandler(router::accept).listen();
    }
}
