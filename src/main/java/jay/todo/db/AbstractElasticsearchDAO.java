package jay.todo.db;

import org.elasticsearch.client.Client;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AbstractElasticsearchDAO {

	protected Client esClient;
    protected ObjectMapper mapper = new ObjectMapper();

	protected AbstractElasticsearchDAO(Client esClient) {
		this.esClient = esClient;
	}

}
