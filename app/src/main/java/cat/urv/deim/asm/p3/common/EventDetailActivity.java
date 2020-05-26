package cat.urv.deim.asm.p3.common;

import cat.urv.deim.asm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import cat.urv.deim.asm.libraries.commanagerdc.models.Event;
import cat.urv.deim.asm.libraries.commanagerdc.providers.DataProvider;

public class EventDetailActivity extends Activity {

    private TextView keywordsTextView;
    private ImageView favIcon;
    private boolean favSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        DataProvider dataProvider = DataProvider.getInstance(
                this.getApplicationContext(),
                R.raw.faqs,R.raw.news,R.raw.articles,R.raw.events,R.raw.calendar);
        List<Event> events = dataProvider.getEvents();

        // Writing on the view
        keywordsTextView = findViewById(R.id.event_keywords);
        keywordsTextView.setText("" /*keywords*/);

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
