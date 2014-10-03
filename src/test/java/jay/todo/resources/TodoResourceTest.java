package jay.todo.resources;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.dropwizard.testing.junit.ResourceTestRule;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jay.todo.core.Item;
import jay.todo.service.TodoService;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;

public class TodoResourceTest {

	private static TodoService todoService = mock(TodoService.class);

	@ClassRule
	public static final ResourceTestRule RULE = ResourceTestRule.builder()
			.addResource(new TodoResource(todoService)).build();
	private Item item;
	private Item itemForSave;

	@Before
	public void setup() {
		item = new Item();
		item.set_id("123");
		item.setTitle("title");
		item.setBody("body");

		itemForSave = new Item();
		itemForSave.setTitle("save-test");
		itemForSave.setBody("save-test");
	}

	@After
	public void tearDown() {
		reset(todoService);
	}

	@Test
	public void getItemSuccess() {
		when(todoService.get("123")).thenReturn(item);

		Item item = RULE.client().resource("/todo/123").get(Item.class);

		verify(todoService).get("123");
		assertThat(item.get_id()).isEqualTo(item.get_id());
	}

	@Test
	public void getItemNotFound() {
		when(todoService.get("456")).thenReturn(null);
		ClientResponse response = RULE.client().resource("/todo/456")
				.get(ClientResponse.class);

		verify(todoService).get("456");
		assertThat(response.getStatus() == Response.Status.NOT_FOUND
				.getStatusCode());
	}

	@Test
	public void saveItemSuccess() {
		when(todoService.save(itemForSave)).thenReturn("789");
		ClientResponse response = RULE.client().resource("/todo")
				.type(MediaType.APPLICATION_JSON).entity(itemForSave)
				.post(ClientResponse.class);
		verify(todoService).save(itemForSave);

		assertThat(response.getStatus() == (Response.Status.CREATED)
				.getStatusCode());
		assertThat(response.getEntity(String.class).equalsIgnoreCase("789"));


	}

}
