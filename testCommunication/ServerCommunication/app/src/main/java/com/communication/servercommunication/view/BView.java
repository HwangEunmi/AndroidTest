package com.communication.servercommunication.view;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.communication.servercommunication.R;
import com.communication.servercommunication.adapter.SOSAdapter;
import com.communication.servercommunication.common.CustomViewData;
import com.communication.servercommunication.common.DBManager;
import com.communication.servercommunication.interfaces.IViewPagerData;
import com.communication.servercommunication.model.DBData;

import java.util.ArrayList;
import java.util.List;

public class BView extends LinearLayout implements View.OnClickListener {

    private SOSActionbar mActionbar;

    private ListView mListView;

    /* DB를 위한 툴바 */
    private LinearLayout mLLDBMode;

    /* DB 삭제모드 툴바 */
    private LinearLayout mLLDBDeleteMode;

    /* DB 조회 버튼 */
    private TextView mTvDBSearch;

    /* DB 삭제모드 전환 버튼 */
    private TextView mTvDBDeleteMode;

    /* DB 삭제버튼 */
    private TextView mDBDeleteText;

    /* DB 삭제모드 취소버튼 */
    private LinearLayout mLLCancel;

    /* 체크박스 전체 선택 뷰 */
    private ImageView mIvAllClick;

    /* SOSListData용 어댑터 */
    private SOSAdapter mSosAdapter;

    private DBManager mDBManager;

    private LayoutInflater mInflater;

    private Context mContext;

    private CustomViewData mCustomViewData;

    private IViewPagerData mIViewPagerData;

    public BView(Context context, IViewPagerData mIViewPagerData) {
        super(context);
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        mCustomViewData = new CustomViewData();
        this.mIViewPagerData = mIViewPagerData;

        init();
    }

    private void init() {
        inflate(getContext(), R.layout.activity_b, this);
        mDBManager = new DBManager(mContext);
        Log.d("TEST", "onCreate B");
        /* 액션바 셋팅 */
        mActionbar = (SOSActionbar) findViewById(R.id.view_actionbar);
        mActionbar.setActionbarTitle("B Activity");
        mActionbar.setNaviBack(mContext, View.VISIBLE);

        mDBDeleteText = (TextView) findViewById(R.id.tv_db_delete);
        mIvAllClick = (ImageView) findViewById(R.id.iv_all_select);
        mLLDBMode = (LinearLayout) findViewById(R.id.ll_db_toolbar);
        mTvDBSearch = (TextView) findViewById(R.id.tv_db_search);
        mTvDBDeleteMode = (TextView) findViewById(R.id.tv_db_mode);
        mLLDBDeleteMode = (LinearLayout) findViewById(R.id.ll_cv_mode);
        mLLCancel = (LinearLayout) findViewById(R.id.ll_db_mode_cancel);

        mListView = (ListView) findViewById(R.id.lv_sos);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mSosAdapter = new SOSAdapter();
        mListView.setAdapter(mSosAdapter);
        mLLDBMode.setVisibility(View.VISIBLE);

        mTvDBDeleteMode.setOnClickListener(this);
        mIvAllClick.setOnClickListener(this);
        mDBDeleteText.setOnClickListener(this);
        mTvDBSearch.setOnClickListener(this);

        mTvDBSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSosAdapter.setIsListView();
                setSearchList();
            }
        });

        /* 클릭리스너 */
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mIViewPagerData != null && mSosAdapter.getDeleteFlag() == false) {
                    DBData data = (DBData) mListView.getItemAtPosition(position);
                    CustomViewData viewData = new CustomViewData();
                    viewData.setmItem(data);
                    mIViewPagerData.onCustomViewData(viewData);
                    mIvAllClick.setSelected(false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // (리스트)뷰
            case R.id.lv_sos:
                mIvAllClick.setSelected(false);
                break;

            // DB 모드로 전환
            case R.id.tv_db_mode:
                List<DBData> data = mDBManager.getSOSListData();

                if (mListView.getCount() != 0) {
                    if (data != null && data.size() != 0) {
                        mLLDBMode.setVisibility(View.GONE);
                        mLLDBDeleteMode.setVisibility(View.VISIBLE);
                        mSosAdapter.setIsDeleteView();

                        // 체크 상태 초기화
                        for (int i = 0; i < mSosAdapter.getCount(); i++) {
                            mListView.setItemChecked(i, false);
                        }

                        /* 취소버튼 클릭시 화면 나가기 */
                        mLLCancel.setVisibility(View.VISIBLE);
                        mLLCancel.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                mSosAdapter.setIsListView();
                                mLLDBDeleteMode.setVisibility(View.GONE);
                                mLLDBMode.setVisibility(View.VISIBLE);

                                mLLCancel.setVisibility(View.GONE);
                            }
                        });
                    } else {
                        Toast.makeText(mContext, "데이터가 존재하지 않습니다", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "먼저 조회를 해주세요", Toast.LENGTH_SHORT).show();
                }
                break;

            // (전체 선택을 위한) 체크박스 이미지뷰
            case R.id.iv_all_select:
                if (mIvAllClick.isSelected()) {
                    mIvAllClick.setSelected(false);

                } else {
                    mIvAllClick.setSelected(true);
                }

                for (int i = 0; i < mSosAdapter.getCount(); i++) {
                    mListView.setItemChecked(i, mIvAllClick.isSelected());
                }
                break;

            // DB 삭제 버튼
            case R.id.tv_db_delete:
                if (mListView.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE) {
                    SparseBooleanArray array = mListView.getCheckedItemPositions();
                    List<DBData> removeList = new ArrayList<DBData>();

                    int count = 0;

                    for (int i = 0; i < array.size(); i++) {
                        int position = array.keyAt(i);

                        if (array.get(position)) {
                            Log.d("TEST", "DF: " + array.get(position));
                            DBData dbData = (DBData) mListView.getItemAtPosition(position);

                            /* DB에서 삭제 */
                            mDBManager.deleteSOSData(dbData);

                            removeList.add(dbData);

                            count++;
                        }
                    }

                    for (DBData d : removeList) {
                        mSosAdapter.remove(d);
                    }

                    mListView.clearChoices();

                    /* DB삭제 클릭시 체크박스에 선택된 아이템이 아무것도 없을때 */
                    if (count == 0) {
                        mSosAdapter.setIsDeleteView();
                    }
                }
                break;

            // DB 내용 조회 버튼
            case R.id.tv_db_search:
                setSearchList();

                break;
        }
    }

    public void setSearchList() {

        List<DBData> listData = mDBManager.getSOSListData();

        if (listData != null && listData.size() != 0) {
            mSosAdapter.clear();
            mSosAdapter.addAll(listData);
            mListView.smoothScrollToPosition(0);

            Toast.makeText(mContext, listData.size() + " 개가 조회되었습니다", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(mContext, "데이터가 존재하지 않습니다", Toast.LENGTH_SHORT).show();
        }
    }

    /*리스너*/
    public void setBIViewPagerData(IViewPagerData listener) {
        mIViewPagerData = listener;
    }
}
