package com.communication.servercommunication.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.communication.servercommunication.common.CustomViewData;
import com.communication.servercommunication.interfaces.IViewPagerData;
import com.communication.servercommunication.view.AView;
import com.communication.servercommunication.view.BView;
import com.communication.servercommunication.view.CView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 은미 on 2017-02-09.
 */

/* 리스너를 생성자에 넘기면, 뷰홀더에서 그 생성자를 클릭리스너처럼 사용했을때 액티비티에서 그 리스너를 다 모아서 액션을 취할 수 있음 */
public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;

    private CustomViewData mCustomViewData;

    private IViewPagerData mIViewPager;

    private List<LinearLayout> mItems;

    public ViewPagerAdapter(Context context, CustomViewData mCustomViewData, IViewPagerData mIViewPager) {
        this.mContext = context;
        this.mCustomViewData = mCustomViewData;
        this.mIViewPager = mIViewPager;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        mItems = new ArrayList<>();

        AView aView = null;
        BView bView = null;
        CView cView = null;

        if (aView != null || bView != null || cView != null) {
            if (aView != null) {
                container.addView(aView);
            }
            if (bView != null) {
                container.addView(bView);
            }
            if (cView != null) {
                container.addView(cView);
            }

        } else {
            if (aView == null || bView == null || cView == null) {
                if (aView == null) {
                    aView = new AView(mContext);
                    container.addView(aView);
                }

                if (bView == null) {
                    bView = new BView(mContext, mIViewPager);
                    bView.setBIViewPagerData(mIViewPager);
                    container.addView(bView);

                }
                if (cView == null) {
                    cView = new CView(mContext, null);
                    cView.setCIViewPagerData(null);
                    container.addView(cView);
                }
            }
        }

        mItems.add(aView);
        mItems.add(bView);
        mItems.add(cView);

        return mItems.get(position);
    }

    public void addView(LinearLayout view, int index) {
        mItems.add(index, view);
        notifyDataSetChanged();
    }

    public void removeView(int index) {
        mItems.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (position == 0) {
            AView view = (AView) object;
            container.removeView(view);

        } else if (position == 1) {
            BView view = (BView) object;
            container.removeView(view);

        } else {
            CView view = (CView) object;
            container.removeView(view);
        }
    }

    @Override
    public int getCount() {
        return 3; // List<>사이즈만큼
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public float getPageWidth(int position) {
        return 1;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
