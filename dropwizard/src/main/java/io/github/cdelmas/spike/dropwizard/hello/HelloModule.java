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
package io.github.cdelmas.spike.dropwizard.hello;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;
import io.github.cdelmas.spike.dropwizard.infrastructure.DropwizardServerConfiguration;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.client.Client;

public class HelloModule extends AbstractModule {

    private Client client;

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
        if (client == null) {
            client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration()).build("toto le haricot");
        }
        return client;
    }
}
