package io.github.cdelmas.spike.springboot.hello;

import io.github.cdelmas.spike.common.domain.Car;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@RestController
public class SampleController {

    @RequestMapping("/")
    @ResponseBody
    String home() {

        RestTemplate rest = new RestTemplate();
        List<Car> cars = asList(rest.getForObject("https://localhost:8443/cars", Car[].class));

        return "Hello World! " + cars.stream().map(Car::getName).collect(toList());
    }

}
