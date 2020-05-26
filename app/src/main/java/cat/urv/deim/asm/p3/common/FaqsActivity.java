package cat.urv.deim.asm.p3.common;

import cat.urv.deim.asm.R;
import cat.urv.deim.asm.p2.common.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class FaqsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

        /*
        TextView question1, question2, question3, question4;
        TextView extendAnswer1, extendAnswer2, extendAnswer3, extendAnswer4;
        TextView answer1, answer2, answer3, answer4;
        */

        ImageButton arrowBack;

        // Configuring the back button in the toolbar
        arrowBack = findViewById(R.id.back_button_faqs);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        getApplicationContext(),
                        MainActivity.class) );
            }
        });

        /*
        question1 = findViewById(R.id.question_1);
        question2 = findViewById(R.id.question_2);
        question3 = findViewById(R.id.question_3);
        question4 = findViewById(R.id.question_4);

        extendAnswer1 = findViewById(R.id.extend_answer_1);
        extendAnswer2 = findViewById(R.id.extend_answer_2);
        extendAnswer3 = findViewById(R.id.extend_answer_3);
        extendAnswer4 = findViewById(R.id.extend_answer_4);

        answer1 = findViewById(R.id.answer_1);
        answer2 = findViewById(R.id.answer_2);
        answer3 = findViewById(R.id.answer_3);
        answer4 = findViewById(R.id.answer_4);

        // The Question TextViews have an onClickListener to make the answer below visible
        question1.setOnClickListener(new ExtendAnswerOnClickListener(question1, answer1, extendAnswer1));
        question2.setOnClickListener(new ExtendAnswerOnClickListener(question2, answer2, extendAnswer2));
        question3.setOnClickListener(new ExtendAnswerOnClickListener(question3, answer3, extendAnswer3));
        question4.setOnClickListener(new ExtendAnswerOnClickListener(question4, answer4, extendAnswer4));

        // Same as with Question TextViews
        extendAnswer1.setOnClickListener(new ExtendAnswerOnClickListener(extendAnswer1, answer1, extendAnswer1));
        extendAnswer2.setOnClickListener(new ExtendAnswerOnClickListener(extendAnswer2, answer2, extendAnswer2));
        extendAnswer3.setOnClickListener(new ExtendAnswerOnClickListener(extendAnswer3, answer3, extendAnswer3));
        extendAnswer4.setOnClickListener(new ExtendAnswerOnClickListener(extendAnswer4, answer4, extendAnswer4));
        */
    }

    private class ExtendAnswerOnClickListener implements View.OnClickListener {
        TextView clickable, answer, symbolToChange;
        public ExtendAnswerOnClickListener(TextView clickable, TextView answer, TextView symbolToChange) {
            this.clickable = clickable;
            this.answer = answer;
            this.symbolToChange = symbolToChange;
        }
        @Override
        public void onClick(View clickable) {
            if (answer.getVisibility() == View.GONE) {
                answer.setVisibility(View.VISIBLE);
                symbolToChange.setText("v");
            } else {
                answer.setVisibility(View.GONE);
                symbolToChange.setText(">");
            }
        }
    }
}
