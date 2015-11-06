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
package io.github.cdelmas.spike.vertx.infrastructure.auth;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.impl.AuthHandlerImpl;

public class BearerAuthHandler extends AuthHandlerImpl {

    public BearerAuthHandler(AuthProvider authProvider) {
        super(authProvider);
    }

    @Override
    public void handle(RoutingContext routingContext) {
        HttpServerRequest request = routingContext.request();
        request.pause();
        String authorization = request.headers().get(HttpHeaders.AUTHORIZATION);
        if (authorization == null) {
            routingContext.fail(401);
        } else {
            String[] parts = authorization.split(" ");
            if (parts.length != 2) {
                routingContext.fail(401);
            } else {
                String scheme = parts[0];
                if (!"bearer".equalsIgnoreCase(scheme)) {
                    routingContext.fail(401);
                } else {
                    String token = parts[1];
                    JsonObject credentials = new JsonObject();
                    credentials.put("token", token);

                    authProvider.authenticate(credentials, res -> {
                        if (res.succeeded()) {
                            routingContext.setUser(res.result());
                            request.resume();
                            routingContext.next();
                        } else {
                            routingContext.fail(401);
                        }
                    });
                }
            }
        }
    }
}
