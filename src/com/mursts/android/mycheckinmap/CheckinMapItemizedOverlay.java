package com.mursts.android.mycheckinmap;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class CheckinMapItemizedOverlay extends ItemizedOverlay<OverlayItem> {

    private Context mContext;
    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

    public CheckinMapItemizedOverlay(Drawable defaultMarker, Context context) {
        super(boundCenter(defaultMarker));
        mContext = context;
    }

    @Override
    protected OverlayItem createItem(int i) {
        return mOverlays.get(i);
    }

    @Override
    public int size() {
        return mOverlays.size();
    }

    public void addOverlay(OverlayItem overlay) {
        mOverlays.add(overlay);
        populate();
    }

    @Override
    public boolean onTap(int Index) {
        OverlayItem item = mOverlays.get(Index);
        String detail = item.getTitle() + "\n" + item.getSnippet();
        Toast.makeText(mContext, detail, Toast.LENGTH_SHORT).show();
        return true;
    }
}
