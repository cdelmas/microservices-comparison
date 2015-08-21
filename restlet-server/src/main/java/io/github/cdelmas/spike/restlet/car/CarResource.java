package io.github.cdelmas.spike.restlet.car;

import io.github.cdelmas.spike.common.domain.CarRepository;
import io.github.cdelmas.spike.common.hateoas.Link;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.inject.Inject;

public class CarResource extends ServerResource {

    @Inject
    private CarRepository carRepository;
    private int carId;

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();
        this.carId = Integer.parseInt(getAttribute("id"));
    }

    @Get("json")
    public CarRepresentation byId() {
        io.github.cdelmas.spike.common.domain.Car car = carRepository.byId(carId).orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND));
        CarRepresentation carRepresentation = new CarRepresentation(car);
        carRepresentation.addLink(Link.self(getReference().toString()));
        return carRepresentation;
    }
}
