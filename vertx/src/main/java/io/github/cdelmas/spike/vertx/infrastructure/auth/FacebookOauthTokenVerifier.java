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

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;

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
                            JsonObject json = new JsonObject(buffer.toString());
                            if (response.statusCode() != 200) {
                                String message = json.getJsonObject("error").getString("message");
                                resultHandler.handle(Future.failedFuture(message));
                            } else {
                                resultHandler.handle(Future.succeededFuture(
                                        new MyUser(Long.parseLong(json.getString("id")),
                                                json.getString("name"),
                                                token)));
                            }
                        }))
                .exceptionHandler(error ->
                        resultHandler.handle(Future.failedFuture(error.getMessage())))
                .end();
    }

}
