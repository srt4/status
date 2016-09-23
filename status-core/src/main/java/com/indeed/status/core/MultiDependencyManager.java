package com.indeed.status.core;

import org.apache.log4j.Logger;

import javax.annotation.Nonnull;

/**
 * Manages the status of multiple dependencies and produces an overall healthcheck.
 *
 * @author srt4@uw.edu
 */
public class MultiDependencyManager extends AbstractDependencyManager {
    private static final Logger LOGGER = Logger.getLogger(MultiDependencyManager.class);

    public MultiDependencyManager(@Nonnull final String serviceName, @Nonnull final Dependency... dependencies) {
        super(serviceName, LOGGER);

        for (final Dependency dependency : dependencies) {
            addDependency(dependency);
        }
    }

    public ServiceHealth getServiceHealth() {
        return new ServiceHealth((CheckResultSet.DetailedSystemReport)evaluate().summarizeBySystemReporter(true),
                getDependencies());
    }
}