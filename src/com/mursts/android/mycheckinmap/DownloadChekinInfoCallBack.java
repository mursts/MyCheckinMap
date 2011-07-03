package com.mursts.android.mycheckinmap;

import java.util.ArrayList;

public interface DownloadChekinInfoCallBack {
    public void onDownloadFinish(ArrayList<CheckinInfo> checkinList);
    public void onDownloadFailed();
}
