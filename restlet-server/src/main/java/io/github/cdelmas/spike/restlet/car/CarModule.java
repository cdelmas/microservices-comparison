package io.github.cdelmas.spike.restlet.car;

import com.google.inject.AbstractModule;
import io.github.cdelmas.spike.common.domain.CarRepository;
import io.github.cdelmas.spike.common.persistence.InMemoryCarRepository;
import org.restlet.Application;

public class CarModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Application.class).annotatedWith(Car.class).to(CarApplication.class);
        bind(CarRepository.class).to(InMemoryCarRepository.class);
    }
}
