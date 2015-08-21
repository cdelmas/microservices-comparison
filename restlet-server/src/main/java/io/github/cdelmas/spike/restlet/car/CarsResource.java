package io.github.cdelmas.spike.restlet.car;

import io.github.cdelmas.spike.common.domain.CarRepository;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import javax.inject.Inject;
import java.util.List;

public class CarsResource extends ServerResource {

    @Inject
    private CarRepository carRepository;

    @Get("json")
    public List<io.github.cdelmas.spike.common.domain.Car> all() {
        List<io.github.cdelmas.spike.common.domain.Car> cars = carRepository.all();
        getResponse().getHeaders().add("total-count", String.valueOf(cars.size()));
        return cars;
    }

    @Post("json")
    public void createCar(io.github.cdelmas.spike.common.domain.Car car) {
        carRepository.save(car);
        setLocationRef(getReference().addSegment(String.valueOf(car.getId())));
    }

}
