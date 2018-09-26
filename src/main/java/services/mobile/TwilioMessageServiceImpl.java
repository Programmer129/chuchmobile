package services.mobile;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import http.HttpUtils;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.ACCOUNT_SID;
import static utils.Constants.AUTH_TOKEN;
import static utils.Constants.CHUCK_API;
import static utils.Constants.FROM;
import static utils.Utilities.parseInputStream;

public class TwilioMessageServiceImpl implements MessageService {
    private HttpUtils httpUtils;

    public TwilioMessageServiceImpl(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
    }

    @Override
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

    @Override
    public String getJoke() throws IOException {
        HttpGet request = httpUtils.createGetRequest(CHUCK_API);

        InputStream stream = httpUtils.executeRequest(request);

        StringBuilder parsed = parseInputStream(stream);

        return new JSONObject(parsed.toString()).getString("value");
    }
}
