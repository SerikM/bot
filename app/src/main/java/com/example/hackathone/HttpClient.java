package com.example.hackathone;
import com.loopj.android.http.*;

public class HttpClient {

    private static final String BASE_URL = "https://hackathoneqna.azurewebsites.net/qnamaker/knowledgebases/9753d6b0-b3b6-4aff-8a12-4cc01a8ed99b/generateAnswer";
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static String authKey = "e339230f-2d59-4172-ac34-7c77831174e7";

    public static void get(RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.addHeader("Authorization", authKey);
        client.addHeader("Content-Type", "application/json");
        client.get(BASE_URL, params, responseHandler);
    }

    public static void post(RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.addHeader("Authorization", authKey);
        client.addHeader("Content-Type", "application/json");
        client.post(BASE_URL, params, responseHandler);
    }
}