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
