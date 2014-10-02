package jay.todo.service.external.twilio;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TwilioConfiguration {

    @JsonProperty
    @NotEmpty
    private String accountSid;

    @JsonProperty
    @NotEmpty
    private String authToken;

    @JsonProperty
    @NotEmpty
    private String number;

	public String getAccountSid() {
		return accountSid;
	}

	public String getAuthToken() {
		return authToken;
	}

	public String getNumber() {
		return number;
	}
}
