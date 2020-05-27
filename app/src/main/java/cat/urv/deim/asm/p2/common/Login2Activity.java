package cat.urv.deim.asm.p2.common;

import cat.urv.deim.asm.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Login2Activity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_2);

        final TextView email_text = (TextView) findViewById(R.id.loginEmail);
        final TextView password_text = (TextView) findViewById(R.id.loginPassword);
        Button sign_in_button = (Button) findViewById(R.id.SigninButton);
        Button continue_anonymously_button = (Button) findViewById(R.id.anonymousUserButton);
        ImageView logo = findViewById(R.id.loginLogo);
        ImageButton arrowBack = findViewById(R.id.back_button2);

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        getApplicationContext(),
                        MainActivity.class) );
            }
        });

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email_text.getText().toString().trim().equals(getString(R.string.user_email)) &&
                        password_text.getText().toString().trim().equals(getString(R.string.user_password))) {
                    GlobalLoginClass.setLoginCorrect(true);
                    startActivity(new Intent(
                            getApplicationContext(),
                            MainActivity.class) );
                } else {
                    startActivity(new Intent(
                            getApplicationContext(),
                            ErrorLoginActivity.class) );
                }
            }
        });

        continue_anonymously_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalLoginClass.setLoginCorrect(false);
                startActivity(new Intent(
                        getApplicationContext(),
                        MainActivity.class) );
            }
        });
    }

}
