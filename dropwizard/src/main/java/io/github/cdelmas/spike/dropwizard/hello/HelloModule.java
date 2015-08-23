package io.github.cdelmas.spike.dropwizard.hello;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;
import io.github.cdelmas.spike.dropwizard.infrastructure.DropwizardServerConfiguration;

import javax.inject.Named;
import javax.ws.rs.client.Client;

public class HelloModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CarService.class).to(RemoteCarService.class);
    }

    @Provides
    @Named("template")
    public String provideTemplate(DropwizardServerConfiguration configuration) {
        return configuration.getTemplate();
    }

    @Provides
    @Named("defaultName")
    public String provideDefaultName(DropwizardServerConfiguration configuration) {
        return configuration.getDefaultName();
    }

    @Provides
    public Client createHttpClient(Environment environment, DropwizardServerConfiguration configuration) {
        return new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration()).build("toto le haricot");
    }
}
