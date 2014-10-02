package jay.todo.resources;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jay.todo.core.Item;
import jay.todo.service.TodoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;

@Path("/todo")
@Produces(MediaType.APPLICATION_JSON)

public class TodoResource {
	
	final static Logger logger = LoggerFactory.getLogger(TodoResource.class);

	private TodoService todoService;

	public TodoResource(TodoService todoService) {
		this.todoService = todoService;
	}

	@GET
	@Timed
	@Path("/{id}")
	public Response get(@PathParam("id") String id) {
		Item item = todoService.get(id);
		return Response.ok(item).build();
	}

	@DELETE
	@Timed
	@Path("/{id}")
	public Response delete(@PathParam("id") String id) {
		if(id  == null || id.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		boolean deleted = todoService.delete(id);
		return Response.status(Response.Status.OK).entity(deleted).build();
	}

	@POST
	@Timed
	/*
	 * save Persists an entity. Will assign an identifier if one doesn't exist.
	 * 
	 * @param item
	 * 
	 * @return Response with 200 ok and Id of entity created
	 */
	public Response save(Item item) {
		String id = todoService.save(item);
		if(id!=null || !id.isEmpty()) {
			return Response.status(Response.Status.CREATED).entity(id).build();
		} else {
			return Response.noContent().build();
		}
	}

	@PUT
	@Timed
	@Path("/{id}")
	/*
	 * update Attempts to persist the entity using an existing identifier.
	 * 
	 * @param item
	 * 
	 * @return Response with 200 ok and Id of entity updated
	 */
	public Response update(@PathParam("id") String id, Item item) {
		boolean done = todoService.update(id, item);
		return Response.status(Response.Status.OK).entity(id).build();
	}

	@POST
	@Timed
	@Path("/{id}/{done}")
	public Response mark(
			@PathParam("id") String id,
			@PathParam("done") Boolean done,
			@QueryParam("smsTo") String smsTo) {
		boolean result = todoService.mark(id, done, smsTo);
		return Response.status(Response.Status.OK).entity(result).build();
	}

	@GET
	@Timed
	@Path("/search/{query}")
	public Response search(@PathParam("query") String query) {
		List<Item> items = todoService.search(query);
		return Response.status(Response.Status.OK).entity(items).build();
	}
}
