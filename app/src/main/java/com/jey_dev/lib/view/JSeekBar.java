package com.jey_dev.lib.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by jeyhoon on 16. 3. 16..
 */
public class JSeekBar extends android.support.v7.widget.AppCompatSeekBar {
    public JSeekBar(Context context) {
        super(context);
        initView();
    }

    public JSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public JSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public JSeekBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        initView();
//    }
    private void initView(){
        try {
            setProgressDrawable(getContext().getResources().getDrawable(R.drawable.seekbar_progress));
            setThumb(getContext().getResources().getDrawable(R.drawable.seekbar_thumb));
        }catch(Exception e){}
    }
}
