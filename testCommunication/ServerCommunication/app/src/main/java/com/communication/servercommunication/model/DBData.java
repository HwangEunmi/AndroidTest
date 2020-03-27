package com.communication.servercommunication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hwangem on 2017-01-31.
 */

/*이 데이터클래스 따로뺌
        * inner클래스는 하나의 경우에만 쓰는건데(네트워크같은)
        * 이건 db도 같이 쓰려고하니까 에러남
        * db용 데이터클래스겸용으로 하나 따로 만듬*/
public class DBData {

   public int mSosSEQ;

    @SerializedName("CATEGORY")
    public String mCategory;

    @SerializedName("ANSIM_INFO_SEQ")
    public int mAnsim_info_seq;

    @SerializedName("SHORT_KEY")
    public String mShort_key;

    @SerializedName("CONTENT")
    public String mContent;

    @SerializedName("DETAIL_CONTENT")
    public String mDetail_content;

    @SerializedName("ANSIM_DETAIL_SEQ")
    public int mAnsim_detail_seq;

    @SerializedName("TYPE")
    public String mType;

    @SerializedName("TITLE")
    public String mTitle;

    public int getmSosSEQ() {
        return mSosSEQ;
    }

    public void setmSosSEQ(int mSosSEQ) {
        this.mSosSEQ = mSosSEQ;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public int getmAnsim_info_seq() {
        return mAnsim_info_seq;
    }

    public void setmAnsim_info_seq(int mAnsim_info_seq) {
        this.mAnsim_info_seq = mAnsim_info_seq;
    }

    public String getmShort_key() {
        return mShort_key;
    }

    public void setmShort_key(String mShort_key) {
        this.mShort_key = mShort_key;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmDetail_content() {
        return mDetail_content;
    }

    public void setmDetail_content(String mDetail_content) {
        this.mDetail_content = mDetail_content;
    }

    public int getmAnsim_detail_seq() {
        return mAnsim_detail_seq;
    }

    public void setmAnsim_detail_seq(int mAnsim_detail_seq) {
        this.mAnsim_detail_seq = mAnsim_detail_seq;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}