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
package io.github.cdelmas.spike.vertx.car;

import io.github.cdelmas.spike.common.domain.Car;
import io.github.cdelmas.spike.common.hateoas.Representation;

public class CarRepresentation extends Representation {
    private final String name;
    private final int id;

    public CarRepresentation(Car car) {
        this.name = car.getName();
        this.id = car.getId();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
