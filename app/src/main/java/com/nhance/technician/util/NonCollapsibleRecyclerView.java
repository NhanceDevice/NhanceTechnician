package com.nhance.technician.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Rahul on 9/14/2017.
 */

public class NonCollapsibleRecyclerView extends RecyclerView {
    public NonCollapsibleRecyclerView(Context context) {
        super(context);
    }

    public NonCollapsibleRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonCollapsibleRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 4, MeasureSpec.AT_MOST));
    }
}