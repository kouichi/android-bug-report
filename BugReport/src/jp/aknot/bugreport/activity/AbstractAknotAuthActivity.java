package jp.aknot.bugreport.activity;

import jp.aknot.bugreport.util.LogUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

public abstract class AbstractAknotAuthActivity extends Activity {
    static final String TAG = "@" + AbstractAknotAuthActivity.class.getSimpleName();

    private static final int REQ_RELOGIN_FOR_ON_CREATE_ID              = -1;
//    private static final int REQ_RELOGIN_FOR_UNAUTHORIZED_EXCEPTION_ID = -2;

    private static final int DLG_ERR_UNAUTHORIZED_ID = -1;

    protected boolean isAuthenticationRequired() {
        return true;
    }

    protected boolean isAllowRedirectLogin() {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setupViews();
        } catch (UnauthorizedException ex) {
            if (isAuthenticationRequired()) {
                if (isAllowRedirectLogin()) {
                    redirectLogin(REQ_RELOGIN_FOR_ON_CREATE_ID);
                } else {
                    showDialog(DLG_ERR_UNAUTHORIZED_ID);
                }
            }
        } finally {
            setupViewsFinalize();
        }
    }

    protected abstract void setupViews() throws UnauthorizedException;
    protected void setupViewsFinalize() {
    }

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
        switch (id) {
        case DLG_ERR_UNAUTHORIZED_ID:
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("認証エラー");
            builder.setMessage("この画面にアクセスするためにはログインする必要があります。");
            builder.setPositiveButton("OK", null);
            return builder.create();
        default:
            return super.onCreateDialog(id);
        }
    }
}
