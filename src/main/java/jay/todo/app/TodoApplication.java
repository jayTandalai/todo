package jay.todo.app;

import io.dropwizard.Application;
import io.dropwizard.elasticsearch.health.EsClusterHealthCheck;
import io.dropwizard.elasticsearch.managed.ManagedEsClient;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.net.UnknownHostException;

import jay.todo.core.Item;
import jay.todo.db.ItemDAO;
import jay.todo.db.ItemElasticsearchDAOImpl;
import jay.todo.db.ItemMongoDAOImpl;
import jay.todo.db.MongoManaged;
import jay.todo.health.MongoHealthCheck;
import jay.todo.resources.TodoResource;
import jay.todo.service.TodoService;
import jay.todo.service.external.SMSService;
import jay.todo.service.external.twilio.TwilioSMSService;

import org.mongojack.JacksonDBCollection;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class TodoApplication extends Application<TodoConfiguration> {
	public static void main(String[] args) throws Exception {
		new TodoApplication().run(args);
	}

	@Override
	public String getName() {
		return "Todo";
	}

	@Override
	public void initialize(Bootstrap<TodoConfiguration> bootstrap) {
	}

	@Override
	public void run(TodoConfiguration configuration, Environment environment) {

		Mongo mongo = null;

		try {
			mongo = new Mongo(configuration.getMongoHost(),
					configuration.getMongoPort());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MongoManaged mongoManaged = new MongoManaged(mongo);

		MongoHealthCheck mongoHealthCheck = new MongoHealthCheck(mongo);
		environment.healthChecks().register("mongo", mongoHealthCheck);

		environment.lifecycle().manage(mongoManaged);

		DB db = mongo.getDB(configuration.getMongoDB());
		JacksonDBCollection<Item, String> items = JacksonDBCollection.wrap(
				db.getCollection("item"), Item.class, String.class);
		
		final ItemDAO itemDAO = new ItemMongoDAOImpl(items);
		// elasticsearch setup
		final ManagedEsClient managedClient = new ManagedEsClient(configuration.getEsConfiguration());
        environment.lifecycle().manage(managedClient);
        environment.healthChecks().register("ES cluster health", new EsClusterHealthCheck(managedClient.getClient()));
        

		final ItemDAO itemElasticsearchDAO = new ItemElasticsearchDAOImpl(managedClient.getClient());
		
		// for accessing twilio for sms
		final SMSService smsService = new TwilioSMSService(configuration.getTwilioConfiguration());
		 
		
		final TodoService todoService = new TodoService(
				itemDAO, itemElasticsearchDAO, smsService);
		final TodoResource todoResource = new TodoResource(todoService);
		environment.jersey().register(todoResource);
	}

}
