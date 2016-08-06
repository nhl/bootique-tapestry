package io.bootique.tapestry;

import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.bootique.ConfigModule;
import io.bootique.config.ConfigurationFactory;
import io.bootique.jetty.JettyModule;
import io.bootique.jetty.MappedFilter;
import io.bootique.tapestry.filter.BQTapestryFilterFactory;

public class TapestryModule extends ConfigModule {

    @Override
    public void configure(Binder binder) {
        JettyModule.contributeMappedFilters(binder).addBinding().to(Key.get(MappedFilter.class, TapestryFilter.class));
    }

    @Singleton
    @Provides
    @TapestryFilter
    MappedFilter createTapestryFilter(ConfigurationFactory configurationFactory, Injector injector) {
        return configurationFactory.config(BQTapestryFilterFactory.class, configPrefix).createTapestryFilter(injector);
    }
}
