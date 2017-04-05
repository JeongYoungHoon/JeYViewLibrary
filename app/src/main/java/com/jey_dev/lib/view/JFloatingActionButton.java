package com.jey_dev.lib.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.util.AttributeSet;


/**
 * Created by JeyHoon on 16. 6. 2..
 */
public class JFloatingActionButton extends android.support.v7.widget.AppCompatImageView {
    public static final int SCALE_NORMAL = 0;
    public static final int SCALE_MINI = 1;
    public static final int SCALE_BIG = 2;
    private int scaleType = SCALE_NORMAL;
    private int tintColor = Color.parseColor("#FF006934");
    private int imgPadding = 10;
    private LayerDrawable layerDrawable = null;

    private Context ctx = null;

    public JFloatingActionButton(Context context) {
        super(context);
        ctx = context;
        initView();
    }

    public JFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx = context;
        getAttrs(attrs);
        initView();
    }

    public JFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ctx = context;
        getAttrs(attrs, defStyleAttr);
        initView();
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public JFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        ctx = context;
//        getAttrs(attrs, defStyleAttr,defStyleRes);
//        initView();
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int mini = JImageUtils.dpToPx(getContext(), 50);
        final int normal = JImageUtils.dpToPx(getContext(), 64);
        final int big = JImageUtils.dpToPx(getContext(), 100);
        int size = normal;
        switch (scaleType) {
            case SCALE_NORMAL:
                size = normal;
                break;
            case SCALE_MINI:
                size = mini;
                break;
            case SCALE_BIG:
                size = big;
                break;
        }
        setMeasuredDimension(size, size);
    }

    private void initView() {
        setClickable(true);
        layerDrawable = getLayerDrawable();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(layerDrawable);
        } else {
            setBackgroundDrawable(layerDrawable);
        }

        setPadding(imgPadding, imgPadding, imgPadding, imgPadding);
    }

    private LayerDrawable getLayerDrawable() {
        Resources res = ctx.getResources();
        Drawable layer1 = res.getDrawable(R.drawable.bg_shadow);
        Drawable layer2 = getButtonBackground(tintColor);
        LayerDrawable result = new LayerDrawable(new Drawable[]{layer1, layer2});
//        result.getDrawable(2).
        final int padding = JImageUtils.dpToPx(ctx, 5);
        result.setLayerInset(1, padding, padding, padding, padding);
        return result;
    }

    //    private RippleDrawable getLayerRippleDrawable(){
//        Resources res = ctx.getResources();
//        RippleDrawable result=new RippleDrawable();
//    }
    private ShapeDrawable getButtonBackground(int tintColor) {
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(tintColor);

        return drawable;
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.JFloatingActionButton);

        setTypeArray(typedArray);
    }


    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.JFloatingActionButton, defStyle, 0);
        setTypeArray(typedArray);

    }
    private void getAttrs(AttributeSet attrs, int defStyle, int defStyleRes) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.JFloatingActionButton, defStyle, defStyleRes);
        setTypeArray(typedArray);

    }


    private void setTypeArray(TypedArray typedArray) {

        scaleType = typedArray.getInt(R.styleable.JFloatingActionButton_scaleType, SCALE_NORMAL);
        tintColor = typedArray.getColor(R.styleable.JFloatingActionButton_tintColor, Color.parseColor("#FF006934"));
        imgPadding = typedArray.getDimensionPixelSize(R.styleable.JFloatingActionButton_imgPadding, JImageUtils.dpToPx(ctx, 10));
        typedArray.recycle();

    }

    public JFloatingActionButton setScaleType(int scaleType) {
        this.scaleType = scaleType;
        return this;
    }

    public JFloatingActionButton setTintColor(int tintColor) {
        this.tintColor = tintColor;
        ((ShapeDrawable) layerDrawable.getDrawable(1)).getPaint().setColor(tintColor);
        return this;
    }
}
