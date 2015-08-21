package io.github.cdelmas.spike.common.auth;

public class AccessTokenVerificationCommandFactory {

    public AccessTokenVerificationCommand createVerificationCommand(String accessToken) {
        return new FacebookAccessTokenVerificationCommand(accessToken);
    }

}
