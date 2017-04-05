package com.jey_dev.lib.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
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
public class JViewPager extends RelativeLayout {
    private int dotScaleType = JPagerIndicatorView.SCALE_NORMAL;
    private int dotColor = Color.BLACK;
    private int dotCount = 2;
    private boolean isStack = false;
    private boolean isTop = false;
    private boolean isUncheckWhite=false;
    private Context ctx = null;
    private JPagerIndicatorView pagerIndicatorView = null;
    private ViewPager viewPager = null;
    private JViewPagerAdapter viewPagerAdapter = null;
    private View root = null;

    private final ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (null != pagerIndicatorView) {
                if (positionOffset > 0.5) {
                    pagerIndicatorView.setSelectPosition(position + 1);
                } else {
                    pagerIndicatorView.setSelectPosition(position);
                }
            }
//            Log.d("Test,onPageScrolled","position : "+position+" positionOffset : "+positionOffset+" positionOffsetPixels : "+positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            if (null != pagerIndicatorView) {
//                pagerIndicatorView.setSelectPosition(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public JViewPager(Context context) {
        super(context);
        ctx = context;
        initView();
    }

    public JViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(attrs);
        ctx = context;
        initView();
    }

    public JViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(attrs, defStyleAttr);
        ctx = context;
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public JViewPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttrs(attrs, defStyleAttr, defStyleRes);
        ctx = context;
        initView();
    }

    public void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(
                infService);
        root = li.inflate(R.layout.view_jviewpager, this, false);
        addView(root);
        pagerIndicatorView = (JPagerIndicatorView) root.findViewById(R.id.jviewpager_indicator);
        viewPager = (ViewPager) root.findViewById(R.id.jviewpager_viewpager);
        pagerIndicatorView.setAttrs(dotScaleType, dotColor, dotCount, isStack,isUncheckWhite);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        if (isTop) {
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        } else {
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        }
        final int margin = JImageUtils.dpToPx(ctx, 10);
        params.setMargins(margin, margin, margin, margin);
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
        isStack = typedArray.getBoolean(R.styleable.JPagerIndicatorView_isStack, false);
        dotScaleType = typedArray.getInt(R.styleable.JPagerIndicatorView_scaleType, JPagerIndicatorView.SCALE_NORMAL);
        dotCount = typedArray.getInt(R.styleable.JPagerIndicatorView_dotCount, 2);
        dotColor = typedArray.getInt(R.styleable.JPagerIndicatorView_dotColor, Color.BLACK);
        isTop = typedArray.getInt(R.styleable.JPagerIndicatorView_indicatorPosition, 0) == 0;
        isUncheckWhite=typedArray.getBoolean(R.styleable.JPagerIndicatorView_uncheckWhite,false);
        typedArray.recycle();

    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setAdapter(JViewPagerAdapter Adapter) {
        viewPager.setAdapter(Adapter);
        viewPagerAdapter = Adapter;
        pagerIndicatorView.setDotCount(Adapter.getCount());
    }

    public void addViewInViewPager(View child, int index, ViewGroup.LayoutParams params) {
        viewPager.addView(child, index, params);
    }

    public void addOnpageChangeListener(ViewPager.OnPageChangeListener listener) {
        viewPager.addOnPageChangeListener(listener);
    }

    public PagerAdapter getAdapter() {
        return viewPager.getAdapter();
    }

    public JViewPagerAdapter getViewPagerAdapter() {
        return viewPagerAdapter;
    }

    public int getCurrentItem() {
        return viewPager.getCurrentItem();
    }

    public void setCurrentItem(int position){
        viewPager.setCurrentItem(position);
    }

    public JPagerIndicatorView getPagerIndicator() {
        return pagerIndicatorView;
    }

    public static class JViewPagerAdapter extends PagerAdapter {
        private final List<View> viewList = new ArrayList<>();
        private OnChildViewClickListener listener=new OnChildViewClickListener() {
            @Override
            public void onChildClick(View v, int position) {

            }
        };
        public void setOnChildViewClickListener(OnChildViewClickListener listener){
            this.listener=listener;
        }
//        private final List<String> mFragmentTitleList = new ArrayList<>();

        //        public JViewPagerAdapter(FragmentManager manager) {
//            super(manager);
//        }
        public View getItem(int position) {
            return viewList.get(position);
        }

        @Override
        public int getCount() {
            return viewList.size();
        }


        /**
         * Determines whether a page View is associated with a specific key object
         * as returned by {@link #instantiateItem(ViewGroup, int)}. This method is
         * required for a PagerAdapter to function properly.
         *
         * @param view   Page View to check for association with <code>object</code>
         * @param object Object to check for association with <code>view</code>
         * @return true if <code>view</code> is associated with the key object <code>object</code>
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void addView(View view) {
            viewList.add(view);
        }
        @Override
        public Object instantiateItem(View pager, final int position) {
            View v = viewList.get(position);
//            if (position == 0) {
//                v = mInflater.inflate(R.layout.inflate_one, null);
//                v.findViewById(R.id.iv_one);
//                v.findViewById(R.id.btn_click).setOnClickListener(mPagerListener);
//            } else if (position == 1) {
//                v = mInflater.inflate(R.layout.inflate_two, null);
//                v.findViewById(R.id.iv_two);
//                v.findViewById(R.id.btn_click_2).setOnClickListener(mPagerListener);
//            } else {
//                v = mInflater.inflate(R.layout.inflate_three, null);
//                v.findViewById(R.id.iv_three);
//                v.findViewById(R.id.btn_click_3).setOnClickListener(mPagerListener);
//            }
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onChildClick(v,position);
                }
            });
            ((ViewPager) pager).addView(v, 0);

            return v;
        }
        @Override
        public void destroyItem(View pager, int position, Object view) {
            ((ViewPager) pager).removeView((View) view);
        }


    }
//
//    private class PagerAdapterClass extends PagerAdapter {
//
//        private LayoutInflater mInflater;
//
//        public PagerAdapterClass(Context c) {
//            super();
//            mInflater = LayoutInflater.from(c);
//        }
//
//        @Override
//        public int getCount() {
//            return 3;
//        }
//
//        /**
//         * Determines whether a page View is associated with a specific key object
//         * as returned by {@link #instantiateItem(ViewGroup, int)}. This method is
//         * required for a PagerAdapter to function properly.
//         *
//         * @param view   Page View to check for association with <code>object</code>
//         * @param object Object to check for association with <code>view</code>
//         * @return true if <code>view</code> is associated with the key object <code>object</code>
//         */
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return false;
//        }
////
////        @Override
////        public Object instantiateItem(View pager, int position) {
////            View v = null;
////            if (position == 0) {
////                v = mInflater.inflate(R.layout.inflate_one, null);
////                v.findViewById(R.id.iv_one);
////                v.findViewById(R.id.btn_click).setOnClickListener(mPagerListener);
////            } else if (position == 1) {
////                v = mInflater.inflate(R.layout.inflate_two, null);
////                v.findViewById(R.id.iv_two);
////                v.findViewById(R.id.btn_click_2).setOnClickListener(mPagerListener);
////            } else {
////                v = mInflater.inflate(R.layout.inflate_three, null);
////                v.findViewById(R.id.iv_three);
////                v.findViewById(R.id.btn_click_3).setOnClickListener(mPagerListener);
////            }
////
////            ((ViewPager) pager).addView(v, 0);
////
////            return v;
////        }
////
////        @Override
////        public void destroyItem(View pager, int position, Object view) {
////            ((ViewPager) pager).removeView((View) view);
////        }
////
////        @Override
////        public boolean isViewFromObject(View pager, Object obj) {
////            return pager == obj;
////        }
//
//        @Override
//        public void restoreState(Parcelable arg0, ClassLoader arg1) {
//        }
//
//        @Override
//        public Parcelable saveState() {
//            return null;
//        }
////
////        @Override
////        public void startUpdate(View arg0) {
////        }
////
////        @Override
////        public void finishUpdate(View arg0) {
////        }
//    }

    public interface OnChildViewClickListener {
        void onChildClick(View v, int position);
    }

}
