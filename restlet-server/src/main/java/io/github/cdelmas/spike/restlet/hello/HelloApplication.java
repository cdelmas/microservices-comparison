package io.github.cdelmas.spike.restlet.hello;

import org.restlet.Restlet;
import org.restlet.ext.guice.ResourceInjectingApplication;
import org.restlet.routing.Router;

public class HelloApplication extends ResourceInjectingApplication {

    @Override
    public Restlet createInboundRoot() {
        Router router = newRouter();
        router.attach("/hello", HelloResource.class);
        return router;
    }
}
