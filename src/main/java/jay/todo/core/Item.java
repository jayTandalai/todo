package jay.todo.core;

import org.hibernate.validator.constraints.NotEmpty;
import org.mongojack.Id;
import org.mongojack.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    @Id
    @ObjectId
    private String _id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String body;

    @NotEmpty
    private Boolean done = false;

    public Item() {
        // Jackson deserialization
    }

    public Item(String id, String title, String body, Boolean done) {
        this._id = id;
        this.title = title;
        this.body = body;
        this.done = done;
    }
    
	@JsonProperty
    public String get_id() {
		return _id;
	}

	@JsonProperty
    public String getTitle() {
        return title;
    }

    @JsonProperty
    public String getBody() {
        return body;
    }

    @JsonProperty
    public Boolean isDone() {
        return done;
    }

	public void setTitle(String title) {
		this.title = title;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	@Override
    public boolean equals(Object o) {
 
        // If the object is compared with itself then return true  
        if (o == this) {
            return true;
        }
 
        /* Check if o is an instance of Item or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Item)) {
            return false;
        }
         
        // typecast o to Item so that we can compare data members 
        Item c = (Item) o;
         
        // Compare the data members and return accordingly 
        return _id.equals(c._id) && title.equalsIgnoreCase(c.title) 
        		&& body.equalsIgnoreCase(c.body) && done==c.done;
    }
}