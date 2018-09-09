package http;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public interface HttpUtils {

    HttpPost createPostRequest(JSONObject requestBody, String path) throws UnsupportedEncodingException;

    HttpGet createGetRequest(String path);

    InputStream executeRequest(HttpUriRequest request) throws IOException;

}
