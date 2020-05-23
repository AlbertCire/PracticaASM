package cat.urv.deim.asm.p2.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextView email_text = (TextView) findViewById(R.id.loginEmail);
        final TextView password_text = (TextView) findViewById(R.id.loginPassword);
        Button sign_in_button = (Button) findViewById(R.id.SigninButton);
        Button anonymous_user_button = (Button) findViewById(R.id.anonymousUserButton);
        ImageView logo = findViewById(R.id.loginLogo);

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email_text.getText().toString().trim().equals(getString(R.string.user_email)) &&
                        password_text.getText().toString().trim().equals(getString(R.string.user_password))) {
                    GlobalLoginClass.setLoginCorrect(true);
                    GlobalLoginClass.setShowBackToMenu(true);
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

        anonymous_user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalLoginClass.setLoginCorrect(false);
                GlobalLoginClass.setShowBackToMenu(true);
                startActivity(new Intent(
                        getApplicationContext(),
                        MainActivity.class) );
            }
        });
    }


}
