package cat.urv.deim.asm.p2.common;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cat.urv.deim.asm.R;

import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.Calendar.*;
import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.Articles.*;
import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.News.*;
import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.*;

public class ExtraActivity extends AppCompatActivity {
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);
        queue = Volley.newRequestQueue(this);
        ImageButton arrowBack = findViewById(R.id.back_button);
        Button newsButton = (Button) findViewById(R.id.news_button);
        Button articlesButton = (Button) findViewById(R.id.articles_button);
        Button calendarButton = (Button) findViewById(R.id.calendar_button);
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

        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtain news from Database
                try {
                    Cursor cursor = getContentResolver().query(CONTENT_URI_NEWS, null, null, null, null);
                    int contentColumnIndex = cursor.getColumnIndex(NEWS_CONTENT);
                    cursor.moveToNext();
                    String actualContent = cursor.getString(contentColumnIndex);
                    Log.i("INFO DATABASE NEWS ", "{" + actualContent + "}");
                }catch (CursorIndexOutOfBoundsException e){
                    Log.i("INFO DATABASE NEWS", "There's nothing in the news table.");
                }

                //Obtain news from api
                StringRequest stringRequest = new StringRequest(
                        Request.Method.GET,
                        url+PATH_NEWS,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                getContentResolver().delete(CONTENT_URI_NEWS, null, null);
                                insertInfoIntoDB(CONTENT_URI_NEWS, NEWS_CONTENT, PATH_NEWS, response);

                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    Log.i("NEWS RESPONSE: ", jsonObject.getString(PATH_NEWS));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Your NEWS response: ", "ERROR");
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

        articlesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtain articles from Database
                try {
                    Cursor cursor = getContentResolver().query(CONTENT_URI_ARTICLES, null, null, null, null);
                    int contentColumnIndex = cursor.getColumnIndex(ARTICLES_CONTENT);
                    cursor.moveToNext();
                    String actualContent = cursor.getString(contentColumnIndex);
                    Log.i("INFO DATABASE ARTICLES ", "{" + actualContent + "}");
                }catch (CursorIndexOutOfBoundsException e){
                    Log.i("INFO DATABASE ARTICLES", "There's nothing in the articles table.");
                }

                //Obtain articles from api
                StringRequest stringRequest = new StringRequest(
                        Request.Method.GET,     // Get list of events
                        url+PATH_ARTICLES,                    // Url defined in parameters
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                getContentResolver().delete(CONTENT_URI_ARTICLES, null, null);
                                insertInfoIntoDB(CONTENT_URI_ARTICLES, ARTICLES_CONTENT, PATH_ARTICLES, response);

                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    Log.i("ARTICLES RESPONSE: ", jsonObject.getString(PATH_ARTICLES));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Your ARTICLES response: ", "ERROR");
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

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtain calendar from Database
                try {
                    Cursor cursor = getContentResolver().query(CONTENT_URI_CALENDAR, null, null, null, null);
                    int contentColumnIndex = cursor.getColumnIndex(CALENDAR_CONTENT);
                    cursor.moveToNext();
                    String actualContent = cursor.getString(contentColumnIndex);
                    Log.i("INFO DATABASE CALENDAR ", "{" + actualContent + "}");
                }catch (CursorIndexOutOfBoundsException e){
                    Log.i("INFO DATABASE CALENDAR", "There's nothing in the calendar table.");
                }

                //Obtain calendar from api
                StringRequest stringRequest = new StringRequest(
                        Request.Method.GET,     // Get list of events
                        url+PATH_CALENDAR,                    // Url defined in parameters
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                getContentResolver().delete(CONTENT_URI_CALENDAR, null, null);
                                insertInfoIntoDB(CONTENT_URI_CALENDAR, CALENDAR_CONTENT, PATH_CALENDAR, response);

                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    Log.i("CALENDAR RESPONSE: ", jsonObject.getString(PATH_CALENDAR));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Your CALENDAR response: ", "ERROR");
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

    public void insertInfoIntoDB(Uri uri, String content, String path, String response){
        ContentValues values = new ContentValues();
        try {
            JSONObject jsonObject = new JSONObject(response);
            values.put(content, jsonObject.getString(path));
            getContentResolver().insert(uri, values);
        }catch (JSONException e){}
    }
}
