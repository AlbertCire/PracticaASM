package cat.urv.deim.asm.p3.common;

import cat.urv.deim.asm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cat.urv.deim.asm.libraries.commanagerdc.models.Event;
import cat.urv.deim.asm.libraries.commanagerdc.providers.DataProvider;

public class EventDetailActivity extends Activity {

    private int eventIndex;             // The index of the concrete event we want from JSON
    private TextView eventTitle;        // In the JSON, it corresponds to the attribute "name"
    private ImageView eventCoverImage;  // The JSON gives an URL to the actual image resource
    private TextView eventKeywords;     // Or "tags"
    private TextView eventText;

    private ImageView favIcon;
    private boolean favSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        // Index of the Event clicked on the Event fragments
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            eventIndex = bundle.getInt("eventIndex");
        } else {
            eventIndex = 0;
        }

        DataProvider dataProvider = DataProvider.getInstance(
                this.getApplicationContext(),
                R.raw.faqs,R.raw.news,R.raw.articles,R.raw.events,R.raw.calendar);
        List<Event> events = dataProvider.getEvents();

        // Using the data extracted from the JSON to update the view
        eventTitle = findViewById(R.id.event_title);
        eventTitle.setText(events.get(eventIndex).getName());

        eventKeywords = findViewById(R.id.event_keywords);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < events.get(eventIndex).getTags().size(); i++) {
            String nextKeyword = events.get(eventIndex).getTags().get(i).getName();
            sb.append(nextKeyword);
            if (i < events.get(eventIndex).getTags().size() - 1) {
                sb.append(", ");
            }
        }
        eventKeywords.setText(sb.toString());

        // Loading the image from URL using external module Picasso
        eventCoverImage = findViewById(R.id.event_cover_image);
        Picasso.get().load(events.get(eventIndex).getImageURL()).into(eventCoverImage);

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

        eventText = findViewById(R.id.event_text);
        eventText.setText(events.get(eventIndex).getDescription());
    }
}
