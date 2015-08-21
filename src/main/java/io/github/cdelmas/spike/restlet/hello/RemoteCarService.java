package io.github.cdelmas.spike.restlet.hello;

import com.restfb.json.JsonObject;
import io.github.cdelmas.spike.common.domain.Car;
import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.Header;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.util.Series;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class RemoteCarService implements CarService {

    @Override
    public List<Car> list() {
        Client client = new Client(new Context(), Protocol.HTTPS);
        Series<Parameter> parameters = client.getContext().getParameters();
        parameters.add("truststorePath", "/home/cyril/Work/localhost.jks");
        parameters.add("truststorePassword", "louise");
        parameters.add("truststoreType", "JKS");

        ClientResource clientResource = new ClientResource("https://localhost:8043/api/cars/cars");
        clientResource.setNext(client);
        Series<Header> headers =
                (Series<Header>) clientResource.getRequestAttributes().get(
                        "org.restlet.http.headers");
        if (headers == null) {
            headers = new Series<>(Header.class);
            clientResource.getRequestAttributes().put("org.restlet.http.headers", headers);
        }
        headers.set("fb-access-token", Context.getCurrent().getAttributes().getOrDefault("fb-access-token", "").toString());
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
