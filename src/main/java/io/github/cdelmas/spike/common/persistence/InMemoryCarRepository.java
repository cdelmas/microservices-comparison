package io.github.cdelmas.spike.common.persistence;

import io.github.cdelmas.spike.common.domain.Car;
import io.github.cdelmas.spike.common.domain.CarRepository;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class InMemoryCarRepository implements CarRepository {

    private final AtomicInteger idGenerator = new AtomicInteger(1);
    private final Map<Integer, Car> repository = new ConcurrentHashMap<>();

    @Override
    public Optional<Car> byId(int id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public List<Car> all() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public void save(Car car) {
        if (car.getId() == null) {
            car.setId(idGenerator.getAndIncrement());
        }
        repository.put(car.getId(), car);
    }

    @Override
    public void update(Car car) {
        save(car);
    }

    @Override
    public void delete(Car car) {
        repository.remove(car.getId());
    }
}
