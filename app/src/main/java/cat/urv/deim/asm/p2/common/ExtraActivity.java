package cat.urv.deim.asm.p2.common;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cat.urv.deim.asm.R;

public class ExtraActivity extends AppCompatActivity {
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);
        queue = Volley.newRequestQueue(this);
        ImageButton arrowBack = findViewById(R.id.back_button);
        Button news_button = (Button) findViewById(R.id.news_button);
        Button articles_button = (Button) findViewById(R.id.articles_button);
        Button calendar_button = (Button) findViewById(R.id.calendar_button);
        String auxURL = null;
        String auxMail = null;
        String auxUsername= null;
        String auxAccessToken = null;


        try {
            InputStream inputStream = getResources().openRawResource(R.raw.credentials);
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.beginObject();
            jsonReader.nextName(); auxURL = jsonReader.nextString();
            jsonReader.nextName(); auxMail = jsonReader.nextString();
            jsonReader.nextName(); auxUsername = jsonReader.nextString();
            jsonReader.nextName(); auxAccessToken = jsonReader.nextString();
            jsonReader.endObject();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            Log.e("Error: ", e.getMessage());
        }

        final String url = auxURL;
        final String mail = auxMail;
        final String username = auxUsername;
        final String accessToken = auxAccessToken;

        news_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest = new StringRequest(
                        Request.Method.GET,
                        url+"news",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("NEWS RESPONSE: ", response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Your response: ", "ERROR");
                            }
                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("mail", mail);
                        headers.put("username", username);
                        headers.put("token", accessToken);
                        return headers;
                    }
                };
                queue.add(stringRequest);
            }
        });

        articles_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(
                        Request.Method.GET,     // Get list of events
                        url+"articles",                    // Url defined in parameters
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("ARTICLES RESPONSE: ", response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Your response: ", "ERROR");
                            }
                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("mail", mail);
                        headers.put("username", username);
                        headers.put("token", accessToken);
                        return headers;
                    }
                };
                queue.add(stringRequest);
            }
        });

        calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(
                        Request.Method.GET,     // Get list of events
                        url+"calendar",                    // Url defined in parameters
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("CALENDAR RESPONSE: ", response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Your response: ", "ERROR");
                            }
                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("mail", mail);
                        headers.put("username", username);
                        headers.put("token", accessToken);
                        return headers;
                    }
                };
                queue.add(stringRequest);
            }
        });

        queue.start();

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        getApplicationContext(),
                        MainActivity.class) );
            }
        });
    }
}
