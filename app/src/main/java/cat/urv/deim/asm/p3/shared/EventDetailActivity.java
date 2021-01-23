package cat.urv.deim.asm.p3.shared;

import cat.urv.deim.asm.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.fragment.app.FragmentTransaction;

import cat.urv.deim.asm.models.Event;
import cat.urv.deim.asm.models.Tag;
import cat.urv.deim.asm.p2.common.MainActivity;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import cat.urv.deim.asm.libraries.commanagerdc.providers.DataProvider;

public class EventDetailActivity extends Activity {

    private TextView eventTitle;        // In the JSON, it corresponds to the attribute "name"
    private ImageView eventImage;  // The JSON gives an URL to the actual image resource
    private TextView eventTags;     // Or "tags"
    private TextView eventDescription;



    private ImageView favIcon;
    private boolean favSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        eventTitle = findViewById(R.id.event_title);
        eventDescription = findViewById(R.id.event_text);
        eventImage = findViewById(R.id.event_cover_image);
        eventTags = findViewById(R.id.event_keywords);

        String title = "";
        String description = "";
        String imageURL = "";
        Tag[] tags = null;
        String type = "";
        String webURL = "";

        Gson gson = new Gson();
        // Index of the Event clicked on the Event fragments
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
            description = bundle.getString("description");
            imageURL = bundle.getString("imageURL");
            tags = gson.fromJson(bundle.getString("tags"), Tag[].class);
            type = bundle.getString("type");
            webURL = bundle.getString("webURL");
        }else{
            title = "Event Not Found";
        }

        //back arrow
        ImageButton arrowBack = findViewById(R.id.back_button_event_detail);

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        getApplicationContext(),
                        MainActivity.class).putExtra("caller", "EventDetailActivity") );
            }
        });

        eventTitle.setText(title);
        // Loading the image from URL using external module Picasso
        Picasso.get().load(imageURL).into(eventImage);
        eventDescription.setText(description);

        //Tag[] tagsList;
        //Gson gson = new Gson();
        //tagsList = gson.fromJson(tags, Tag[].class);

        if (tags!=null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tags.length; i++) {
                String nextKeyword = tags[i].getName();
                sb.append(nextKeyword);
                if (i < tags.length - 1) {
                    sb.append(", ");
                }
            }
            eventTags.setText(sb.toString());
        }else{
            eventTags.setText("");
        }

        // Fav icon configuration
        favIcon = findViewById(R.id.fav_icon);
        favSelected = false;

        favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favSelected) {
                    favIcon.setImageResource(R.drawable.favorite_border);
                    favSelected = false;
                } else {
                    favIcon.setImageResource(R.drawable.favorite_black);
                    favSelected = true;
                }
            }
        });
    }
}
