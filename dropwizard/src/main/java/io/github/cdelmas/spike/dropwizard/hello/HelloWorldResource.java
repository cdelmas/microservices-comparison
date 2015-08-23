package io.github.cdelmas.spike.dropwizard.hello;

import com.codahale.metrics.annotation.Timed;
import io.github.cdelmas.spike.common.domain.Car;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    @Inject
    private CarService carService;

    @Inject
    public HelloWorldResource(@Named("template") String template, @Named("defaultName") String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.orElse(defaultName));
        List<Car> allCars = carService.getAllCars();
        return new Saying(counter.incrementAndGet(), value + " " + '\n' + allCars.toString());
    }

}

