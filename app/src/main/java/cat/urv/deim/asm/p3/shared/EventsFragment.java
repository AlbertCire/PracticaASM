package cat.urv.deim.asm.p3.shared;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    ArrayList<String> titlesList;
    ArrayList<String> contentsList;
    ArrayList<String> urlImagesList;
    ArrayList<String> tagList;


    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        recyclerEvents = view.findViewById(R.id.recyclerId);
        recyclerEvents.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


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
