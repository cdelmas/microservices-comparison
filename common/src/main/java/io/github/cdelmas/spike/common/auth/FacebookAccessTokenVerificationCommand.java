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
package io.github.cdelmas.spike.common.auth;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.restfb.DefaultFacebookClient;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import javaslang.control.Try;

public class FacebookAccessTokenVerificationCommand extends HystrixCommand<Try<User>> implements AccessTokenVerificationCommand {

    private final String accessToken;

    public FacebookAccessTokenVerificationCommand(String accessToken) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("AccessTokenVerification"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("FacebookAccessTokenVerification")));
        this.accessToken = accessToken;
    }

    @Override
    public Try<User> executeCommand() {
        return super.execute();
    }


    @Override
    protected Try<User> run() throws Exception {
        DefaultFacebookClient fb = new DefaultFacebookClient(accessToken, Version.VERSION_2_4);

        return Try.of(() -> fb.fetchObject("me", JsonObject.class))
                .map(this::createUser);
    }

    private User createUser(JsonObject fbu) {
        User user = new User(fbu.getString("id"), fbu.getString("name"));
        user.setToken(accessToken);
        return user;
    }

}
