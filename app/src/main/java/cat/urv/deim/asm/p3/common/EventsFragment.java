package cat.urv.deim.asm.p3.common;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cat.urv.deim.asm.R;
import cat.urv.deim.asm.libraries.commanagerdc.models.Event;
import cat.urv.deim.asm.libraries.commanagerdc.models.Tag;
import cat.urv.deim.asm.libraries.commanagerdc.providers.DataProvider;


public class EventsFragment extends Fragment {

    RecyclerView recyclerEvents;
    CardView cardView;
    ArrayList<String> titlesList;
    ArrayList<String> contentsList;
    ArrayList<String> urlImagesList;
    ArrayList<String> tagList;
    private ImageView favIcon1, bookmarkIcon1;
    private boolean[] selectedIcons = {true, false};


    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        View viewItemList = inflater.inflate(R.layout.item_list, container, false);

        recyclerEvents = view.findViewById(R.id.recyclerId);
        recyclerEvents.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        cardView = viewItemList.findViewById(R.id.events_card);

        titlesList = new ArrayList<>();
        contentsList = new ArrayList<>();
        urlImagesList = new ArrayList<>();
        tagList = new ArrayList<>();

        DataProvider dataProvider = DataProvider.getInstance(
                this.getContext(),
                R.raw.faqs,R.raw.news,R.raw.articles,R.raw.events,R.raw.calendar);
        List<Event> events = dataProvider.getEvents();

        for (Event event : events) {
            titlesList.add(event.getName());
            contentsList.add(event.getDescription());
            urlImagesList.add(event.getImageURL());

            StringBuilder sb = new StringBuilder();
            List<Tag> tags = event.getTags();
            for (int i = 0; i < tags.size(); i++) {
                sb.append(tags.get(i).getName());
                if (i < tags.size() - 1) {
                    sb.append(", ");
                }
            }
            tagList.add(sb.toString());
        }

        EventsListAdapter adapter = new EventsListAdapter(titlesList, contentsList, urlImagesList, tagList);
        recyclerEvents.setAdapter(adapter);

        final TextView selectedTitle = viewItemList.findViewById(R.id.events_title);
        selectedTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = -1;

                // First find out which title has been clicked to know its index
                for (int i = 0; i < titlesList.size(); i++) {
                    if (titlesList.get(i).equalsIgnoreCase(selectedTitle.getText().toString())) {
                        index = i;
                        break;
                    }
                }
                // If the title was found in the event title list, then we go to the particular event
                if (index != -1) {
                    Intent intent = new Intent(
                            getContext(),
                            EventDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("eventIndex", index);     // Passing index as an argument
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        final TextView selectedText = viewItemList.findViewById(R.id.events_content);
        selectedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = -1;

                // First find out which title has been clicked to know its index
                for (int i = 0; i < contentsList.size(); i++) {
                    if (contentsList.get(i).equalsIgnoreCase(selectedText.getText().toString())) {
                        index = i;
                        break;
                    }
                }
                // If the title was found in the event title list, then we go to the particular event
                if (index != -1) {
                    Intent intent = new Intent(
                            getContext(),
                            EventDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("eventIndex", index);     // Passing index as an argument
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        // Fav and bookmark buttons (can be toggled)
        favIcon1 = viewItemList.findViewById(R.id.fav_icon_1);
        bookmarkIcon1 = viewItemList.findViewById(R.id.bookmark_icon_1);

        favIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedIcons[0]) {
                    favIcon1.setImageResource(R.drawable.favorite_border);
                    selectedIcons[0] = false;
                } else {
                    favIcon1.setImageResource(R.drawable.favorite_black);
                    selectedIcons[0] = true;
                }
            }
        });
        bookmarkIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedIcons[1]) {
                    bookmarkIcon1.setImageResource(R.drawable.bookmark_border);
                    selectedIcons[1] = false;
                } else {
                    bookmarkIcon1.setImageResource(R.drawable.bookmark_black);
                    selectedIcons[1] = true;
                }
            }
        });

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
