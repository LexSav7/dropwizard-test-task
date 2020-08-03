import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import resources.NameService;

public class RestApp extends Application<RestConfig> {

    public static void main(String[] args) throws Exception {
        new RestApp().run(args);
    }

    @Override
    public void run(RestConfig restConfig, Environment env) throws Exception {
        final NameService nameService = new NameService();
        env.jersey().register(nameService);
    }
}
