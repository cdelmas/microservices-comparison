package io.github.cdelmas.spike.vertx.infrastructure.auth;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AbstractUser;
import io.vertx.ext.auth.AuthProvider;

public class MyUser extends AbstractUser {

    private AuthProvider authProvider;
    private final String name;
    private final String token;

    public MyUser(String name, String token) {
        this.name = name;
        this.token = token;
    }

    @Override
    protected void doIsPermitted(String permission, Handler<AsyncResult<Boolean>> resultHandler) {
        resultHandler.handle(Future.failedFuture("Not implemented yet"));
    }

    @Override
    public JsonObject principal() {
        return new JsonObject().put("name", name).put("token", token);
    }

    @Override
    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }
}
