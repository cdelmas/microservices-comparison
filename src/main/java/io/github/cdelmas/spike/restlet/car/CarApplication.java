package io.github.cdelmas.spike.restlet.car;

import org.restlet.Restlet;
import org.restlet.ext.guice.ResourceInjectingApplication;
import org.restlet.routing.Router;

public class CarApplication extends ResourceInjectingApplication {

    @Override
    public Restlet createInboundRoot() {
        Router router = newRouter();
        router.attach("/cars", CarsResource.class);
        router.attach("/cars/{id}", CarResource.class);
        return router;
    }
}
