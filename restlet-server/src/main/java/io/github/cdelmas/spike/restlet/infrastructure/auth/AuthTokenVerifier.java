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
                                    return Verifier.RESULT_VALID;
                                }).orElse(Verifier.RESULT_INVALID)
                )
                .orElse(Verifier.RESULT_MISSING);
    }

    private org.restlet.security.User createRestletUser(User user) {
        org.restlet.security.User restletUser = new org.restlet.security.User();
        restletUser.setIdentifier(user.getId());
        restletUser.setLastName(user.getName());
        return restletUser;
    }

}
