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

import io.github.cdelmas.spike.common.domain.CarRepository;
import io.github.cdelmas.spike.common.persistence.InMemoryCarRepository;
import io.github.cdelmas.spike.vertx.car.CarResource;
import io.github.cdelmas.spike.vertx.car.CarsResource;
import io.github.cdelmas.spike.vertx.hello.HelloResource;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class Main {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        HelloResource helloResource = new HelloResource();
        router.get("/hello").produces("text/plain").handler(helloResource::hello);

        CarRepository carRepository = new InMemoryCarRepository();
        CarsResource carsResource = new CarsResource(carRepository);
        router.route("/cars*").handler(BodyHandler.create());
        router.get("/cars").produces("application/json").handler(carsResource::all);
        router.post("/cars").consumes("application/json").handler(carsResource::create);

        CarResource carResource = new CarResource(carRepository);
        router.get("/cars/:id").produces("application/json").handler(carResource::byId);

        server.requestHandler(router::accept).listen(8090);
    }
}
