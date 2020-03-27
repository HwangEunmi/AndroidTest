package com.communication.servercommunication.activities;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.communication.servercommunication.R;
import com.communication.servercommunication.adapter.ViewPagerAdapter;
import com.communication.servercommunication.common.CustomViewData;
import com.communication.servercommunication.interfaces.IViewPagerData;
import com.communication.servercommunication.view.CView;
import com.communication.servercommunication.view.SOSActionbar;

public class ViewPagerActivity extends Activity {

    private ViewPager mPager;

    private ViewPagerAdapter mAdapter;

    private SOSActionbar mActionbar;

    private LinearLayout mLLIvTab;

    private CustomViewData mCustomViewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        mPager = (ViewPager) findViewById(R.id.pager);
        mActionbar = (SOSActionbar) findViewById(R.id.view_actionbar);

        mCustomViewData = new CustomViewData();
        mAdapter = new ViewPagerAdapter(ViewPagerActivity.this, mCustomViewData, new IViewPagerData() {

            @Override
            public void onCustomViewData(CustomViewData customViewData) {
                mCustomViewData = customViewData;

                Toast.makeText(ViewPagerActivity.this,
                        "boardSEQ: " + mCustomViewData.getmItem().getmAnsim_info_seq(),
                        Toast.LENGTH_SHORT)
                        .show();

                CView cView = new CView(ViewPagerActivity.this, mCustomViewData, null);
                cView.init();
                cView.setData(mCustomViewData);
                cView.setDataContent();

                mPager.setCurrentItem(2);
//                CView cView = new CView(ViewPagerActivity.this, mCustomViewData, new IShortKeyData() {
//                    @Override
//                    public void onShortKeyData(CustomViewData customViewData) {
//
//                        Toast.makeText(ViewPagerActivity.this, "Toast", Toast.LENGTH_SHORT).show();
//
//                        Intent urlIntent = new Intent(Intent.ACTION_VIEW,
//                                Uri.parse("https://sos.openit.co.kr/"
//                                        + Integer.parseInt(mCustomViewData.getmItem().getmShort_key())
//                                        + "/m/"));
//
//                        startActivity(urlIntent);
//                    }
//                });
////                cView.setData(customViewData);
////                cView.init();
//                cView.setDataContent();

            }

        });

        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(0); // 첫번째 페이지로 초기화
        mPager.setOffscreenPageLimit(1); // 양쪽에 유지되는 페이지수 설정

        ImageView ivOne = (ImageView) findViewById(R.id.iv_one);
        ImageView ivTwo = (ImageView) findViewById(R.id.iv_two);
        ImageView ivThree = (ImageView) findViewById(R.id.iv_three);
        mLLIvTab = (LinearLayout) findViewById(R.id.ll_iv_tab);

        ivOne.setOnClickListener(movePageListener);
        ivOne.setTag(0); // 첫번째화면 호출
        ivTwo.setOnClickListener(movePageListener);
        ivTwo.setTag(1); // 두번째화면 호출
        ivThree.setOnClickListener(movePageListener);
        ivThree.setTag(2); // 세번째화면 호출

        ivOne.setSelected(true); // 앱 처음 실행때, 첫번째 탭이 선택되어있어야하니까

        /* 현재 뷰가 탭의 Tag와 같으면, selected표시 */
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int i = 0;
                while (i < 3) {
                    if (position == i) {
                        // findViewWithTag : Tag로 뷰를 찾음 (단, Object.equals() 비교를 함)
                        mLLIvTab.findViewWithTag(i).setSelected(true);

                    } else {
                        mLLIvTab.findViewWithTag(i).setSelected(false);
                    }

                    i++;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    View.OnClickListener movePageListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int currentTag = (int) v.getTag();

            int i = 0;
            while (i < 3) {
                if (currentTag == i) {
                    mLLIvTab.findViewWithTag(i).setSelected(true);

                } else {
                    mLLIvTab.findViewWithTag(i).setSelected(false);
                }

                i++;
            }
            mPager.setCurrentItem(currentTag);
        }
    };
}
