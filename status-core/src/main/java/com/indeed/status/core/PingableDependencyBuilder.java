package com.indeed.status.core;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import javax.annotation.Nonnull;

/**
 * @author matts
 */
public abstract class PingableDependencyBuilder<T extends PingableDependency, B extends PingableDependencyBuilder<T,B>> {
    protected String id;
    protected String description;
    protected long timeout = PingableDependency.DEFAULT_TIMEOUT;
    protected long pingPeriod = PingableDependency.DEFAULT_PING_PERIOD;
    protected Urgency urgency;
    @Nonnull
    protected Supplier<Boolean> toggle = Suppliers.ofInstance(Boolean.TRUE);

    protected PingableDependencyBuilder() {}

    public abstract T build();

    public B setId(final String id) {
        this.id = id;
        return cast();
    }

    public B setDescription(final String description) {
        this.description = description;
        return cast();
    }

    public B setTimeout(final long timeout) {
        this.timeout = timeout;
        return cast();
    }

    public B setPingPeriod(final long pingPeriod) {
        this.pingPeriod = pingPeriod;
        return cast();
    }

    public B setUrgency(final Urgency urgency) {
        this.urgency = urgency;
        return cast();
    }

    public B setToggle(@Nonnull final Supplier<Boolean> toggle) {
        this.toggle = toggle;
        return cast();
    }

    private B cast() {
        //noinspection unchecked
        return (B)this;
    }
}