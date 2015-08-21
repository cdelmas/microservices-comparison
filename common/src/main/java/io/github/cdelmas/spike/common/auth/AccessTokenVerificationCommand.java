package io.github.cdelmas.spike.common.auth;

import javaslang.control.Try;

public interface AccessTokenVerificationCommand {
    Try<User> executeCommand();
}
