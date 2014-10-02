package jay.todo.resources;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.dropwizard.testing.junit.ResourceTestRule;
import jay.todo.core.Item;
import jay.todo.db.ItemDAO;
import jay.todo.db.ItemMongoDAOImpl;
import jay.todo.service.TodoService;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

public class TodoResourceTest {

}
