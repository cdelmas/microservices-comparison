package io.github.cdelmas.spike.restlet;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.cdelmas.spike.restlet.car.CarModule;
import io.github.cdelmas.spike.restlet.hello.HelloModule;
import io.github.cdelmas.spike.restlet.infrastructure.di.RestletInfraModule;
import org.restlet.ext.guice.SelfInjectingServerResourceModule;

public class Main {

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new SelfInjectingServerResourceModule(),
                new RestletInfraModule(),
                new CarModule(),
                new HelloModule());
        RestComponent component = injector.getInstance(RestComponent.class);
        component.start();
    }
}
