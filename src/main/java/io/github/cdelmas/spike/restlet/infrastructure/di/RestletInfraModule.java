package io.github.cdelmas.spike.restlet.infrastructure.di;

import com.google.inject.AbstractModule;
import io.github.cdelmas.spike.restlet.infrastructure.auth.AuthTokenVerifier;
import org.restlet.security.Verifier;

public class RestletInfraModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Verifier.class).to(AuthTokenVerifier.class);
    }
}
