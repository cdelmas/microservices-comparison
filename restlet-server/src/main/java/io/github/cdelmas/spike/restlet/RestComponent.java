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
package io.github.cdelmas.spike.restlet;

import io.github.cdelmas.spike.restlet.car.Car;
import io.github.cdelmas.spike.restlet.hello.Hello;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.Verifier;
import org.restlet.util.Series;

import javax.inject.Inject;

public class RestComponent extends Component {

    @Inject
    public RestComponent(@Hello Application helloApp, @Car Application carApp, Verifier authTokenVerifier) {
        getClients().add(Protocol.HTTPS);
        Server secureServer = getServers().add(Protocol.HTTPS, 8043);
        Series<Parameter> parameters = secureServer.getContext().getParameters();
        parameters.add("sslContextFactory", "org.restlet.engine.ssl.DefaultSslContextFactory");
        parameters.add("keyStorePath", System.getProperty("javax.net.ssl.keyStorePath"));
        getDefaultHost().attach("/api/hello", secure(helloApp, authTokenVerifier, "Hello"));
        getDefaultHost().attach("/api/cars", secure(carApp, authTokenVerifier, "Cars"));
    }

    private Restlet secure(Application app, Verifier verifier, String realm) {
        ChallengeAuthenticator guard = new ChallengeAuthenticator(getContext().createChildContext(),
                ChallengeScheme.CUSTOM, realm);
        guard.setVerifier(verifier);
        guard.setNext(app);
        return guard;
    }

}
