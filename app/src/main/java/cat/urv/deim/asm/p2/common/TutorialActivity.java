package cat.urv.deim.asm.p2.common;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ProgressBar;

import java.util.Hashtable;

public class TutorialActivity extends Activity {

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private int currentImage = 0;
    private static final int        // All the images in the order they appear in the tutorial
            IMG_BE_IN_TOUCH = 0,
            IMG_NETWORKING = 1,
            IMG_LEARN = 2;
    private Hashtable<Integer, Integer> imgNumberToId;  // Object to map images to its resource id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        progressBar = findViewById(R.id.progressBar);

        // Setting the images for the tutorial
        imgNumberToId = new Hashtable<>();
        imgNumberToId.put(IMG_BE_IN_TOUCH, R.drawable.help_1);  // Change the resource names
        imgNumberToId.put(IMG_NETWORKING, R.drawable.help_2);   // here and only here when needed
        imgNumberToId.put(IMG_LEARN, R.drawable.help_3);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Update image
        currentImage++;
        // setImageResource();

        // Make increment in the progress bar and update it
        progressStatus += 33;
        if (progressStatus == 99) {
            progressStatus = 100;
        }
        updateProgressBar();
    }

    private void updateProgressBar() {
        int percToUpdate = progressStatus - progressBar.getProgress();
        for (int i = 0; i < percToUpdate; i++) {
            progressBar.incrementProgressBy(1);
        }
    }
}
