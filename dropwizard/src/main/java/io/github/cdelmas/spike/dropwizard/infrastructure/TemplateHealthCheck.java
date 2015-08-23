package io.github.cdelmas.spike.dropwizard.infrastructure;

import com.codahale.metrics.health.HealthCheck;

import javax.inject.Inject;
import javax.inject.Named;

public class TemplateHealthCheck extends HealthCheck {
    private final String template;

    @Inject
    public TemplateHealthCheck(@Named("template") String template) {
        this.template = template;
    }

    @Override
    protected Result check() throws Exception {
        final String saying = String.format(template, "TEST");
        if (!saying.contains("TEST")) {
            return Result.unhealthy("template doesn't include a name");
        }
        return Result.healthy();
    }

}
