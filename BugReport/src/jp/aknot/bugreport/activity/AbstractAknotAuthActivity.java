package jp.aknot.bugreport.activity;

import jp.aknot.bugreport.util.LogUtil;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public abstract class AbstractAknotAuthActivity extends Activity {

    static final String TAG = "@" + AbstractAknotAuthActivity.class.getSimpleName();

    private static final int REQ_RELOGIN_FOR_ON_CREATE_ID              = 0;
    private static final int REQ_RELOGIN_FOR_UNAUTHORIZED_EXCEPTION_ID = 1;

    public boolean isAllowRedirectLogin() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setupViews();
        } catch (UnauthorizedException ex) {
            if (isAllowRedirectLogin()) {
                redirectLogin(REQ_RELOGIN_FOR_ON_CREATE_ID);
            }
        } finally {

        }
    }

    protected abstract void setupViews() throws UnauthorizedException;

    protected void redirectLogin(int requestCode) {
        Intent intent = new Intent(AbstractAknotAuthActivity.this, LoginActivity.class);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case REQ_RELOGIN_FOR_ON_CREATE_ID:
            try {
                setupViews();
            } catch (UnauthorizedException e) {
                LogUtil.d(TAG, "2度目のUnauthorizedExceptionは発生しない", e);
            }
            break;
        default:
            break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        return super.onCreateDialog(id);
    }

    protected void showTaskFailedMsg(int id, Exception ex) {
        LogUtil.d(TAG, "Failed Task Failre!: id=" + id, ex);

        if (ex instanceof UnauthorizedException) {
            redirectLogin(REQ_RELOGIN_FOR_UNAUTHORIZED_EXCEPTION_ID);
        } else {

        }
    }

    protected void showTaskSuccessMsg(int id) {
        Toast.makeText(this, "Task succeeded!", Toast.LENGTH_LONG).show();
    }
}
