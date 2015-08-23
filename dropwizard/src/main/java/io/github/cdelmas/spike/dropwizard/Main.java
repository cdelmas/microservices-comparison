package io.github.cdelmas.spike.dropwizard;

import io.github.cdelmas.spike.dropwizard.infrastructure.DropwizardApplication;

public class Main {

    public static void main(String[] args) throws Exception {
        new DropwizardApplication().run(args);
    }
}
