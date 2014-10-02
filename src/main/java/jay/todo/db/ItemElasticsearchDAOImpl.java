package jay.todo.db;

import java.io.IOException;
import java.util.List;

import jay.todo.core.Item;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class ItemElasticsearchDAOImpl extends AbstractElasticsearchDAO implements ItemDAO {
	
	final static Logger logger = LoggerFactory.getLogger(ItemElasticsearchDAOImpl.class);

	public ItemElasticsearchDAOImpl(Client esClient) {
		super(esClient);
	}

	@Override
	public Item findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String create(Item item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(String id, Item item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Item> search(String query) {
		logger.debug("query:"+query);
		logger.debug("es query:"+ 
				esClient.prepareSearch("mongo_river")
		        .setTypes("item")
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(
		        		QueryBuilders.multiMatchQuery(query, "title^2", "body")
		        		.type(MultiMatchQueryBuilder.Type.MOST_FIELDS)
		        		.operator(Operator.AND))  
		        .setSize(60).setExplain(true)
		        .toString()	
			);
		SearchResponse response = esClient.prepareSearch("mongo_river")
		        .setTypes("item")
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(
		        		QueryBuilders.multiMatchQuery(query, "title^2", "body")
		        		.type(MultiMatchQueryBuilder.Type.MOST_FIELDS)
		        		.operator(Operator.AND))
		        .setSize(60).setExplain(true)
		        .execute()
		        .actionGet();
		return toItems(response);
	}

    private List<Item> toItems(SearchResponse searchResponse) {
        List<Item> items = Lists.newArrayList();

        for (SearchHit searchHit : searchResponse.getHits().hits()) {
            try {
                Item item = mapper.readValue(searchHit.source(), Item.class);
                items.add(item);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return items;
    }

	@Override
	public boolean mark(Item item, boolean done) {
		// TODO Auto-generated method stub
		return false;
	}
}
