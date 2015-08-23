package io.github.cdelmas.spike.dropwizard.car;

import io.github.cdelmas.spike.common.domain.Car;
import io.github.cdelmas.spike.common.domain.CarRepository;
import io.github.cdelmas.spike.common.hateoas.Link;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Optional;

@Path("/cars/{id}")
@Produces(MediaType.APPLICATION_JSON)
public class CarResource {

    @Inject
    private CarRepository carRepository;

    @GET
    public Response byId(@PathParam("id") int carId) {
        Optional<Car> car = carRepository.byId(carId);
        return car.map(c -> {
            CarRepresentation carRepresentation = new CarRepresentation(c);
            carRepresentation.addLink(Link.self(UriBuilder.fromResource(CarResource.class).build(c.getId()).toString()));
            return Response.ok(new CarRepresentation(c)).build();
        }).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}
