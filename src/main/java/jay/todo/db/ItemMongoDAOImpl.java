package jay.todo.db;

import java.util.List;

import jay.todo.core.Item;

import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemMongoDAOImpl extends AbstractMongoDAO implements ItemDAO {
	
	final static Logger logger = LoggerFactory.getLogger(ItemMongoDAOImpl.class);
	
	public ItemMongoDAOImpl(JacksonDBCollection<Item, String> items) {
        super(items);
	}

	@Override
	public Item findById(String id) {
		logger.debug("id:"+id);
		Item item = collection.findOneById(id);
		return item;

	}

	@Override
	public String create(Item item) {
		WriteResult<Item, String> result = collection.insert(item);
		return result.getSavedId();
	}

	@Override
	public boolean update(String id, Item item) {
		logger.info("id:"+id);
		collection.updateById(id, item);
		return true;
	}

	@Override
	public boolean delete(String id) {
		collection.removeById(id);
		return true;
	}

	@Override
	public boolean mark(Item item, boolean done) {
		item.setDone(done);
		collection.updateById(item.get_id(), item);
		return true;
	}

	@Override
	public List<Item> search(String query) {
		// TODO Auto-generated method stub
		return null;
	}
}
