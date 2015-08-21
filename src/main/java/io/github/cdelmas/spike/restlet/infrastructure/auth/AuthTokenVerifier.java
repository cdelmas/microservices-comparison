package io.github.cdelmas.spike.restlet.infrastructure.auth;

import io.github.cdelmas.spike.common.auth.AccessTokenVerificationCommandFactory;
import io.github.cdelmas.spike.common.auth.User;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.security.Verifier;

import javax.inject.Inject;
import java.util.Optional;

public class AuthTokenVerifier implements Verifier {

    @Inject
    private AccessTokenVerificationCommandFactory accessTokenVerificationCommandFactory;

    @Override
    public int verify(Request request, Response response) {
        Optional<String> accessToken = Optional.ofNullable(request.getHeaders().getFirstValue("fb-access-token", true));

        return accessToken
                .map(t -> {
                    Context.getCurrent().getAttributes().put("fb-access-token", t);
                    return accessTokenVerificationCommandFactory.createVerificationCommand(t).executeCommand();
                })
                .map(authUser ->
                                authUser.map(u -> {
                                    request.getClientInfo().setUser(createRestletUser(u));
                                    return RESULT_VALID;
                                }).orElse(RESULT_INVALID)
                )
                .orElse(RESULT_MISSING);
    }

    private org.restlet.security.User createRestletUser(User user) {
        org.restlet.security.User restletUser = new org.restlet.security.User();
        restletUser.setIdentifier(user.getId());
        restletUser.setLastName(user.getName());
        return restletUser;
    }

}
