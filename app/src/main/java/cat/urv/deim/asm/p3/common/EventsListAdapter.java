package cat.urv.deim.asm.p3.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cat.urv.deim.asm.R;

public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.ViewHolder> {

    ArrayList<String> titlesList;
    ArrayList<String> contentsList;
    ArrayList<String> urlImagesList;

    public EventsListAdapter(ArrayList<String> titlesList, ArrayList<String> contentsList, ArrayList<String> urlImagesList) {
        this.titlesList = titlesList;
        this.contentsList = contentsList;
        this.urlImagesList = urlImagesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.updateData(titlesList.get(position), contentsList.get(position), urlImagesList.get(position));
    }

    @Override
    public int getItemCount() {
        //Es el numero de eventos que tenemos
        return titlesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitle;
        TextView eventContent;
        ImageView eventImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitle = (TextView) itemView.findViewById(R.id.events_title);
            eventContent = (TextView) itemView.findViewById(R.id.events_content);
            eventImage = (ImageView) itemView.findViewById(R.id.event_image);
        }

        public void updateData(String title, String content, String image) {
            eventTitle.setText(title);
            eventContent.setText(content);
            Picasso.get().load(image).into(eventImage); //pasamos URL a ImageView
        }
    }
}
