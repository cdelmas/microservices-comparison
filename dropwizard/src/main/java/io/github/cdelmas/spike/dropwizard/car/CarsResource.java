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

import io.dropwizard.auth.Auth;
import io.github.cdelmas.spike.common.auth.User;
import io.github.cdelmas.spike.common.domain.Car;
import io.github.cdelmas.spike.common.domain.CarRepository;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/cars")
public class CarsResource {

    @Context
    UriInfo uriInfo;

    @Inject
    private CarRepository carRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(@Auth User user) {
        List<Car> cars = carRepository.all();
        List<CarRepresentation> representations = cars.stream()
                .map(c -> {
                    CarRepresentation representation = new CarRepresentation(c);
                    representation.addLink(io.github.cdelmas.spike.common.hateoas.Link.self(uriInfo.getAbsolutePathBuilder().build(c.getId()).toString()));
                    return representation;
                }).collect(toList());
        return Response.ok(representations)
                .header("total-count", String.valueOf(cars.size()))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCar(@Auth User user, Car car) {
        carRepository.save(car);
        return Response.created(UriBuilder.fromResource(CarsResource.class).path("/{id}").build(String.valueOf(car.getId())))
                .build();
    }

}
