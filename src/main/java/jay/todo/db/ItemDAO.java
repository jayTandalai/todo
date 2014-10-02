package jay.todo.db;

import java.util.List;

import jay.todo.core.Item;

public interface ItemDAO {
	
	public Item findById(String id) ;

	public String create(Item item);

	public boolean update(String id, Item item);

	public boolean delete(String id);

	public boolean mark(Item item, boolean done); 
	
	public List<Item> search(String query);
}
