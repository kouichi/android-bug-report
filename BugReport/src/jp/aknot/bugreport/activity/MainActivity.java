package jp.aknot.bugreport.activity;

import jp.aknot.bugreport.R;
import jp.aknot.bugreport.util.LogUtil;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AbstractAknotAuthActivity {

    private static final int TASK_SAMPLE_ID = 999;

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
                    public void onTaskSuccess(Object result) {
                        showTaskSuccessMsg(taskId);
                    }
                    @Override
                    public void onTaskFailure(Object result) {
                        showTaskFailureMsg(taskId, result);
                    }
                    @Override
                    public void onTaskError(Throwable tr) {
                        showTaskErrorMsg(taskId, tr);
                    }
                }
                .setTaskId(TASK_SAMPLE_ID)
                .createProgressDialog(MainActivity.this, null, "処理中...")
                .execute();
            }
        });

        // 認証エラーを意図的に発生させる
        throw new UnauthorizedException();
    }

    private void showTaskSuccessMsg(int id) {
        Toast.makeText(this, "Task succeeded!", Toast.LENGTH_LONG).show();
    }

    private void showTaskFailureMsg(int id, Object result) {
        LogUtil.w(TAG, "Failed to process task!: id=" + id + ", result=" + result);
    }

    private void showTaskErrorMsg(int id, Throwable tr) {
        LogUtil.e(TAG, "Error Task!: id=" + id, tr);
    }
}
