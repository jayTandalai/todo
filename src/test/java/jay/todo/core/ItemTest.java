package jay.todo.core;

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.fest.assertions.api.Assertions.assertThat;
import io.dropwizard.jackson.Jackson;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ItemTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        final Item item = new Item("123","title","body",false);
        assertThat(MAPPER.writeValueAsString(item))
                .isEqualTo(fixture("fixtures/item.json"));
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final Item item = new Item("123","title","body",false);
        assertThat(MAPPER.readValue(fixture("fixtures/item.json"), Item.class))
                .isEqualTo(item);
    }
}