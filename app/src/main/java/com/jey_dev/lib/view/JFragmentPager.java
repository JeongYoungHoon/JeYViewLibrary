package com.jey_dev.lib.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JeyHoon on 16. 7. 4..
 */
public class JFragmentPager extends RelativeLayout {
    private int dotScaleType = JPagerIndicatorView.SCALE_NORMAL;
    private int dotColor= Color.BLACK;
    private int dotCount=2;
    private boolean isStack=false;
    private boolean isTop=false;
    private boolean isUncheckWhite=false;
    private Context ctx=null;
    private JPagerIndicatorView pagerIndicatorView =null;
    private ViewPager viewPager=null;
    private JFragmentPagerAdapter viewPagerAdapter=null;
    private View root=null;
    private final ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(null!=pagerIndicatorView) {
                if (positionOffset > 0.5) {
                    pagerIndicatorView.setSelectPosition(position+1);
                }else{
                    pagerIndicatorView.setSelectPosition(position);
                }
            }
//            Log.d("Test,onPageScrolled","position : "+position+" positionOffset : "+positionOffset+" positionOffsetPixels : "+positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            if(null!=pagerIndicatorView){
//                pagerIndicatorView.setSelectPosition(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public JFragmentPager(Context context) {
        super(context);
        ctx=context;
        initView();
    }

    public JFragmentPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(attrs);
        ctx=context;
        initView();
    }

    public JFragmentPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(attrs,defStyleAttr);
        ctx=context;
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public JFragmentPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttrs(attrs,defStyleAttr,defStyleRes);
        ctx=context;
        initView();
    }

    public void initView(){
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(
                infService);
        root = li.inflate(R.layout.view_jviewpager, this, false);
        addView(root);
        pagerIndicatorView =(JPagerIndicatorView)root.findViewById(R.id.jviewpager_indicator);
        viewPager=(ViewPager)root.findViewById(R.id.jviewpager_viewpager);
        pagerIndicatorView.setAttrs(dotScaleType,dotColor,dotCount,isStack,isUncheckWhite);
        LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        if(isTop){
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        }else{
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        }
        final int margin= JImageUtils.dpToPx(ctx,10);
        params.setMargins(margin,margin,margin,margin);
        pagerIndicatorView.setLayoutParams(params);
        viewPager.addOnPageChangeListener(onPageChangeListener);
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
        dotScaleType = typedArray.getInt(R.styleable.JPagerIndicatorView_scaleType, JPagerIndicatorView.SCALE_NORMAL);
        dotCount = typedArray.getInt(R.styleable.JPagerIndicatorView_dotCount,2);
        dotColor = typedArray.getInt(R.styleable.JPagerIndicatorView_dotColor, Color.BLACK);
        isTop = typedArray.getInt(R.styleable.JPagerIndicatorView_indicatorPosition,0)==0;
        isUncheckWhite=typedArray.getBoolean(R.styleable.JPagerIndicatorView_uncheckWhite,false);
        typedArray.recycle();

    }
    public ViewPager getViewPager(){
        return viewPager;
    }
    public void setAdapter(JFragmentPagerAdapter Adapter){
        viewPager.setAdapter(Adapter);
        viewPagerAdapter=Adapter;
        pagerIndicatorView.setDotCount(Adapter.getCount());
    }
    public void addViewInViewPager(View child, int index, ViewGroup.LayoutParams params){
        viewPager.addView(child, index, params);
    }
    public void addOnpageChangeListener(ViewPager.OnPageChangeListener listener){
        viewPager.addOnPageChangeListener(listener);
    }
    public PagerAdapter getAdapter(){
        return viewPager.getAdapter();
    }
    public JFragmentPagerAdapter getViewPagerAdapter(){
        return viewPagerAdapter;
    }
    public int getCurrentItem() {
        return viewPager.getCurrentItem();
    }
    public JPagerIndicatorView getPagerIndicator(){
        return pagerIndicatorView;
    }
    public static class JFragmentPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public JFragmentPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
//            return null;
        }

    }

}
