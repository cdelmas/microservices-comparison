package io.github.cdelmas.spike.dropwizard.car;

import com.google.inject.AbstractModule;
import io.github.cdelmas.spike.common.domain.CarRepository;
import io.github.cdelmas.spike.common.persistence.InMemoryCarRepository;

public class CarModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CarRepository.class).to(InMemoryCarRepository.class);
    }
}
