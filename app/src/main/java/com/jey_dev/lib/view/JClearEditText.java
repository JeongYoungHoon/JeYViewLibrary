package com.jey_dev.lib.view;

/**
 * Created by JeyHoon on 16. 5. 28..
 */

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

//import android.widget.AutoCompleteTextView;


/**
 * Created by TedPark on 16. 4. 11..
 */
public class JClearEditText extends android.support.v7.widget.AppCompatAutoCompleteTextView implements TextWatcher, View.OnTouchListener, View.OnFocusChangeListener {
    private Drawable clearDrawable;
    private Drawable leftDrawable=null;
    private OnFocusChangeListener onFocusChangeListener;
    private OnTouchListener onTouchListener;
    private OnClickListener onClearListener=new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    public JClearEditText(final Context context) {
        super(context);
        init();
    }
    public JClearEditText(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public JClearEditText(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
    }
    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }
    private void init() {
        Drawable tempDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_clear_white_24dp);
        clearDrawable = DrawableCompat.wrap(tempDrawable);
        DrawableCompat.setTintList(clearDrawable,getHintTextColors());
//        clearDrawable.setBounds(0, 0, clearDrawable.getIntrinsicWidth(), clearDrawable.getIntrinsicHeight());
//        DrawableCompat.setTintList(iconDrawable,getHintTextColors());
        int dip24= JImageUtils.dpToPx(getContext(),24);
        clearDrawable.setBounds(0, 0, dip24,dip24);
        setClearIconVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }
    @Override
    public void onFocusChange(final View view, final boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
        if (onFocusChangeListener != null) {
            onFocusChangeListener.onFocusChange(view, hasFocus);
        }
    }
    @Override
    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        final int x = (int) motionEvent.getX();
        if (clearDrawable.isVisible() && x > getWidth() - getPaddingRight() - clearDrawable.getIntrinsicWidth()) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                setError(null);
                setText(null);
                onClearListener.onClick(view);
            }
            return true;
        }
        if (onTouchListener != null) {
            return onTouchListener.onTouch(view, motionEvent);
        } else {
            return false;
        }
    }
    @Override
    public final void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
        if (isFocused()) {
            setClearIconVisible(s.length() > 0);
        }
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void afterTextChanged(Editable s) {
    }
    private void setClearIconVisible(boolean visible) {
        clearDrawable.setVisible(visible, false);
        setCompoundDrawables(null!=leftDrawable?(leftDrawable.isVisible()?leftDrawable:null):null, null, visible ? clearDrawable : null, null);
    }
    public void setLeftIconVisible(boolean visible){
        if(null!=leftDrawable) leftDrawable.setVisible(visible,false);
        setCompoundDrawables(null!=leftDrawable?leftDrawable.isVisible()?leftDrawable:null:null, null, clearDrawable.isVisible() ? clearDrawable : null, null);
    }

    public void setLeftDrawable(int resId){
        setLeftDrawable(resId,null);
    }
    public void setLeftDrawable(int resId, ColorStateList colorStateList){
        Drawable tempDrawable = ContextCompat.getDrawable(getContext(), resId);
        leftDrawable = DrawableCompat.wrap(tempDrawable);
        if(null!=colorStateList)
            DrawableCompat.setTintList(leftDrawable,colorStateList);
        int dip24= JImageUtils.dpToPx(getContext(),24);
        leftDrawable.setBounds(0, 0, dip24,dip24);
        setLeftIconVisible(true);
    }
    public Drawable getLeftDrawable(){return this.leftDrawable;}
    public void setOnClearClickListener(OnClickListener listener){
        onClearListener=listener;
    }
}
