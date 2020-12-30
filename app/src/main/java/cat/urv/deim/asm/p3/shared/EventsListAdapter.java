package cat.urv.deim.asm.p3.shared;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cat.urv.deim.asm.R;
import cat.urv.deim.asm.models.Tag;

import static androidx.core.content.ContextCompat.startActivity;


public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.ViewHolder> {

    ArrayList<String> titlesList;
    ArrayList<String> descriptionsList;
    ArrayList<String> urlImagesList;
    ArrayList<Tag[]> tagList;
    ArrayList<String> typeList;
    ArrayList<String> webURLList;

    ImageView favIcon1, bookmarkIcon1;
    private boolean[] selectedIcons = {true, true};




    public EventsListAdapter(ArrayList<String> titlesList, ArrayList<String> descriptionsList,
                             ArrayList<String> urlImagesList, ArrayList<Tag[]> tagList,
                             ArrayList<String> typeList, ArrayList<String> webURLList) {



        this.titlesList = titlesList;
        this.descriptionsList = descriptionsList;
        this.urlImagesList = urlImagesList;
        this.tagList = tagList;
        this.typeList = typeList;
        this.webURLList = webURLList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);


        final TextView selectedTitle = view.findViewById(R.id.events_title);
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
                Gson gson = new Gson();
                // If the title was found in the event title list, then we go to the particular event
                if (index != -1) {
                    Intent intent = new Intent(
                            parent.getContext(),
                            EventDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title", titlesList.get(index));
                    bundle.putString("description", descriptionsList.get(index));
                    bundle.putString("imageURL", urlImagesList.get(index));
                    bundle.putString("tags", gson.toJson(tagList.get(index)));
                    bundle.putString("type", typeList.get(index));
                    bundle.putString("webURL", webURLList.get(index));


                    intent.putExtras(bundle);
                    startActivity(parent.getContext(), intent, null);
                }

            }

        });

        final TextView selectedText = view.findViewById(R.id.events_content);
        selectedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = -1;

                // First find out which title has been clicked to know its index
                for (int i = 0; i < descriptionsList.size(); i++) {
                    if (descriptionsList.get(i).equalsIgnoreCase(selectedText.getText().toString())) {
                        index = i;
                        break;
                    }
                }
                // If the title was found in the event title list, then we go to the particular event
                if (index != -1) {
                    Intent intent = new Intent(
                            parent.getContext(),
                            EventDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("eventIndex", index);     // Passing index as an argument
                    intent.putExtras(bundle);
                    startActivity(parent.getContext(), intent, null);
                }
            }
        });

        favIcon1 = view.findViewById(R.id.fav_icon_1);
        bookmarkIcon1 = view.findViewById(R.id.bookmark_icon_1);

        favIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedIcons[0]) {
                    Toast.makeText(parent.getContext(), R.string.add_fav, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(parent.getContext(), R.string.del_fav, Toast.LENGTH_SHORT).show();

                }
                selectedIcons[0] = !selectedIcons[0];
            }
        });
        bookmarkIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedIcons[1]) {
                    Toast.makeText(parent.getContext(), R.string.add_bookmark, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(parent.getContext(), R.string.del_bookmark, Toast.LENGTH_SHORT).show();

                }
                selectedIcons[1] = !selectedIcons[1];
            }
        });



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Gson gson = new Gson();
            holder.updateData(titlesList.get(position), descriptionsList.get(position), urlImagesList.get(position), gson.toJson(tagList.get(position)));
    }

    @Override
    public int getItemCount() {
        // Just the number of events we have
        return titlesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitle;
        TextView eventContent;
        ImageView eventImage;
        TextView eventTags;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitle = (TextView) itemView.findViewById(R.id.events_title);
            eventContent = (TextView) itemView.findViewById(R.id.events_content);
            eventImage = (ImageView) itemView.findViewById(R.id.event_image);
            eventTags = (TextView) itemView.findViewById(R.id.event_tags);
        }

        public void updateData(String title, String content, String image, String stringTags) {
            eventTitle.setText(title);
            eventContent.setText(content);
            Picasso.get().load(image).into(eventImage); // URL image to ImageView
            Gson gson = new Gson();

            Tag[] tags = gson.fromJson(stringTags, Tag[].class);
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
        }
    }


}
