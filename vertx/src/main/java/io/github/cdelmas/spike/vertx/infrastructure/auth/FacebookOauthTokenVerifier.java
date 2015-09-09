package io.github.cdelmas.spike.vertx.infrastructure.auth;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;

/**
 * (c) Lectra.
 * @author c.delmas
 */
public class FacebookOauthTokenVerifier implements AuthProvider {

    // https://graph.facebook.com/v2.4/me -> validate

    @Override
    public void authenticate(JsonObject credentials, Handler<AsyncResult<User>> resultHandler) {

        String token = credentials.getString("token");

        // TODO call facebook (blocking?) with facebook4j or restfb or call it by hand (easy)
        // https://graph.facebook.com/v2.4/me
        if (token != null) {
            resultHandler.handle(Future.succeededFuture(new MyUser("bob", token)));
        } else {
            resultHandler.handle(Future.failedFuture("Token not Active"));

        }
    }

}
