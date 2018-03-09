package com.psm.lifeorganizer.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by pmaestri on 2/26/2018.
 */

public class ParentCustomViewHolder extends RecyclerView.ViewHolder {

    protected RelativeLayout viewForeground;
    protected RelativeLayout viewBackground;

    public RelativeLayout getViewForeground() {
        return viewForeground;
    }

    public RelativeLayout getViewBackground() {
        return viewBackground;
    }

    public ParentCustomViewHolder(View itemView) {
        super(itemView);
    }
}
