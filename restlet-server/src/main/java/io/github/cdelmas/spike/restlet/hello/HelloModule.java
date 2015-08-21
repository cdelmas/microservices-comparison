package io.github.cdelmas.spike.restlet.hello;

import com.google.inject.AbstractModule;
import org.restlet.Application;

public class HelloModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Application.class).annotatedWith(Hello.class).to(HelloApplication.class);
        bind(CarService.class).to(RemoteCarService.class);
    }
}
