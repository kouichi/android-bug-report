package jp.aknot.bugreport.activity;

import android.view.View;
import android.widget.Button;
import jp.aknot.bugreport.R;


public class LoginActivity extends AbstractAknotAuthActivity {
    @Override
    public boolean isAllowRedirectLogin() {
        return false;
    }

    @Override
    protected void setupViews() throws UnauthorizedException {
        setContentView(R.layout.login);

        ((Button) findViewById(R.id.login_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
