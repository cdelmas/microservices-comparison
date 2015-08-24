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
import io.github.cdelmas.spike.common.hateoas.Link;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.inject.Inject;

public class CarResource extends ServerResource {

    @Inject
    private CarRepository carRepository;
    private int carId;

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();
        this.carId = Integer.parseInt(getAttribute("id"));
    }

    @Get("json")
    public CarRepresentation byId() {
        io.github.cdelmas.spike.common.domain.Car car = carRepository.byId(carId).orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND));
        CarRepresentation carRepresentation = new CarRepresentation(car);
        carRepresentation.addLink(Link.self(getReference().toString()));
        carRepresentation.addLink(Link.remove(getReference().toString()));
        return carRepresentation;
    }
}
