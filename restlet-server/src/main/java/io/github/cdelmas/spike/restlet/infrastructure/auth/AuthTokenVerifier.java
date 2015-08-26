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
import javaslang.control.Try;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.security.Verifier;

import javax.inject.Inject;

public class AuthTokenVerifier implements Verifier {

    @Inject
    private AccessTokenVerificationCommandFactory accessTokenVerificationCommandFactory;

    @Override
    public int verify(Request request, Response response) {
        final String token;

        try {
            ChallengeResponse cr = request.getChallengeResponse();
            if (cr == null) {
                return RESULT_MISSING;
            } else if (ChallengeScheme.HTTP_OAUTH_BEARER.equals(cr.getScheme())) {
                final String bearer = cr.getRawValue();
                if (bearer == null || bearer.isEmpty()) {
                    return RESULT_MISSING;
                }
                token = bearer;
            } else {
                return RESULT_UNSUPPORTED;
            }
        } catch (Exception ex) {
            return RESULT_INVALID;
        }

        Try<User> user = accessTokenVerificationCommandFactory.createVerificationCommand(token).executeCommand();
        return user.map(u -> {
            org.restlet.security.User restletUser = createRestletUser(u);
            request.getClientInfo().setUser(restletUser);
            Context.getCurrent().getAttributes().put("fb-access-token", token);
            return RESULT_VALID;
        }).orElse(RESULT_INVALID);
    }

    private org.restlet.security.User createRestletUser(User user) {
        org.restlet.security.User restletUser = new org.restlet.security.User();
        restletUser.setIdentifier(user.getId());
        restletUser.setLastName(user.getName());
        return restletUser;
    }

}
