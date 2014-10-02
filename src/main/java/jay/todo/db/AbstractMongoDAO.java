package jay.todo.db;

import jay.todo.core.Item;

import org.mongojack.JacksonDBCollection;

public class AbstractMongoDAO {

	protected JacksonDBCollection<Item, String> collection;

	protected AbstractMongoDAO(JacksonDBCollection<Item, String> items) {
		this.collection = items;
	}

}
