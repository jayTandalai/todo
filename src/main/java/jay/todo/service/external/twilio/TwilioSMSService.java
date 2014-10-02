package jay.todo.service.external.twilio;

import java.util.ArrayList;
import java.util.List;

import jay.todo.db.ItemMongoDAOImpl;
import jay.todo.service.external.SMSService;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

public class TwilioSMSService implements SMSService {
	
	final static Logger logger = LoggerFactory.getLogger(TwilioSMSService.class);
	TwilioRestClient client;
	String number;

	public TwilioSMSService(TwilioConfiguration twilioConfiguration) {
		this.client = new TwilioRestClient(
				twilioConfiguration.getAccountSid(), twilioConfiguration.getAuthToken());
		this.number = twilioConfiguration.getNumber();
	}

	@Override
	public String send(String to, String body) {
		// Build a filter for the SmsList
		logger.debug("SMS details:"+ number+":"+to+":"+body);
		Message message = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Body", body));
		params.add(new BasicNameValuePair("To", to));
		params.add(new BasicNameValuePair("From", number));

		MessageFactory messageFactory = client.getAccount().getMessageFactory();
	    try {
			message = messageFactory.create(params);
			logger.debug("SMS sent:"+ message.getStatus());
		} catch (TwilioRestException e) {
			logger.debug("Exception in sending Twilio sms:", e);
			return null;
		}
		return message.getSid();
	}
}
