package jp.aknot.bugreport.activity;

import android.os.AsyncTask;

public abstract class AknotAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);

        if (result instanceof Throwable) {
            onTaskFailure(new UnauthorizedException());
        } else {
            onTaskSuccess();
        }
    }

    public abstract void onTaskFailure(Exception ex);
    public abstract void onTaskSuccess();
}
