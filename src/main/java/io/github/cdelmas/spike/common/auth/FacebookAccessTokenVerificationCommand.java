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
        return new User(fbu.getString("id"), fbu.getString("name"));
    }

}
