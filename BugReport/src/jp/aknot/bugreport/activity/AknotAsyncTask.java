package jp.aknot.bugreport.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public abstract class AknotAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    protected int taskId = -1;
    private ProgressDialog dialog = null;

    @Override
    protected void onPreExecute() {
        if (dialog != null) {
            dialog.show();
        }
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);

        if (result instanceof Throwable) {
            onTaskError((Throwable) result);
        } else {
            if (!isTaskSuccess(result)) {
                onTaskFailure(result);
            } else {
                onTaskSuccess(result);
            }
        }

        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public AknotAsyncTask<Params, Progress, Result> setTaskId(int id) {
        this.taskId = id;
        return this;
    }

    public AknotAsyncTask<Params, Progress, Result> createProgressDialog(Context context, int rscTitleId, int rscMessageId) {
        return createProgressDialog(context, context.getResources().getString(rscTitleId), context.getResources().getString(rscMessageId));
    }

    public AknotAsyncTask<Params, Progress, Result> createProgressDialog(Context context, CharSequence title, CharSequence message) {
        dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        return this;
    }

    public boolean isTaskSuccess(Result result) {
        return true;
    }

    public abstract void onTaskSuccess(Result result);
    public abstract void onTaskFailure(Result result);
    public abstract void onTaskError(Throwable tr);
}
