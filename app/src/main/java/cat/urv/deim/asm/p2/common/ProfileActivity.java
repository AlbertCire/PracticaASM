package cat.urv.deim.asm.p2.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button GoBack = findViewById(R.id.profile_toolbar);

        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        getApplicationContext(),
                        MainActivity.class) );
            }
        });

    }

}
