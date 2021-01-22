package cat.urv.deim.asm.p3.shared;

import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.json.*;

import cat.urv.deim.asm.R;
import cat.urv.deim.asm.models.Event;
import cat.urv.deim.asm.models.Tag;
import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.Events.*;

public class EventsFragment extends Fragment {

    //Arrays of the content of the events.
    RecyclerView recyclerEvents;
    ArrayList<String> titlesList;
    ArrayList<String> descriptionsList;
    ArrayList<String> urlImagesList;
    ArrayList<Tag[]> tagList;
    ArrayList<String> webURLList;
    ArrayList<String> typeList;
    //Credentials
    String url;
    String mail;
    String username;
    String accessToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        // Obtaining credentials
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.credentials);
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.beginObject();
            jsonReader.nextName(); url = jsonReader.nextString() + "events";
            jsonReader.nextName(); mail = jsonReader.nextString();
            jsonReader.nextName(); username = jsonReader.nextString();
            jsonReader.nextName(); accessToken = jsonReader.nextString();
            jsonReader.endObject();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            Log.e("Error: ", e.getMessage());
        }

        recyclerEvents = view.findViewById(R.id.recyclerId);
        recyclerEvents.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        titlesList = new ArrayList<>();
        descriptionsList = new ArrayList<>();
        urlImagesList = new ArrayList<>();
        tagList = new ArrayList<>();
        webURLList = new ArrayList<>();
        typeList = new ArrayList<>();

        // Obtaining content of the events from DB
        obtainEventsInfoFromDB();
        EventsListAdapter adapter = new EventsListAdapter(titlesList, descriptionsList, urlImagesList, tagList, typeList, webURLList);
        recyclerEvents.setAdapter(adapter);

        titlesList = new ArrayList<>();
        descriptionsList = new ArrayList<>();
        urlImagesList = new ArrayList<>();
        tagList = new ArrayList<>();
        webURLList = new ArrayList<>();
        typeList = new ArrayList<>();

        //Obtaining events from api
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,     // Get list of events
                url,                    // Url defined in parameters
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("EVENTS RESPONSE: ", response);

                        String eventsString = null;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            eventsString = jsonObject.getString("events");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Event[] events;

                        try {
                            if (!response.equals(null)) {
                                Gson gson = new Gson();
                                events = gson.fromJson(eventsString, Event[].class);

                                // Show Events on screen
                                for (Event event : events) {

                                    descriptionsList.add(event.getDescription());
                                    urlImagesList.add(event.getImageURL());
                                    typeList.add(event.getType());

                                    if (event.getWebURL() != null && event.getName() != null && event.getTags() != null) {
                                        webURLList.add(event.getWebURL());
                                        titlesList.add(event.getName());
                                        tagList.add(event.getTags());
                                    } else {
                                        webURLList.add("");
                                        titlesList.add("");
                                        tagList.add(null);
                                    }
                                }

                                //Inserting the content of events obtained from the api
                                getActivity().getApplicationContext().getContentResolver().delete(CONTENT_URI_EVENTS, null, null);
                                insertEventsIntoDB();

                                //Reverse the arrays to show the info ASC
                                Collections.reverse(titlesList);
                                Collections.reverse(descriptionsList);
                                Collections.reverse(urlImagesList);
                                Collections.reverse(tagList);
                                Collections.reverse(typeList);
                                Collections.reverse(webURLList);

                                EventsListAdapter adapter =
                                        new EventsListAdapter
                                                (titlesList, descriptionsList, urlImagesList, tagList, typeList, webURLList);
                                recyclerEvents.setAdapter(adapter);

                            } else {
                                Log.e("Your array response: " + response, "Data null");
                            }
                        }catch (Exception e){}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Your EVENTS response: ", "ERROR");
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

        RequestQueue queue = Volley.newRequestQueue(this.getContext());

        queue.add(stringRequest);
        queue.start();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        }
    }

    public void obtainEventsInfoFromDB() {
        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(CONTENT_URI_EVENTS, null, null, null, null);

        int idColumnIndex = cursor.getColumnIndex(EVENTS_ID);
        int descriptionColumnIndex = cursor.getColumnIndex(EVENTS_DESCRIPTION);
        int imageURLColumnIndex = cursor.getColumnIndex(EVENTS_IMAGEURL);
        int nameColumnIndex = cursor.getColumnIndex(EVENTS_NAME);
        int typeColumnIndex = cursor.getColumnIndex(EVENTS_TYPE);
        int webURLColumnIndex = cursor.getColumnIndex(EVENTS_WEBURL);
        int tagsColumnIndex = cursor.getColumnIndex(EVENTS_TAGS);

        while (cursor.moveToNext()){
            int actualID = cursor.getInt(idColumnIndex);
            String actualDescription = cursor.getString(descriptionColumnIndex);
            String actualimageURL = cursor.getString(imageURLColumnIndex);
            String actualName = cursor.getString(nameColumnIndex);
            String actualType = cursor.getString(typeColumnIndex);
            String actualWebURL = cursor.getString(webURLColumnIndex);
            String auxTags = cursor.getString(tagsColumnIndex);
            Gson gson = new Gson();
            Tag[] actualTags = gson.fromJson(auxTags, Tag[].class);

            descriptionsList.add(actualDescription);
            urlImagesList.add(actualimageURL);
            typeList.add(actualType);
            webURLList.add(actualWebURL);
            titlesList.add(actualName);
            tagList.add(actualTags);
        }

        cursor.close();
    }

    public void insertEventsIntoDB(){
        ContentValues values = new ContentValues();
        for (int i = 0; i< descriptionsList.size(); i++){
            values.put(EVENTS_DESCRIPTION, descriptionsList.get(i));
            values.put(EVENTS_IMAGEURL, urlImagesList.get(i));
            values.put(EVENTS_NAME, titlesList.get(i));
            values.put(EVENTS_TYPE, typeList.get(i));
            values.put(EVENTS_WEBURL, webURLList.get(i));
            Gson gson = new Gson();
            values.put(EVENTS_TAGS, gson.toJson(tagList.get(i)));

            getActivity().getApplicationContext().getContentResolver().insert(CONTENT_URI_EVENTS, values);
        }
    }
}
