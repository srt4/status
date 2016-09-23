package com.indeed.status.core;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Represents an overall health summary for a service, including helper methods to assist with load balancer placement
 *
 * @author srt4@uw.edu
 */
public class ServiceHealth {
    private final boolean wantsTraffic;
    private final CheckResultSet.DetailedSystemReport systemReport;
    private final Collection<Dependency> dependencies;

    public ServiceHealth(@Nonnull final CheckResultSet.DetailedSystemReport systemReport,
                         @Nonnull final Collection<Dependency> dependencies) {
        this.systemReport = systemReport;
        this.dependencies = dependencies;
        wantsTraffic = systemReport.condition.isBetterThan(CheckStatus.MAJOR);
    }

    /**
     *
     * @return true if the service is capable of handling requests
     */
    public boolean wantsTraffic() {
        return wantsTraffic;
    }

    /**
     *
     * @return true if the service should be triggering alerts
     */
    public boolean isCaution() {
        for (final Dependency dependency : dependencies) {
            switch (dependency.getUrgency()) {
                case REQUIRED: case STRONG:
                    if (!isDependencyOkay(dependency)) {
                        return true;
                    }
                    break;
                // A weak dependency being degraded is not caution
                case WEAK:
                    break;
                case NONE:
                    break;
                case UNKNOWN:
                    break;
            }
        }

        return false;
    }

    public CheckResultSet.DetailedSystemReport getSystemReport() {
        return systemReport;
    }

    private boolean isDependencyOkay(@Nonnull final Dependency dependency) {
        try {
            return dependency.call().getStatus().isBetterThan(CheckStatus.MINOR);
        }
        catch (final Exception e) {
            return false;
        }
    }
}