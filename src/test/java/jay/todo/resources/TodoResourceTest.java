package jay.todo.resources;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.dropwizard.testing.junit.ResourceTestRule;

import javax.ws.rs.core.Response;

import jay.todo.core.Item;
import jay.todo.service.TodoService;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

public class TodoResourceTest {

	private static TodoService todoService = mock(TodoService.class);

    @ClassRule
    public static final ResourceTestRule RULE = ResourceTestRule.builder()
            .addResource(new TodoResource(todoService))
            .build();
    private Item item;

    @Before
    public void setup() {
        item = new Item();
        item.set_id("123");
    }

    @After
    public void tearDown() {
        reset(todoService);
    }

    @Test
    public void getItemSuccess() {
        when(todoService.get("123")).thenReturn(item);

        Response response  = RULE.client().resource("/todo/123").get(Response.class);

        assertThat(((Item)response.getEntity()).get_id()).isEqualTo(item.get_id());
        verify(todoService).get("123");
    }

    @Test
    public void getItemNotFound() {
        when(todoService.get("456")).thenReturn(null);
        final Response response = RULE.client().resource("/todo/456").get(Response.class);

        assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(todoService).get("456");
    }

}
