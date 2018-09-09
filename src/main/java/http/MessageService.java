package http;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MessageService {

    private static final String CHUCK_API = "https://api.chucknorris.io/jokes/random";
    private static final String ACCOUNT_SID = "AC0714a5abec2a4918c7a7c4c637309ea1";
    private static final String AUTH_TOKEN = "2ba39c6df5a9c7f4850d3e592e0f2ed3";
    private static final String FROM = "+18654193208";

    private HttpUtils httpUtils;

    public MessageService(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
    }

    private StringBuilder parse(InputStream stream) throws IOException {
        int c;
        StringBuilder stringBuilder = new StringBuilder();
        while((c=stream.read()) != -1) {
            stringBuilder.append((char)c);
        }

        return stringBuilder;
    }

    private String getJoke() throws IOException {
        HttpGet request = httpUtils.createGetRequest(CHUCK_API);

        InputStream stream = httpUtils.executeRequest(request);

        StringBuilder parsed = parse(stream);

        return new JSONObject(parsed.toString()).getString("value");
    }

    public String [] sendMessage(String [] to) throws IOException {
        String [] sids = new String[to.length];

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        PhoneNumber from = new PhoneNumber(FROM);

        PhoneNumber [] receivers = new PhoneNumber[to.length];

        for (int i = 0; i < to.length; i++) {
            receivers[i] = new PhoneNumber(to[i]);
            Message message = Message.creator(receivers[i], from, getJoke()).create();
            sids[i] = message.getSid();
        }

        return sids;
    }
}
