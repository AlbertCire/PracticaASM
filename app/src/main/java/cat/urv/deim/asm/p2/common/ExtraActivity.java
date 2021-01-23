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
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cat.urv.deim.asm.R;
import cat.urv.deim.asm.models.Article;
import cat.urv.deim.asm.models.Calendar;
import cat.urv.deim.asm.models.New;
import cat.urv.deim.asm.models.Tag;

import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.Calendar.*;
import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.Articles.*;
import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.News.*;
import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.*;

public class ExtraActivity extends AppCompatActivity {
    RequestQueue queue;

    ArrayList<New> newsList = new ArrayList<>();
    ArrayList<Article> articlesList = new ArrayList<>();
    ArrayList<Calendar> calendarList = new ArrayList<>();

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
                    obtainNewsInfoFromDB();
                    Log.i("INFO DATABASE NEWS ", "{" + newsList + "}");
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
                                JSONObject jsonObject = null;
                                String newsString = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    newsString = jsonObject.getString("news");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                New[] news = null;
                                if (!response.equals(null)){
                                    Gson gson = new Gson();
                                    news = gson.fromJson(newsString, New[].class);
                                }
                                newsList.clear();
                                int i = 1;
                                for(New item : news) {
                                    item.setId(i);
                                    newsList.add(item);
                                    i++;
                                }

                                //Show news from API
                                Log.i("INFO API NEWS ", "{" + newsList + "}");

                                //Update news table in DB
                                getContentResolver().delete(CONTENT_URI_NEWS, null, null);
                                insertNewsIntoDB();
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
                    obtainArticlesInfoFromDB();
                    Log.i("INFO DATABASE ARTICLES ", "{" + articlesList + "}");
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
                                JSONObject jsonObject = null;
                                String articlesString = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    articlesString = jsonObject.getString("articles");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Article[] articles = null;
                                if(!response.equals(null)){
                                    Gson gson = new Gson();
                                    articles = gson.fromJson(articlesString, Article[].class);
                                }
                                articlesList.clear();
                                int i = 1;
                                for(Article item : articles) {
                                    item.setId(i);
                                    articlesList.add(item);
                                    i++;
                                }

                                //Show articles from API
                                Log.i("INFO API ARTICLES ", "{" + articlesList + "}");

                                //Update articles table in DB
                                getContentResolver().delete(CONTENT_URI_ARTICLES, null, null);
                                insertArticlesIntoDB();
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
                    obtainCalendarInfoFromDB();
                    Log.i("INFO DATABASE CALENDAR ", "{" + calendarList + "}");
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
                                JSONObject jsonObject = null;
                                String calendarString = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    calendarString = jsonObject.getString("calendar");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Calendar[] calendar = null;
                                if(!response.equals(null)){
                                    Gson gson = new Gson();
                                    calendar = gson.fromJson(calendarString, Calendar[].class);
                                }
                                calendarList.clear();
                                int i = 1;
                                for(Calendar item : calendar) {
                                    item.setId(i);
                                    calendarList.add(item);
                                    i++;
                                }

                                //Show calendar from API
                                Log.i("INFO API CALENDAR ", "{" + calendarList + "}");

                                //Update calendar table in DB
                                getContentResolver().delete(CONTENT_URI_CALENDAR, null, null);
                                insertCalendarIntoDB();
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


    public void obtainNewsInfoFromDB() {
        Cursor cursor = getContentResolver().query(CONTENT_URI_NEWS, null, null, null, null);

        int idColumnIndex = cursor.getColumnIndex(NEWS_ID);
        int dateColumnIndex = cursor.getColumnIndex(NEWS_DATE);
        int dateUpdateColumnIndex = cursor.getColumnIndex(NEWS_DATEUPDATE);
        int imageURLColumnIndex = cursor.getColumnIndex(NEWS_IMAGEURL);
        int subtitleColumnIndex = cursor.getColumnIndex(NEWS_SUBTITLE);
        int tagsColumnIndex = cursor.getColumnIndex(NEWS_TAGS);
        int textColumnIndex = cursor.getColumnIndex(NEWS_TEXT);
        int titleColumnIndex = cursor.getColumnIndex(NEWS_TITLE);

        while (cursor.moveToNext()){
            int actualID = cursor.getInt(idColumnIndex);
            String actualDate = cursor.getString(dateColumnIndex);
            String actualDateUpdate = cursor.getString(dateUpdateColumnIndex);
            String actualImageURL = cursor.getString(imageURLColumnIndex);
            String actualSubtitle = cursor.getString(subtitleColumnIndex);
            String actualText = cursor.getString(textColumnIndex);
            String actualTitle = cursor.getString(titleColumnIndex);
            String auxTags = cursor.getString(tagsColumnIndex);
            Gson gson = new Gson();
            Tag[] actualTags = gson.fromJson(auxTags, Tag[].class);

            newsList.add(new New(actualID, actualDate, actualDateUpdate, actualImageURL, actualSubtitle, actualTags, actualText, actualTitle));
        }
        cursor.close();
    }

    public void insertNewsIntoDB(){
        ContentValues values = new ContentValues();
        for (int i = 0; i< newsList.size(); i++){
            values.put(NEWS_ID, newsList.get(i).getId());
            values.put(NEWS_DATE, newsList.get(i).getDate());
            values.put(NEWS_DATEUPDATE, newsList.get(i).getDateUpdate());
            values.put(NEWS_IMAGEURL, newsList.get(i).getImageURL());
            values.put(NEWS_SUBTITLE, newsList.get(i).getSubtitle());
            Gson gson = new Gson();
            values.put(NEWS_TAGS, gson.toJson(newsList.get(i).getTags()));
            values.put(NEWS_TEXT, newsList.get(i).getText());
            values.put(NEWS_TITLE, newsList.get(i).getTitle());

            getContentResolver().insert(CONTENT_URI_NEWS, values);
        }
    }

    public void obtainArticlesInfoFromDB() {
        Cursor cursor = getContentResolver().query(CONTENT_URI_ARTICLES, null, null, null, null);

        int idColumnIndex = cursor.getColumnIndex(ARTICLES_ID);
        int abstractTextColumnIndex = cursor.getColumnIndex(ARTICLES_ABSTRACTTEXT);
        int authorColumnIndex = cursor.getColumnIndex(ARTICLES_AUTHOR);
        int dateColumnIndex = cursor.getColumnIndex(ARTICLES_DATE);
        int dateUpdateColumnIndex = cursor.getColumnIndex(ARTICLES_DATEUPDATE);
        int descriptionColumnIndex = cursor.getColumnIndex(ARTICLES_DESCRIPTION);
        int imageURLColumnIndex = cursor.getColumnIndex(ARTICLES_IMAGEURL);
        int tagsColumnIndex = cursor.getColumnIndex(ARTICLES_TAGS);
        int textColumnIndex = cursor.getColumnIndex(ARTICLES_TEXT);
        int titleColumnIndex = cursor.getColumnIndex(ARTICLES_TITLE);

        while (cursor.moveToNext()){
            int actualID = cursor.getInt(idColumnIndex);
            String actualAbstractText = cursor.getString(abstractTextColumnIndex);
            String actualAuthor = cursor.getString(authorColumnIndex);
            String actualDate = cursor.getString(dateColumnIndex);
            String actualDateUpdate = cursor.getString(dateUpdateColumnIndex);
            String actualDescription = cursor.getString(descriptionColumnIndex);
            String actualImageURL = cursor.getString(imageURLColumnIndex);
            String actualText = cursor.getString(textColumnIndex);
            String actualTitle = cursor.getString(titleColumnIndex);
            String auxTags = cursor.getString(tagsColumnIndex);
            Gson gson = new Gson();
            Tag[] actualTags = gson.fromJson(auxTags, Tag[].class);

            articlesList.add(new Article(actualID, actualAbstractText, actualAuthor, actualDate, actualDateUpdate, actualDescription, actualImageURL, actualTags, actualText, actualTitle));
        }
        cursor.close();
    }

    public void insertArticlesIntoDB(){
        ContentValues values = new ContentValues();
        for (int i = 0; i< articlesList.size(); i++){
            values.put(ARTICLES_ID, articlesList.get(i).getId());
            values.put(ARTICLES_ABSTRACTTEXT, articlesList.get(i).getAbstractText());
            values.put(ARTICLES_AUTHOR, articlesList.get(i).getAuthor());
            values.put(ARTICLES_DATE, articlesList.get(i).getDate());
            values.put(ARTICLES_DATEUPDATE, articlesList.get(i).getDateUpdate());
            values.put(ARTICLES_DESCRIPTION, articlesList.get(i).getDescription());
            values.put(ARTICLES_IMAGEURL, articlesList.get(i).getImageURL());
            Gson gson = new Gson();
            values.put(ARTICLES_TAGS, gson.toJson(articlesList.get(i).getTags()));
            values.put(ARTICLES_TEXT, articlesList.get(i).getText());
            values.put(ARTICLES_TITLE, articlesList.get(i).getTitle());

            getContentResolver().insert(CONTENT_URI_ARTICLES, values);
        }
    }

    public void obtainCalendarInfoFromDB() {
        Cursor cursor = getContentResolver().query(CONTENT_URI_CALENDAR, null, null, null, null);

        int idColumnIndex = cursor.getColumnIndex(CALENDAR_ID);
        int dateColumnIndex = cursor.getColumnIndex(CALENDAR_DATE);
        int descriptionColumnIndex = cursor.getColumnIndex(CALENDAR_DESCRIPTION);
        int hourColumnIndex = cursor.getColumnIndex(CALENDAR_HOUR);
        int imageURLColumnIndex = cursor.getColumnIndex(CALENDAR_IMAGEURL);
        int nameColumnIndex = cursor.getColumnIndex(CALENDAR_NAME);
        int tagsColumnIndex = cursor.getColumnIndex(CALENDAR_TAGS);
        int venueColumnIndex = cursor.getColumnIndex(CALENDAR_VENUE);

        while (cursor.moveToNext()){
            int actualID = cursor.getInt(idColumnIndex);
            String actualDate = cursor.getString(dateColumnIndex);
            String actualDescription = cursor.getString(descriptionColumnIndex);
            String actualHour = cursor.getString(hourColumnIndex);
            String actualImageURL = cursor.getString(imageURLColumnIndex);
            String actualName = cursor.getString(nameColumnIndex);
            String actualVenue = cursor.getString(venueColumnIndex);
            String auxTags = cursor.getString(tagsColumnIndex);
            Gson gson = new Gson();
            Tag[] actualTags = gson.fromJson(auxTags, Tag[].class);

            calendarList.add(new Calendar(actualID, actualDate, actualDescription, actualHour, actualImageURL, actualName, actualTags, actualVenue));
        }
        cursor.close();
    }

    public void insertCalendarIntoDB(){
        ContentValues values = new ContentValues();
        for (int i = 0; i< calendarList.size(); i++){
            values.put(CALENDAR_ID, calendarList.get(i).getId());
            values.put(CALENDAR_DATE, calendarList.get(i).getDate());
            values.put(CALENDAR_DESCRIPTION, calendarList.get(i).getDescription());
            values.put(CALENDAR_HOUR, calendarList.get(i).getHour());
            values.put(CALENDAR_IMAGEURL, calendarList.get(i).getImageURL());
            values.put(CALENDAR_NAME, calendarList.get(i).getName());
            Gson gson = new Gson();
            values.put(CALENDAR_TAGS, gson.toJson(calendarList.get(i).getTags()));
            values.put(CALENDAR_VENUE, calendarList.get(i).getVenue());

            getContentResolver().insert(CONTENT_URI_CALENDAR, values);
        }
    }
}
