package com.example.hackathone;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void submitHandler(View view)
    {
        final ProgressBar progressBar = findViewById(R.id.progressBar1);
        final TextView answerTextView = findViewById(R.id.answerTextView);
        final EditText questionEditText = findViewById(R.id.questionEditText);
        final String question = questionEditText.getText().toString();

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(questionEditText.getWindowToken(), 0);

        answerTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        RequestParams params = new RequestParams();
        params.add("question", question);
        params.setUseJsonStreamer(true);
        HttpClient client = new HttpClient();
        client.post(params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try
                {
                      String answerStr = getAnswer(response);
                      sleep(2000);
                      answerTextView.setVisibility(View.VISIBLE);
                      answerTextView.setText(Html.fromHtml(answerStr));
                      progressBar.setVisibility(View.GONE);

                }
                catch(Exception e){}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject response) {
                answerTextView.setText("Request failed");
            }

        });
    }

    private String getAnswer(JSONObject response) {
       int displayLimit = 200;
        String answerStr = "";
        try
       {
           JSONArray array = response.getJSONArray("answers");
           JSONObject answerObj = array.getJSONObject(0);
           answerStr = answerObj.getString("answer");
           if (answerStr.length() > displayLimit)
           {
               answerStr = answerStr.substring(0, displayLimit);
           }

           return answerStr;
       }
       catch(Exception e){}
       return answerStr;
    }
}
