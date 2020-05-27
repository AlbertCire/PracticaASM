package cat.urv.deim.asm.p2.common;

import cat.urv.deim.asm.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ErrorLoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_login);

        Button TryAgainButton = (Button) findViewById(R.id.TryAgainButton);
        ImageView logo = findViewById(R.id.errorLogInImageView);

        TryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GlobalLoginClass.isShowBackToMenu()) {
                    startActivity(new Intent(
                            getApplicationContext(),
                            Login2Activity.class));
                }else {
                    startActivity(new Intent(
                            getApplicationContext(),
                            LoginActivity.class));
                }
            }
        });

    }

}
