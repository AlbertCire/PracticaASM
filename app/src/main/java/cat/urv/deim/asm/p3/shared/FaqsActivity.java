package cat.urv.deim.asm.p3.shared;

import cat.urv.deim.asm.R;
/*
 import cat.urv.deim.asm.libraries.commanagerdc.models.Faq;
 import cat.urv.deim.asm.libraries.commanagerdc.providers.DataProvider;
 */
import cat.urv.deim.asm.models.Event;
import cat.urv.deim.asm.models.Faq;
import cat.urv.deim.asm.p2.common.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
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

public class FaqsActivity extends AppCompatActivity {

    String url;
    String mail;
    String username;
    String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

        // Obtaining credentials
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.credentials);
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.beginObject();
            jsonReader.nextName(); url = jsonReader.nextString() + "faqs";
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

        // Retrieve the data from the JSON files, map each question to its answer, and set
        // the adapter for the ExpandableListView
        /*
        DataProvider dataProvider = DataProvider.getInstance(
                this.getApplicationContext(),
                R.raw.faqs,R.raw.news,R.raw.articles,R.raw.events,R.raw.calendar);
        List<Faq> faqs = dataProvider.getFaqs();
        */

        final Context context = this;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,     // Get list of events
                url,                    // Url defined in parameters
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSE: ", response);
                        Faq[] faqs;
                        if (!response.equals(null)) {
                            String splitResponse;
                            StringBuilder faqArray = new StringBuilder();
                            faqArray.append("[");
                            splitResponse = response.split("\\[")[1];
                            splitResponse = splitResponse.split("]")[0];
                            faqArray.append(splitResponse);
                            faqArray.append("]");

                            Gson gson = new Gson();
                            faqs = gson.fromJson(faqArray.toString(), Faq[].class);

                            // *********************
                            // Show Faqs on screen
                            ArrayList<String> questionList = new ArrayList<>();
                            HashMap<String, String> questionAnswer = new HashMap<>();
                            for (Faq faq : faqs) {
                                questionList.add(faq.getTitle());
                                questionAnswer.put(faq.getTitle(), faq.getBody());
                            }
                            ExpandableListView expLV = (ExpandableListView) findViewById(R.id.exp_lv);
                            ExpLVAdapter adapter;
                            adapter = new ExpLVAdapter(questionList, questionAnswer, context);
                            expLV.setAdapter(adapter);
                            // *********************

                            Log.e("Your response: ", response);
                        } else {
                            Log.e("Your array response: " + response, "Data null");
                        }
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

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);
        queue.start();


        // Configuring the back button in the toolbar
        ImageButton arrowBack;
        arrowBack = findViewById(R.id.back_button_faqs);
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
