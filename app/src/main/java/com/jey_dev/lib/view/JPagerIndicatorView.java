package com.jey_dev.lib.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by JeyHoon on 16. 7. 4..
 */
public class JPagerIndicatorView extends LinearLayout {
    public static final int SCALE_NORMAL=0;
    public static final int SCALE_SMALL=1;
    public static final int SCALE_BIG=2;

    private int scaleType=SCALE_NORMAL;
    private int dotColor= Color.BLACK;
    private int dotCount=2;
    private boolean isStack=false;
    private Context ctx=null;

    private ArrayList<View> views=new ArrayList<View>();
    private int nowPosition=0;
    private boolean isUncheckWhite=false;
    public JPagerIndicatorView(Context context) {
        super(context);
        ctx=context;
        initView();
    }

    public JPagerIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(attrs);
        ctx=context;
        initView();
    }

    public JPagerIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(attrs,defStyleAttr);
        ctx=context;
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public JPagerIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttrs(attrs,defStyleAttr,defStyleRes);
        ctx=context;
        initView();
    }

    private void initView(){
        setOrientation(HORIZONTAL);
        final int normalSize= JImageUtils.dpToPx(ctx,10);
        final int smallSize= JImageUtils.dpToPx(ctx,5);
        final int bigSize= JImageUtils.dpToPx(ctx,20);
        int size=normalSize;
        if(scaleType==SCALE_NORMAL){
            size=normalSize;
        }else if(scaleType==SCALE_SMALL){
            size=smallSize;
        }else if(scaleType==SCALE_BIG){
            size=bigSize;
        }
        for(int i=0; i<dotCount;i++){
            View view=null;
            if(i==0){
                view=getView(getCheckedDrawable(),size);
            }else{
                view=getView(getUnCheckedDrawable(),size);
            }
            views.add(view);
            addView(view);
        }
    }
    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.JPagerIndicatorView);

        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.JPagerIndicatorView, defStyle, 0);
        setTypeArray(typedArray);

    }

    private void getAttrs(AttributeSet attrs, int defStyle, int defStyleRes) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.JPagerIndicatorView, defStyle, defStyleRes);
        setTypeArray(typedArray);

    }
    private void setTypeArray(TypedArray typedArray) {
        isStack = typedArray.getBoolean(R.styleable.JPagerIndicatorView_isStack,false);
        scaleType = typedArray.getInt(R.styleable.JPagerIndicatorView_scaleType, SCALE_NORMAL);
        dotCount = typedArray.getInt(R.styleable.JPagerIndicatorView_dotCount,2);
        dotColor = typedArray.getInt(R.styleable.JPagerIndicatorView_dotColor, Color.BLACK);
        isUncheckWhite=typedArray.getBoolean(R.styleable.JPagerIndicatorView_uncheckWhite,false);
        typedArray.recycle();

    }

    private Drawable getCheckedDrawable(){
        ShapeDrawable drawable=new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(dotColor);
        return drawable;
    }
    private Drawable getUnCheckedDrawable(){
        ShapeDrawable drawable=new ShapeDrawable(new OvalShape());
        if(isUncheckWhite) {
            drawable.getPaint().setColor(Color.WHITE);
        }else{
            drawable.getPaint().setColor(dotColor);
            drawable.getPaint().setAlpha(80);
        }
        return drawable;
    }
    private View getView(Drawable drawable, int size){
        final int padding= size/2;
        final LayoutParams params=new LayoutParams(size,size);
        params.setMargins(padding,padding,padding,padding);
        View view=new View(ctx);
        view.setLayoutParams(params);
        view.setBackground(drawable);
        return view;
    }
    public void setSelectPosition(int position){
        nowPosition=position;
        refreshDots();
    }
    public void nextPosition(){
        if(nowPosition<dotCount-1) {
            nowPosition++;
            refreshDots();
        }
    }
    public void prePosition(){
        if(nowPosition>0){
            nowPosition--;
            refreshDots();
        }
    }
    public int getNowPosition(){
        return nowPosition;
    }
    public int getSelectPosition(){
        return nowPosition;
    }
    public void setScaleType(int scaleType){
        this.scaleType=scaleType;
        removeAllViews();
        initView();
    }
    public void setDotColor(int dotColor){
        this.dotColor=dotColor;
        removeAllViews();
        initView();
    }
    public void setDotCount(int dotCount){
        this.dotCount=dotCount;
        removeAllViews();
        initView();
    }
    public void setStack(boolean isStack){
        this.isStack=isStack;
        removeAllViews();
        initView();
    }
    public void setAttrs(int scaleType,int dotColor, int dotCount,boolean isStack,boolean isUncheckWhite){
        removeAllViews();
        this.scaleType=scaleType;
        this.dotColor=dotColor;
        this.dotCount=dotCount;
        this.isStack=isStack;
        this.isUncheckWhite=isUncheckWhite;
        initView();
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        views.clear();
    }

    private void refreshDots(){
        for(int i=0; i<dotCount;i++) {
            if(i<=nowPosition) {
                if (isStack) {
                    views.get(i).setBackground(getCheckedDrawable());
                    continue;
                } else if(i==nowPosition) {
                    views.get(i).setBackground(getCheckedDrawable());
                    continue;
                }
                else{

                }
            }
            views.get(i).setBackground(getUnCheckedDrawable());
        }
    }
}
