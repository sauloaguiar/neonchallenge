package com.sauloaguiar.neonapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.sauloaguiar.neonapplication.R;

/**
 * GridLines is a view which directly overlays the preview and draws
 * evenly spaced grid lines.
 */
public class GridLines extends RecyclerView {
    public GridLines(Context context) {
        super(context);
    }

    public GridLines(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridLines(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override protected void dispatchDraw(Canvas canvas) {
        int lineSpace = (int) getResources().getDimension(R.dimen.grid_line_space);
        int photoOffset = (int) getResources().getDimension(R.dimen.graph_friend_photo);
        int bottom = getBottom() - photoOffset;
        int left = getLeft();
        int right = getRight();

        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.gridLineColor));
        paint.setStrokeWidth((int) getResources().getDimension(R.dimen.grid_line_width));

        while (bottom > 0) {
            canvas.drawLine(left, bottom, right, bottom, paint);
            bottom -= lineSpace;
        }

        super.dispatchDraw(canvas);
    }

}