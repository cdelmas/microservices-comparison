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
import io.github.cdelmas.spike.common.hateoas.Link;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Optional;

@Path("/cars/{id}")
@Produces(MediaType.APPLICATION_JSON)
public class CarResource {

    @Context
    UriInfo uriInfo;

    @Inject
    private CarRepository carRepository;

    @GET
    public Response byId(@PathParam("id") int carId) {
        Optional<Car> car = carRepository.byId(carId);
        return car.map(c -> {
            CarRepresentation carRepresentation = new CarRepresentation(c);
            carRepresentation.addLink(Link.self(uriInfo.getAbsolutePathBuilder().build(c.getId()).toString()));
            return Response.ok(carRepresentation).build();
        }).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}
