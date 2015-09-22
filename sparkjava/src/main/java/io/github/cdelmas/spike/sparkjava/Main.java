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
package io.github.cdelmas.spike.sparkjava;

import io.github.cdelmas.spike.common.domain.Car;
import io.github.cdelmas.spike.common.domain.CarRepository;
import io.github.cdelmas.spike.common.persistence.InMemoryCarRepository;

import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;

public class Main {
    public static void main(String[] args) {
        port(8099); // defaults on 4567
        get("/hello", (request, response) -> "Hello World");

        CarRepository carRepository = new InMemoryCarRepository();
        get("/cars", "application/json", (request, response) -> {
            List<Car> cars = carRepository.all();
            response.header("count", String.valueOf(cars.size()));
            response.type("application/json");
            return cars;
        });

//        post("/cars", "application/json", (request, response) -> {
//            request.
//            List<Car> cars = carRepository.all();
//            response.header("count", String.valueOf(cars.size()));
//            return cars;
//        });


    }
}
