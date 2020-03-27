package com.communication.servercommunication.common;

import com.communication.servercommunication.model.DBData;

/**
 * Created by hwangem on 2017-02-23.
 */

// 모든 뷰들이 공통으로 사용하는 것들을 하나의 클래스에 모아서 따로 빼기
public class CustomViewData {

    public String mViewType;

    public DBData mItem;

    public boolean mIsClick;

    public String getmViewType() {
        return mViewType;
    }

    public void setmViewType(String mViewType) {
        this.mViewType = mViewType;
    }

    public DBData getmItem() {
        return mItem;
    }

    public void setmItem(DBData mItem) {
        this.mItem = mItem;
    }
}
