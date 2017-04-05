package com.jey_dev.lib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;


/**
 * Created by JeyHoon on 2016. 10. 28..
 */

public class JImageButton extends android.support.v7.widget.AppCompatImageButton {

    private Paint mPaint=null;
    private boolean isDot=true;

    public JImageButton(Context context) {
        super(context);
        mPaint=new Paint();
    }

    public JImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint();
    }

    public JImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint();
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public JImageButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        mPaint=new Paint();
//    }

    public void setDot(boolean isDot){
        this.isDot=isDot;
    }

    public boolean isDot(){return this.isDot;}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isDot) {
            mPaint.setColor(Color.RED);
            mPaint.setStyle(Paint.Style.FILL);
            int i= JImageUtils.dpToPx(getContext(),14);
            int r= JImageUtils.dpToPx(getContext(),7);

            canvas.drawCircle(getWidth()-(float)i, (float)i, r/2,mPaint);
        }
    }
}
