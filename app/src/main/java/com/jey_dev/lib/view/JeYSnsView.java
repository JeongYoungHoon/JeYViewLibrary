package com.jey_dev.lib.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;


/**
 * Created by JeyHoon on 16. 6. 1..
 */
public class JeYSnsView extends LinearLayout {
    public static final int SCALE_NORMAL=JFloatingActionButton.SCALE_NORMAL;
    public static final int SCALE_MINI=JFloatingActionButton.SCALE_MINI;
    public static final int SCALE_BIG=JFloatingActionButton.SCALE_BIG;
    private int scaleType=SCALE_NORMAL;
    private View root=null;
    private JFloatingActionButton kakaoView=null;
    private JFloatingActionButton facebookView=null;
    private JFloatingActionButton webView=null;
    private JFloatingActionButton marketView=null;
    public JeYSnsView(Context context) {
        super(context);
        initView();
    }

    public JeYSnsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(attrs);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public JeYSnsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(attrs,defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public JeYSnsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttrs(attrs,defStyleAttr,defStyleRes);
        initView();
    }
    private void initView(){
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(
                infService);
        root = li.inflate(R.layout.view_sns, this, false);
        addView(root);
        kakaoView=(JFloatingActionButton)findViewById(R.id.sns_kakao);
        facebookView=(JFloatingActionButton)findViewById(R.id.sns_facebook);
        webView=(JFloatingActionButton)findViewById(R.id.sns_web);
        marketView=(JFloatingActionButton)findViewById(R.id.sns_market);
        webView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                Uri u = Uri.parse("http://www.jey-dev.com");

                intent.setData(u);
                startActivity(intent);
            }
        });
        kakaoView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("kakaoplus://plusfriend/friend/@tansandevteam"));
//                intent.setData(Uri.parse("kakaostory://story/channel/@wenoun"));
                intent.setData(Uri.parse("http://story.kakao.com/ch/wenoun/app"));
//                intent.setData(Uri.parse("kakaostory://profiles/channels/@wenoun"));

                startActivity(intent);
            }
        });
        facebookView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String facebookID="1521326674797347";
                try
                {
                    Uri uri = Uri.parse( "fb://page/" + facebookID );
                    startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
                }
                catch ( Exception e )
                {
                    Intent i = new Intent( Intent.ACTION_VIEW );
                    Uri u = Uri.parse( "http://www.facebook.com/" + facebookID );
                    i.setData( u );
                    startActivity( i );
                }
            }
        });
        marketView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://jey-dev.com/jey/store.php")));
            }
        });
        setButtonSize();
    }
    public void setScaleType(int scaleType){
        this.scaleType=scaleType;
        setButtonSize();
    }
    private void setButtonSize(){
        final int margins= JImageUtils.dpToPx(getContext(),10);
//        final int mini= ImageUtils.dpToPx(getContext(),45);
//        final int normal= ImageUtils.dpToPx(getContext(),60);
//        final int big= ImageUtils.dpToPx(getContext(),80);
//        int size=normal;
//        switch(scaleType){
//            case SCALE_NORMAL:
//                size=normal;
//                break;
//            case SCALE_MINI:
//                size=mini;
//                break;
//            case SCALE_BIG:
//                size=big;
//                break;
//        }
//        LayoutParams params=new LayoutParams(size,size);
//        params.setMargins(margins,margins,margins,margins);
        facebookView.setScaleType(scaleType);
//        facebookView.setLayoutParams(params);
//        facebookView.setPadding(margins,margins,margins,margins);
        kakaoView.setScaleType(scaleType);
//        kakaoView.setLayoutParams(params);
//        kakaoView.setPadding(margins,margins,margins,margins);
        webView.setScaleType(scaleType);
//        webView.setLayoutParams(params);
//        webView.setPadding(margins,margins,margins,margins);
        marketView.setScaleType(scaleType);
//        marketView.setLayoutParams(params);
//        marketView.setPadding(margins,margins,margins,margins);
    }
    private void startActivity(Intent intent){
        getContext().startActivity(intent);
    }
    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.JeYSnsView);

        setTypeArray(typedArray);
    }


    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.JeYSnsView, defStyle, 0);
        setTypeArray(typedArray);

    }
    private void getAttrs(AttributeSet attrs, int defStyle, int defStyleRes) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.JeYSnsView, defStyle, defStyleRes);
        setTypeArray(typedArray);

    }


    private void setTypeArray(TypedArray typedArray) {
        scaleType = typedArray.getInt(R.styleable.JeYSnsView_scaleType, SCALE_NORMAL);
        typedArray.recycle();

    }
}
