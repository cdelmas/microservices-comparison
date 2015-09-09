package io.github.cdelmas.spike.vertx.infrastructure.auth;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;

/**
 * (c) Lectra.
 *
 * @author c.delmas
 */
public class FacebookOauthTokenVerifier implements AuthProvider {

    private final HttpClient httpClient;

    public FacebookOauthTokenVerifier(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public void authenticate(JsonObject credentials, Handler<AsyncResult<User>> resultHandler) {
        String token = credentials.getString("token");

        httpClient.getAbs("https://graph.facebook.com:443/v2.4/me?access_token=" + token)
                .handler(response ->
                        response.bodyHandler(buffer -> {
                            JsonObject userCredentials = new JsonObject(buffer.toString());
                            resultHandler.handle(Future.succeededFuture(
                                    new MyUser(Integer.parseInt(userCredentials.getString("id")),
                                            userCredentials.getString("name"),
                                            token)));
                        }).exceptionHandler(error ->
                                resultHandler.handle(Future.failedFuture(error.getMessage()))))
                .end();
    }

}
