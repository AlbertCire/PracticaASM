package cat.urv.deim.asm.p2.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TutorialActivity extends Activity {

    private int currentTutorialScreen = 0;
    private static final int NUM_TUTORIAL_SCREENS = 3;
    private int[] imgResource = new int[NUM_TUTORIAL_SCREENS];   // ids for every central image
    private int[] txtResource = new int[NUM_TUTORIAL_SCREENS];   // ids for every central text

    private ImageView imgMainImage;         // The central image itself
    private TextView txtTutorial;           // And the text below it
    private ProgressBar progressBar;        // Shows in which step of the tutorial we are
    private ImageButton skipTutorial;       // Skips until the login screen
    private float progressStatus = 0f;      // How full is the bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        // Prepare the resources (image and text for each tutorial screen)
        imgResource[0] = R.drawable.help_1;     txtResource[0] = R.string.tutorial_text_1;
        imgResource[1] = R.drawable.help_2;     txtResource[1] = R.string.tutorial_text_2;
        imgResource[2] = R.drawable.help_3;     txtResource[2] = R.string.tutorial_text_3;

        imgMainImage = findViewById(R.id.image_tutorial);   // Defined in XML
        txtTutorial = findViewById(R.id.tutorial_text);     // Same
        progressBar = findViewById(R.id.progressBar);       // Same
        skipTutorial = findViewById(R.id.skipTutorial);     // Same

        skipTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTutorialScreen = NUM_TUTORIAL_SCREENS - 1;
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        currentTutorialScreen = 0;

        // Update image and text to the current screen
        imgMainImage.setImageResource(imgResource[currentTutorialScreen]);
        txtTutorial.setText(txtResource[currentTutorialScreen]);

        // Make increment in the progress bar and update it
        progressStatus += 100.0 / NUM_TUTORIAL_SCREENS;

        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, progressBar.getProgress(), progressStatus);
        anim.setDuration(2000);
        progressBar.startAnimation(anim);

        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                nextScreen();
            }}, 3000);
    }

    private void nextScreen() {
        if (currentTutorialScreen < NUM_TUTORIAL_SCREENS - 1) {
            currentTutorialScreen++;

            // Update image and text to the current screen
            imgMainImage.setImageResource(imgResource[currentTutorialScreen]);
            txtTutorial.setText(txtResource[currentTutorialScreen]);

            // Make increment in the progress bar and update it
            progressStatus += 100.0 / NUM_TUTORIAL_SCREENS;

            ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, progressBar.getProgress(), progressStatus);
            anim.setDuration(1500);
            progressBar.startAnimation(anim);

            (new Handler()).postDelayed(new Runnable() {
                public void run() {
                    nextScreen();
                }
            }, 3000);
        } else {
            currentTutorialScreen = NUM_TUTORIAL_SCREENS - 1;
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }

    class ProgressBarAnimation extends Animation {
        private ProgressBar progressBar;
        private float from;
        private float  to;

        private ProgressBarAnimation(ProgressBar progressBar, float from, float to) {
            super();
            this.progressBar = progressBar;
            this.from = from;
            this.to = to;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            float value = from + (to - from) * interpolatedTime;
            progressBar.setProgress((int) value);
        }

    }
}
