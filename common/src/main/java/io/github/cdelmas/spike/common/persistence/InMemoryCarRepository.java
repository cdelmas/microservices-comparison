/*
   Copyright 2015 Cyril Delmas

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
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
