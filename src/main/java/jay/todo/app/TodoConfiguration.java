package jay.todo.app;

import io.dropwizard.Configuration;
import io.dropwizard.elasticsearch.config.EsConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import jay.todo.service.external.twilio.TwilioConfiguration;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TodoConfiguration extends Configuration {
	@NotEmpty
	public String mongoHost = "localhost";

	@Min(1)
	@Max(65535)
	public int mongoPort = 27017;

	@NotEmpty
	public String mongoDB = "mydb";

	@Valid
	@NotNull
	public EsConfiguration esConfiguration;

	@Valid
	@NotNull
	@JsonProperty
	private TwilioConfiguration twilioConfiguration;

	@JsonProperty
	public String getMongoHost() {
		return mongoHost;
	}

	@JsonProperty
	public int getMongoPort() {
		return mongoPort;
	}

	@JsonProperty
	public String getMongoDB() {
		return mongoDB;
	}

	@JsonProperty
	public TwilioConfiguration getTwilioConfiguration() {
		return twilioConfiguration;
	}

	@JsonProperty
	public EsConfiguration getEsConfiguration() {
		return esConfiguration;
	}
}
