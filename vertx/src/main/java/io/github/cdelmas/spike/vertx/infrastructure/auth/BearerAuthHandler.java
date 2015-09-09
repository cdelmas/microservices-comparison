package io.github.cdelmas.spike.vertx.infrastructure.auth;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.impl.AuthHandlerImpl;

/**
 * (c) Lectra.
 * @author c.delmas
 */
public class BearerAuthHandler extends AuthHandlerImpl {

    public BearerAuthHandler(AuthProvider authProvider) {
        super(authProvider);
    }

    @Override
    public void handle(RoutingContext routingContext) {
        HttpServerRequest request = routingContext.request();
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
