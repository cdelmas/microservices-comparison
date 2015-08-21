package io.github.cdelmas.spike.restlet.hello;

import io.github.cdelmas.spike.common.domain.Car;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class HelloResource extends ServerResource {

    @Inject
    private CarService carService;

    @Get("txt")
    public String hello() {
        List<Car> cars = carService.list();
        List<String> carNames = cars.stream().map(Car::getName).collect(toList());

        return "Resource URI  : " + getReference() + '\n' + "Root URI      : "
                + getRootRef() + '\n' + "Routed part   : "
                + getReference().getBaseRef() + '\n' + "Remaining part: "
                + getReference().getRemainingPart() + '\n'
                + carNames.toString();
    }
}
