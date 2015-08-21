package io.github.cdelmas.spike.common.domain;

import java.util.List;
import java.util.Optional;

public interface CarRepository {

    Optional<Car> byId(int id);

    List<Car> all();

    void save(Car car);

    void update(Car car);

    void delete(Car car);
}
