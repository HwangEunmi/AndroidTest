package com.communication.servercommunication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.communication.servercommunication.R;
import com.communication.servercommunication.model.DBData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 은미 on 2017-01-19.
 */

public class SOSAdapter extends BaseAdapter{

    private List<DBData> mItems = new ArrayList<>();

    /*삭제/리스트뷰 구별하는 플래그*/
    private boolean mIsDeleteView;

    /*삭제화면 on*/
    public void setIsDeleteView() {
        mIsDeleteView = true;
        notifyDataSetChanged();
    }

    /*삭제화면 off(리스트 화면)*/
    public void setIsListView() {
        mIsDeleteView = false;
        notifyDataSetChanged();
    }

    public boolean getDeleteFlag() {
        return mIsDeleteView;
    }

    /*모델들을 추가하는 메소드*/
    public void add(DBData data) {
        mItems.add(data);
        notifyDataSetChanged();
    }

    /*리스트를 추가하는 메소드*/
    public void addAll(List<DBData> items) {
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    /*모델을 삭제하는 메소드*/
    public void remove(DBData data) {
        mItems.remove(data);
        notifyDataSetChanged();
    }

    /*리스트를 초기화하는 메소드*/
    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final SOSViewHolder view;

        if (convertView == null) {
            view = new SOSViewHolder(parent.getContext());
            convertView = view;

        } else {
            view = (SOSViewHolder) convertView;
        }

        /*뷰에 모델의 값 셋팅*/
        view.setSOSListData(mItems.get(position), mIsDeleteView);
        /*클릭리스너를 위한*/
        view.setTag(mItems.get(position));

        return view;
    }

    /*뷰홀더*/
    class SOSViewHolder extends FrameLayout implements Checkable {

        private LinearLayout mLLViewLayout;

        private TextView mTvTitle;

        private TextView mTvContent;

        private ImageView mIvDelete;

        private DBData mSosListData;

        /*체크박스가 체크되었는지/아닌지에 대한 플래그*/
        private boolean mIsChecked;

        public SOSViewHolder(Context context) {
            super(context);

            init();
        }

        /*뷰 초기화*/
        private void init() {
            inflate(getContext(), R.layout.view_sos, this);

            mLLViewLayout = (LinearLayout)findViewById(R.id.ll_sos);
            mTvTitle = (TextView) findViewById(R.id.tv_title);
            mTvContent = (TextView) findViewById(R.id.tv_content);
            mIvDelete = (ImageView) findViewById(R.id.iv_delete_sos);
        }

        /*각각 뷰에 데이터값 셋팅*/
        public void setSOSListData(DBData data, boolean isDeleteView) {
            mSosListData = data;

            mTvTitle.setText(data.mTitle);
            mTvContent.setText(data.mContent);

        /*현재뷰가 삭제뷰 일때*/
            if (isDeleteView) {
                /*체크박스 보임*/
                mIvDelete.setVisibility(View.VISIBLE);
            } else {
                mIvDelete.setVisibility(View.GONE);
            }
        }

        @Override
        public void setChecked(boolean checked) {
            if (mIsChecked != checked) {
                mIsChecked = checked;
                drawCheck();
            }
        }

        /*체크on/off 이미지 셋팅 메소드*/
        private void drawCheck() {
            if (mIsChecked) {
                mIvDelete.setBackgroundResource(android.R.color.transparent);
                mIvDelete.setImageResource(android.R.drawable.checkbox_on_background);
            } else {

                mIvDelete.setBackgroundResource(android.R.color.transparent);
                mIvDelete.setImageResource(android.R.drawable.checkbox_off_background);
            }
        }

        @Override
        public boolean isChecked() {
            return mIsChecked;
        }

        @Override
        public void toggle() {
            setChecked(!mIsChecked);
        }
    }
}


