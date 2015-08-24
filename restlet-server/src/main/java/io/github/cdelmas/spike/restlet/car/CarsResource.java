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
