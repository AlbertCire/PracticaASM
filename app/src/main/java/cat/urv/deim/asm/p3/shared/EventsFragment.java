package cat.urv.deim.asm.p3.shared;

import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.*;

import cat.urv.deim.asm.R;

import cat.urv.deim.asm.models.Event;
import cat.urv.deim.asm.models.Tag;

import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.CONTENT_URI;
import static cat.urv.deim.asm.p3.shared.DatabaseCredentials.Events.*;

public class EventsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    RecyclerView recyclerEvents;
    ArrayList<String> titlesList;
    ArrayList<String> contentsList;
    ArrayList<String> urlImagesList;
    ArrayList<Tag[]> tagList;
    ArrayList<String> webURLList;
    ArrayList<String> typeList;
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
        contentsList = new ArrayList<>();
        urlImagesList = new ArrayList<>();
        tagList = new ArrayList<>();
        webURLList = new ArrayList<>();
        typeList = new ArrayList<>();

        //******************************************************************************************
        /*SQLite QUERY*/

        /*
        SQLiteProvider sqLiteProvider = new SQLiteProvider();
        Cursor queryCursor = sqLiteProvider.query(CONTENT_URI, null, null, null, null);
        Loader<Cursor> cursorLoader = onCreateLoader(0, null);
        onLoadFinished(cursorLoader, queryCursor);
        */

        //******************************************************************************************

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,     // Get list of events
                url,                    // Url defined in parameters
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("EVENTS RESPONSE: ", response);

                        String eventsString = null;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            eventsString = jsonObject.getString("events");
                            Log.e("Prova JSONOBJECT EVENTS: ", eventsString);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Event[] events;

                        if (!response.equals(null)) {
                            Gson gson = new Gson();
                            Log.e("STRING", eventsString);
                            events = gson.fromJson(eventsString, Event[].class);

                            // *********************
                            // Show Events on screen
                            for (Event event : events) {

                                contentsList.add(event.getDescription());
                                urlImagesList.add(event.getImageURL());
                                typeList.add(event.getType());

                                if(event.getWebURL() != null && event.getName() != null && event.getTags() != null) {
                                    webURLList.add(event.getWebURL());
                                    titlesList.add(event.getName());
                                    tagList.add(event.getTags());
                                }else{
                                    webURLList.add("");
                                    titlesList.add("");
                                    tagList.add(null);
                                }
                            }
                            EventsListAdapter adapter =
                                    new EventsListAdapter
                                            (titlesList, contentsList, urlImagesList, tagList, typeList, webURLList);
                            recyclerEvents.setAdapter(adapter);
                            // *********************

                        } else {
                            Log.e("Your array response: " + response, "Data null");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Your response: ", "ERROR");
                        EventsListAdapter adapter = new EventsListAdapter(null, null, null, null, null, null);
                        recyclerEvents.setAdapter(adapter);
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

    private void insertEvent(String description, String imageURL, String name, String type, String webURL, String tags) {
        ContentValues values = new ContentValues();
        values.put(EVENTS_DESCRIPTION, description);
        values.put(EVENTS_IMAGEURL, imageURL);
        values.put(EVENTS_NAME, name);
        values.put(EVENTS_TYPE, type);
        values.put(EVENTS_WEBURL, webURL);
        values.put(EVENTS_TAGS, tags);

        Uri contactUri = getActivity().getApplicationContext().getContentResolver().insert(CONTENT_URI, values);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getActivity().getApplicationContext(), CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        ArrayList<Event> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            int descriptionIndex = cursor.getColumnIndex(EVENTS_DESCRIPTION);
            int imageURLIndex = cursor.getColumnIndex(EVENTS_IMAGEURL);
            int nameIndex = cursor.getColumnIndex(EVENTS_NAME);
            int typeIndex = cursor.getColumnIndex(EVENTS_TYPE);
            int webURLIndex = cursor.getColumnIndex(EVENTS_WEBURL);
            int tagsIndex = cursor.getColumnIndex(EVENTS_TAGS);

            String description = cursor.getString(descriptionIndex);
            String imageURL = cursor.getString(imageURLIndex);
            String name = cursor.getString(nameIndex);
            String type = cursor.getString(typeIndex);
            String webURL = cursor.getString(webURLIndex);
            Gson gson = new Gson();
            Tag[] tags = gson.fromJson(cursor.getString(tagsIndex), Tag[].class);

            Event event = new Event(description, imageURL, name, tags, type, webURL);
            list.add(event);
        }

        EventsListAdapter adapter = new EventsListAdapter (list);
        recyclerEvents.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        //Not necessary to implement
    }
}
