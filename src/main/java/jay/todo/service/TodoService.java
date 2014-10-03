package jay.todo.service;

import java.util.ArrayList;
import java.util.List;

import jay.todo.core.Item;
import jay.todo.db.ItemDAO;
import jay.todo.service.external.SMSService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TodoService {
	
	final static Logger logger = LoggerFactory.getLogger(TodoService.class);

    private final ItemDAO itemDAO;
    private final ItemDAO itemElasticsearchDAO;
    private final SMSService smsService;
    private static final String DEFAULT_SUFFIX = " task has been marked as done";

    public TodoService(ItemDAO itemDAO, ItemDAO itemElasticsearchDAO, SMSService smsService) {
        this.itemDAO = itemDAO;
        this.itemElasticsearchDAO = itemElasticsearchDAO;
        this.smsService = smsService;
    }
    
    public Item get(String id) {
    	Item item = itemDAO.findById(id);
        return item;
    }
    
    public boolean delete(String id) {
    	boolean deleted = itemDAO.delete(id);
        return deleted;
    }
    public String save(Item item) {
    	return itemDAO.create(item);
    }

    public boolean update(String id, Item item) {
        return itemDAO.update(id, item);
    }

    public boolean mark(String id, Boolean done, String smsTo) {
    	Item item = itemDAO.findById(id);
    	boolean wasDone = item.isDone();
		boolean marked = itemDAO.mark(item, done);
    	if(done) {
    		if(item!=null && !wasDone) {
    			// send sms via twilio
    			String sid = smsService.send(
    					smsTo, 
    					item.getTitle()+DEFAULT_SUFFIX);
    			if(sid != null || !sid.isEmpty()) {
    				logger.debug("twilio sms sent - sid:"+sid);;
    			} else {
    				logger.debug("could not send twilio sms");
    			}
    		}
    	}
        return marked;
    }
    
    public List<Item> search(String query) {
    	List<Item> items = null;
    	if (query != null && !query.isEmpty()) {
    		items = itemElasticsearchDAO.search(query);
    		return items;
		} else {
			return new ArrayList<Item>();
		}
    }

}
