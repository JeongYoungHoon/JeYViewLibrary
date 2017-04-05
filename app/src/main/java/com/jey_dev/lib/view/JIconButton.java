package com.jey_dev.lib.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by JeyHoon on 16. 7. 11..
 */
public class JIconButton extends LinearLayout {
    private Context ctx=null;
    private View root=null;
    private ImageView iconView=null;
    private View gabView=null;
    private TextView textView=null;

    private int backgroundColor= R.color.colorPrimaryDark;

    public JIconButton(Context context) {
        super(context);
        ctx=context;
        setViewIds();
        initView();
    }

    public JIconButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx=context;
        setViewIds();
        getAttrs(attrs);
        initView();
    }

    public JIconButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ctx=context;
        setViewIds();
        getAttrs(attrs,defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public JIconButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        ctx=context;
        setViewIds();
        getAttrs(attrs,defStyleAttr,defStyleRes);
        initView();
    }

    private void setViewIds(){
        LayoutInflater.from(ctx).inflate(R.layout.view_icon_button, this);
        root=findViewById(R.id.view_icon_button_root);
        iconView=(ImageView)findViewById(R.id.view_icon_button_icon);
        gabView=findViewById(R.id.view_icon_button_gab);
        textView=(TextView)findViewById(R.id.view_icon_button_text);
    }

    private void initView(){

    }

    public void setText(CharSequence cs){
        textView.setText(cs);
    }

    public void setText(String str){
        textView.setText(str);
    }

    public void setText(int strResId){
        textView.setText(strResId);
    }

    public void setTextColor(int color){
        textView.setTextColor(color);
    }

    public void setTextColor(ColorStateList color){
        textView.setTextColor(color);
    }

    public void setTextSize(float size){
        textView.setTextSize(size);
    }

    public void setBackgroundColor(int backgroundColor){
        this.backgroundColor=backgroundColor;
        setBackground(getBackgroundDrawable());
    }

//    private Drawable getBackgroundDrawable(){
//            float[] outerR = new float[] { 24, 24, 24, 24, 0, 0, 0, 0 };
//            RectF inset = new RectF(6, 6, 6, 6);
//            float[] innerR = new float[] { 12, 12, 0, 0, 12, 12, 0, 0 };
//            ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(outerR,inset, innerR));
////            drawable.setIntrinsicWidth(200);
////            drawable.setIntrinsicHeight(100);
//            drawable.getPaint().setColor(backgroundColor);
//            return drawable;
//    }
    private Drawable getBackgroundDrawable(){
        Resources res = ctx.getResources();
        Drawable layer1 = res.getDrawable(R.drawable.bg_jicon_button_shadow);
        Drawable layer2 = getButtonBackground();
        LayerDrawable result = new LayerDrawable(new Drawable[]{layer1, layer2});
//        result.getDrawable(2).
        final int padding = JImageUtils.dpToPx(ctx, 2);
        result.setLayerInset(1, padding, padding, padding, padding);
        return result;
    }
    private Drawable getButtonBackground() {
        int btnColor = backgroundColor;
        GradientDrawable g = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT, new int[] { btnColor,
                btnColor, btnColor });
        g.setShape(GradientDrawable.RECTANGLE);
        g.setCornerRadius(JImageUtils.dpToPx(ctx,10));
        return g;
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.JIconButton);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.JIconButton, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle, int defStyleRes) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.JIconButton, defStyle, defStyleRes);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray a) {
        String text = a.getString(R.styleable.JIconButton_text);
        setText(text);
        int textSize=a.getDimensionPixelSize(R.styleable.JIconButton_labelTextSize,30);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        int textColor=a.getColor(R.styleable.JIconButton_textColor, Color.BLACK);
        setTextColor(textColor);
        int backgroundColor=a.getColor(R.styleable.JIconButton_backgroundColor,getResources().getColor(R.color.colorPrimaryDark));
        setBackgroundColor(backgroundColor);
        iconView.setImageResource(a.getResourceId(R.styleable.JIconButton_iconSrc, ctx.getApplicationInfo().icon));
        gabView.setLayoutParams(
                new LayoutParams(a.getDimensionPixelSize(R.styleable.JIconButton_gabSize, JImageUtils.dpToPx(ctx,10))
                        ,a.getDimensionPixelSize(R.styleable.JIconButton_gabSize, JImageUtils.dpToPx(ctx,1))));
        iconView.setLayoutParams(
                new LayoutParams(a.getDimensionPixelSize(R.styleable.JIconButton_iconWidth, JImageUtils.dpToPx(ctx,48))
                        ,a.getDimensionPixelSize(R.styleable.JIconButton_iconHeight, JImageUtils.dpToPx(ctx,48))));
        int iconPadding=a.getDimensionPixelSize(R.styleable.JIconButton_iconPadding, JImageUtils.dpToPx(ctx,5));
        iconView.setPadding(iconPadding,iconPadding,iconPadding,iconPadding);
        a.recycle();
    }   //가져온대로 화면을 구성하고
}
