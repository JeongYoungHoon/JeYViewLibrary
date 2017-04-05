package com.jey_dev.lib.view;

/**
 * Created by JeyHoon on 16. 5. 28..
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

//import com.wenoun.based.JUtil;
//import com.wenoun.based.R;
//import com.wenoun.withhappy.talk.R;


/**
 * Created by TedPark on 16. 4. 11..
 */
public class JClearSearchText extends android.support.v7.widget.AppCompatAutoCompleteTextView implements TextWatcher, View.OnTouchListener, View.OnFocusChangeListener {
    private Drawable clearDrawable;
    private Drawable searchDrawable;
    private OnFocusChangeListener onFocusChangeListener;
    private OnTouchListener onTouchListener;
    private OnClickListener onClearListener=new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private boolean isSearchIconBack=true;
    private OnClickListener onSearchListener=new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    public JClearSearchText(final Context context) {
        super(context);

        init();
    }
    public JClearSearchText(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        getAttrs(attrs);
        init();
    }
    public JClearSearchText(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(attrs,defStyleAttr);
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
        setInputType(InputType.TYPE_CLASS_TEXT);
        setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        Drawable tempDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_clear_white_24dp);
        Drawable temp0Drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_search_black_24dip);
        clearDrawable = DrawableCompat.wrap(tempDrawable);
        DrawableCompat.setTintList(clearDrawable,getHintTextColors());

        searchDrawable = DrawableCompat.wrap(temp0Drawable);
        DrawableCompat.setTintList(searchDrawable,getTextColors());
//        clearDrawable.setBounds(0, 0, clearDrawable.getIntrinsicWidth(), clearDrawable.getIntrinsicHeight());
//        DrawableCompat.setTintList(iconDrawable,getHintTextColors());
        int dip24= JImageUtils.dpToPx(getContext(),20);
        clearDrawable.setBounds(0, 0, dip24,dip24);
        searchDrawable.setBounds(0,0,dip24,dip24);
        setClearIconVisible(false);
        setSearchIconVisible(true);
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
        if (searchDrawable.isVisible() && x > getWidth() - getPaddingRight() - searchDrawable.getIntrinsicWidth()) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                onSearchListener.onClick(view);
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
        setCompoundDrawables(isSearchIconBack?null:searchDrawable, null, visible ? clearDrawable : isSearchIconBack?searchDrawable:null, null);
    }
    private void setSearchIconVisible(boolean visible) {
        searchDrawable.setVisible(visible, false);
        setCompoundDrawables(isSearchIconBack?null:searchDrawable,null,  visible ? isSearchIconBack?searchDrawable:null: null,  null);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.JFloatingActionButton);

        setTypeArray(typedArray);
    }


    private void getAttrs(AttributeSet attrs, int defStyle) {
        getAttrs(attrs, defStyle,0);

    }
    private void getAttrs(AttributeSet attrs, int defStyle, int defStyleRes) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.JFloatingActionButton, defStyle, defStyleRes);
        setTypeArray(typedArray);

    }


    private void setTypeArray(TypedArray typedArray) {

        isSearchIconBack = typedArray.getBoolean(R.styleable.JClearSearchText_searchIconLeft, true);
        typedArray.recycle();

    }
    public void setOnClearClickListener(OnClickListener listener){
        onClearListener=listener;
    }
    public void setOnSearchListener(OnClickListener listener){onSearchListener=listener;}
    public boolean isSearchIconBack(){return isSearchIconBack;}
    public void setSearchIconBack(boolean isSearchIconBack){this.isSearchIconBack=isSearchIconBack;}
}
