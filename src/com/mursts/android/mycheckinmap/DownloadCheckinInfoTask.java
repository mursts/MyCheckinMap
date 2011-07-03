package com.mursts.android.mycheckinmap;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.mursts.android.mycheckinmap.DownloadCheckinInfoTask.DownloadChekinResult;

/**
 * @see http://d.hatena.ne.jp/tomorrowkey/20100824/1282655538
 */
public class DownloadCheckinInfoTask extends AsyncTask<Object, Void, DownloadChekinResult> {

    private static final String RSS_URL = "YOUR_RSS_URL";

    private Context mContext;
    private DownloadChekinInfoCallBack mCallBack;

    private ProgressDialog mDialog = null;

    public DownloadCheckinInfoTask(Context context, DownloadChekinInfoCallBack callback) {
        mContext = context;
        mCallBack = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context context = mContext;
        mDialog = new ProgressDialog(context);
        mDialog.setMessage(context.getString(R.string.download_now));
        mDialog.show();
    }

    @Override
    protected DownloadChekinResult doInBackground(Object... arg0) {
        ArrayList<CheckinInfo> checkinList = null;

        DefaultHttpClient client = null;
        HttpResponse res = null;

        DownloadChekinResult result = new DownloadChekinResult();
        result.setResult(false);

        try {
            client = new DefaultHttpClient();
            res = client.execute(new HttpGet(RSS_URL));
            int status = res.getStatusLine().getStatusCode();
            if(HttpStatus.SC_OK == status) {
                String rss = EntityUtils.toString(res.getEntity(), "UTF-8");
                checkinList = XmlParser.parser(rss);
            }
        } catch (Exception e) {
            return result;
        }
        result.setList(checkinList);
        result.setResult(true);
        return result;
    }

    @Override
    protected void onPostExecute(DownloadChekinResult result) {
        super.onPostExecute(result);
        mDialog.dismiss();
        if(result.getResult()) {
            mCallBack.onDownloadFinish(result.getList());
        } else {
            mCallBack.onDownloadFailed();
        }
    }

    public class DownloadChekinResult {
        private boolean mResult = false;
        private ArrayList<CheckinInfo> mList = new ArrayList<CheckinInfo>();

        public boolean getResult() {
            return mResult;
        }
        public void setResult(boolean result) {
            this.mResult = result;
        }
        public ArrayList<CheckinInfo> getList() {
            return mList;
        }
        public void setList(ArrayList<CheckinInfo> list) {
            this.mList = list;
        }
    }
}
