package io.github.cdelmas.spike.springboot.car;

import io.github.cdelmas.spike.common.domain.Car;
import io.github.cdelmas.spike.common.domain.CarRepository;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@EnableOAuth2Resource
@RestController
@RequestMapping(value = "/cars", produces = "application/json")
public class CarsController {

    private final CarRepository carRepository;

    @Inject
    public CarsController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CarRepresentation>> allCars() {
        List<Car> cars = carRepository.all();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("total-count", String.valueOf(cars.size()));
        return new ResponseEntity<>(cars.stream().map(CarRepresentation::new).collect(toList()), responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CarRepresentation> byId(@PathVariable("id") String carId) {
        Optional<Car> car = carRepository.byId(Integer.parseInt(carId));
        return car.map(c -> {
            CarRepresentation rep = new CarRepresentation(c);
            rep.add(linkTo(methodOn(CarsController.class).byId(carId)).withSelfRel());
            return new ResponseEntity<>(rep, HttpStatus.OK);
        })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<CarRepresentation> create(@RequestBody Car car) {
        carRepository.save(car);
        HttpHeaders responseHeaders = new HttpHeaders();
        Link link = linkTo(methodOn(CarsController.class).byId(String.valueOf(car.getId()))).withSelfRel();
        responseHeaders.set("Location", link.getHref());
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }
}
