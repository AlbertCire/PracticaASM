package cat.urv.deim.asm.p3.shared;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
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
import com.google.gson.internal.bind.DateTypeAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.*;

import cat.urv.deim.asm.R;

import cat.urv.deim.asm.models.Event;

/*
import cat.urv.deim.asm.libraries.commanagerdc.models.Event;
import cat.urv.deim.asm.libraries.commanagerdc.models.Tag;
import cat.urv.deim.asm.libraries.commanagerdc.providers.DataProvider;
*/

public class EventsFragment extends Fragment {

    RecyclerView recyclerEvents;
    ArrayList<String> titlesList;
    ArrayList<String> contentsList;
    ArrayList<String> urlImagesList;
    ArrayList<String> tagList;
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


        //DataProvider dataProvider = DataProvider.getInstance(
        //        this.getContext(),
        //        R.raw.faqs,R.raw.news,R.raw.articles,R.raw.events,R.raw.calendar);
        //List<Event> events = dataProvider.getEvents();

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
                            /*
                            String splitResponse;
                            StringBuilder eventArray = new StringBuilder();
                            eventArray.append("[");
                            splitResponse = response.split("\\[")[1];
                            splitResponse = splitResponse.split("]")[0];
                            eventArray.append(splitResponse);
                            eventArray.append("]");
                            */
                            Gson gson = new Gson();
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
                                    tagList.add("");
                                }



                                /*
                                StringBuilder sb = new StringBuilder();
                                List<Tag> tags = event.getTags();
                                for (int i = 0; i < tags.size(); i++) {
                                    sb.append(tags.get(i).getName());
                                    if (i < tags.size() - 1) {
                                        sb.append(", ");
                                    }
                                }
                                tagList.add(sb.toString());
                                */

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        }
    }

}
