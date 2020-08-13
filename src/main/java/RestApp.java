import data.User;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import dao.NameDAO;
import io.dropwizard.Application;
import io.dropwizard.auth.*;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.jose4j.jwt.consumer.JwtContext;
import org.skife.jdbi.v2.DBI;
import resources.AuthResource;
import resources.JWTGenResource;
import resources.NameResource;
import resources.TelegramResource;
import auth.AuthFilterUtils;

public class RestApp extends Application<RestConfig> {

    public static void main(String[] args) throws Exception {
        new RestApp().run(args);
    }

    @Override
    public void initialize(final Bootstrap<RestConfig> bootstrap) {

        bootstrap.addBundle(new MigrationsBundle<RestConfig>() {
            @Override
            public DataSourceFactory getDataSourceFactory(RestConfig configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(RestConfig restConfig, Environment env) throws Exception {

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(env, restConfig.getDataSourceFactory(), "postgresql");
        final NameDAO nameDAO = jdbi.onDemand(NameDAO.class);
        final NameResource nameResource = new NameResource(nameDAO);
        env.jersey().register(nameResource);

        registerResources(env);
        registerAuthFilters(env);
    }

    private void registerResources(Environment env) {
        env.jersey().register(TelegramResource.class);
        env.jersey().register(AuthResource.class);
        env.jersey().register(JWTGenResource.class);
    }

    private void registerAuthFilters(Environment env) {
        AuthFilterUtils authFilterUtils = new AuthFilterUtils();
        final AuthFilter<BasicCredentials, PrincipalImpl> basicFilter = authFilterUtils.buildBasicAuthFilter();
        final AuthFilter<JwtContext, User> jwtFilter = authFilterUtils.buildJwtAuthFilter();

        final PolymorphicAuthDynamicFeature feature = new PolymorphicAuthDynamicFeature<>(
                ImmutableMap.of(PrincipalImpl.class, basicFilter, User.class, jwtFilter));
        final AbstractBinder binder = new PolymorphicAuthValueFactoryProvider.Binder<>(
                ImmutableSet.of(PrincipalImpl.class, User.class));

        env.jersey().register(feature);
        env.jersey().register(binder);
        env.jersey().register(RolesAllowedDynamicFeature.class);
    }
}
