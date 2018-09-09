package http;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class HttpUtilsImpl implements HttpUtils {

    public HttpPost createPostRequest(JSONObject requestBody, String path) throws UnsupportedEncodingException {
        HttpPost post = new HttpPost(path);

        post.setEntity(new StringEntity(requestBody.toString()));

        return post;
    }

    public HttpGet createGetRequest(String path) {
        return new HttpGet(path);
    }

    public InputStream executeRequest(HttpUriRequest request) throws IOException {
        HttpClient client = HttpClients.createDefault();

        HttpResponse execute = client.execute(request);

        return execute.getEntity().getContent();
    }
}
