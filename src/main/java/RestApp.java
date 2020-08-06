import auth.AuthenticateFilter;
import dao.NameDAO;
import exceptionmapper.MessageBodyProviderNotFoundExceptionMapper;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import resources.AuthResource;
import resources.NameResource;
import resources.TelegramResource;

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

//        env.jersey().register(new AuthDynamicFeature(
//                new OAuthCredentialAuthFilter.Builder<User>()
//                        .setAuthenticator(new ExampleOAuthAuthenticator())
//                        .setAuthorizer(new ExampleAuthorizer())
//                        .setPrefix("Bearer")
//                        .buildAuthFilter()));

        env.jersey().register(TelegramResource.class);
        env.jersey().register(AuthResource.class);
        env.jersey().register(AuthenticateFilter.class);
//        env.jersey().register(MessageBodyProviderNotFoundExceptionMapper.class);
        env.jersey().register(new MessageBodyProviderNotFoundExceptionMapper());
    }
}
