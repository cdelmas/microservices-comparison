package io.github.cdelmas.spike.sparkjava;

import io.github.cdelmas.spike.common.auth.AccessTokenVerificationCommandFactory;
import io.github.cdelmas.spike.common.auth.User;
import javaslang.control.Try;
import spark.Request;
import spark.Response;

import static spark.Spark.halt;

public class AuthenticationFilter {

    private final AccessTokenVerificationCommandFactory accessTokenVerificationCommandFactory;

    public AuthenticationFilter(AccessTokenVerificationCommandFactory accessTokenVerificationCommandFactory) {
        this.accessTokenVerificationCommandFactory = accessTokenVerificationCommandFactory;
    }

    public void filter(Request request, Response response) {
        String token = readToken(request);
        Try<User> user = accessTokenVerificationCommandFactory.createVerificationCommand(token).executeCommand();
        user.peek(u ->
                request.attribute("user", u))
                .orElseRun(e -> halt(401, "{\"error\":\"" + e.getMessage() + "\"}"));
    }

    private String readToken(Request request) {
        String authorization = request.headers("Authorization");
        return authorization.substring(authorization.indexOf(' ') + 1);
    }
}
