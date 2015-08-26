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
import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;
import org.restlet.util.Series;

import java.util.List;

import static java.util.Arrays.asList;

public class RemoteCarService implements CarService {

    @Override
    public List<Car> list() {
        Client client = new Client(new Context(), Protocol.HTTPS);
        Series<Parameter> parameters = client.getContext().getParameters();
        parameters.add("truststorePath", System.getProperty("javax.net.ssl.trustStore"));

        ClientResource clientResource = new ClientResource("https://localhost:8043/api/cars/cars");
        clientResource.setNext(client);
        ChallengeResponse challenge = new ChallengeResponse(ChallengeScheme.HTTP_OAUTH_BEARER);
        challenge.setRawValue(Context.getCurrent().getAttributes().getOrDefault("fb-access-token", "").toString());
        clientResource.setChallengeResponse(challenge);
        CarServiceInterface carServiceInterface = clientResource.wrap(CarServiceInterface.class);
        Car[] allCars = carServiceInterface.getAllCars();
        try {
            client.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return asList(allCars);
    }
}
