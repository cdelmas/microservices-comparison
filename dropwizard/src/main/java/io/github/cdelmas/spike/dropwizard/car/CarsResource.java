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
