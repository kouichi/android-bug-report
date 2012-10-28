package jp.aknot.bugreport.activity;

import jp.aknot.bugreport.R;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AbstractAknotAuthActivity {

    private static final int TASK_SAMPLE = 1;

    @Override
    protected void setupViews() throws UnauthorizedException {
        setContentView(R.layout.main);

        ((Button) findViewById(R.id.exception1_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new NullPointerException("意図的にcatchしないExceptionを発生する");
            }
        });
        ((Button) findViewById(R.id.exception2_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AknotAsyncTask<Void, Void, Object>() {
                    @Override
                    protected Object doInBackground(Void... params) {
                        return new Error("適当なエラー");
                    }

                    @Override
                    public void onTaskFailure(Exception ex) {
                        showTaskFailedMsg(TASK_SAMPLE, ex);
                    }
                    @Override
                    public void onTaskSuccess() {
                        showTaskSuccessMsg(TASK_SAMPLE);
                    }
                }.execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
