package cat.urv.deim.asm.p3.shared;

import cat.urv.deim.asm.R;
import cat.urv.deim.asm.libraries.commanagerdc.models.Faq;
import cat.urv.deim.asm.libraries.commanagerdc.providers.DataProvider;
import cat.urv.deim.asm.p2.common.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FaqsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

        ExpandableListView expLV = (ExpandableListView) findViewById(R.id.exp_lv);
        ExpLVAdapter adapter;
        ArrayList<String> questionList = new ArrayList<>();
        HashMap<String, String> questionAnswer = new HashMap<>();

        // Retrieve the data from the JSON files, map each question to its answer, and set
        // the adapter for the ExpandableListView
        DataProvider dataProvider = DataProvider.getInstance(
                this.getApplicationContext(),
                R.raw.faqs,R.raw.news,R.raw.articles,R.raw.events,R.raw.calendar);
        List<Faq> faqs = dataProvider.getFaqs();

        for (Faq faq : faqs) {
            questionList.add(faq.getTitle());
            questionAnswer.put(faq.getTitle(), faq.getBody());
        }
        adapter = new ExpLVAdapter(questionList, questionAnswer, this);
        expLV.setAdapter(adapter);

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
