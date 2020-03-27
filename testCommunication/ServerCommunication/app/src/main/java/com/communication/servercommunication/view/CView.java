package com.communication.servercommunication.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.communication.servercommunication.R;
import com.communication.servercommunication.common.Constant;
import com.communication.servercommunication.common.CustomViewData;
import com.communication.servercommunication.common.HttpMultiProtocol;
import com.communication.servercommunication.common.Utils;
import com.communication.servercommunication.interfaces.IShortKeyData;
import com.communication.servercommunication.model.BaseData;
import com.communication.servercommunication.model.SOSContentData;

public class CView extends LinearLayout {

    private SOSActionbar mActionbar;

    /* 제목 */
    private TextView mTvTitle;

    /* 내용 */
    private TextView mTvContent;

    /* 바로가기 */
    private TextView mTvShortKey;

    /* 이미지1 */
    private ImageView mIvOneURL;

    /* 이미지2 */
    private ImageView mIvTwoURL;

    /* 이미지3 */
    private ImageView mIvThreeURL;

    /* boardSeq를 담는 변수 */
    private int mBoardSeq;

    /* shortKey를 담는 변수 */
    private String mShortKey;

    private LayoutInflater mInflater;

    private IShortKeyData mShortKeyData;

    private CustomViewData mCustomViewData;

    private Context mContext;

    public CView(Context context, IShortKeyData shortKeyData) {
        super(context);
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
        mShortKeyData = shortKeyData;

        init();
    }

    public CView(Context context, CustomViewData customViewData, IShortKeyData shortKeyData) {
        super(context);
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        mCustomViewData = customViewData;
        mShortKeyData = shortKeyData;

        init();
    }

    public void init() {
        inflate(getContext(), R.layout.activity_c, this);

        /* 액션바 셋팅 */
        mActionbar = (SOSActionbar) findViewById(R.id.view_actionbar);
        mActionbar.setActionbarTitle("C Activity");
        mActionbar.setNaviBack(mContext, View.VISIBLE);
        Log.d("TEST", "onCreate C");

        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mIvOneURL = (ImageView) findViewById(R.id.iv_one_url);
        mIvTwoURL = (ImageView) findViewById(R.id.iv_two_url);
        mIvThreeURL = (ImageView) findViewById(R.id.iv_three_url);
        mTvShortKey = (TextView) findViewById(R.id.tv_short_key);
        mTvShortKey.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//                Log.d("TEST", "final : "+mCustomViewData.getmItem().getmAnsim_info_seq());
                if (mShortKeyData != null && mCustomViewData.getmItem() != null) {
                    mShortKeyData.onShortKeyData(mCustomViewData);
                }
            }
        });
//            setDataContent();

//        if (mCustomViewData != null && mCustomViewData.getmItem() != null) {
//            Log.d("TEST", "ansimInfo: " + mCustomViewData.getmItem().getmAnsim_info_seq());
//            setDataContent();
//        }
    }

    /* 서버로부터 데이터를 가져오는 메소드 */
    public void setDataContent() {
//        Log.d("TEST", "NUM: "+mCustomViewData.getmItem().getmAnsim_info_seq());
        SOSContentData contentData =
                new SOSContentData(Constant.METHOD_POST,
                        Constant.GET_BOARD_CONTENT_URL,
                        mCustomViewData.getmItem().getmAnsim_info_seq());
//                                                        111);
        HttpMultiProtocol protocol = new HttpMultiProtocol(new Utils.NetworkCheckCallback() {

            @Override
            public BaseData onSuccess(BaseData data) {
                if (data != null) {
                    SOSContentData.SOSContentRequest request = (SOSContentData.SOSContentRequest) data.getmDataClass();

                    if ("true".equals(request.mIsSuccess)) { // 성공한 경우
                        Log.d("TEST", String.valueOf(request.mMessage));
                        Toast.makeText(mContext, request.mTitle, Toast.LENGTH_SHORT).show();
                        mTvTitle.setText(request.mTitle);
                        mTvContent.setText(request.mContent);

                        Log.d("TEST", "imagePath: " + request.mImgPath0);
                        Log.d("TEST", "url: " + Constant.DEFAULT_SOS_URL + request.mImgPath0);

                        if (request.mImgPath0 != null) {
                            mIvOneURL.setVisibility(View.VISIBLE);

                            /* ImageView 비우기 */
                            mIvOneURL.setImageDrawable(null);

                            Glide.with(mContext).load(Constant.DEFAULT_SOS_URL + request.mImgPath0).into(mIvOneURL);
                        } else {
                            return null;
                        }

                        if (request.mImgPath1 != null) {
                            mIvTwoURL.setVisibility(View.VISIBLE);

                            /* ImageView 비우기 */
                            mIvTwoURL.setImageDrawable(null);

                            Glide.with(mContext).load(Constant.DEFAULT_SOS_URL + request.mImgPath1).into(mIvTwoURL);
                        } else {
                            return null;
                        }

                        if (request.mImgPath2 != null) {
                            mIvThreeURL.setVisibility(View.VISIBLE);

                            /* ImageView 비우기 */
                            mIvThreeURL.setImageDrawable(null);

                            Glide.with(mContext).load(Constant.DEFAULT_SOS_URL + request.mImgPath2).into(mIvThreeURL);
                        } else {
                            return null;
                        }

                        /* 바로가기를 위한 shortKey를 받아 변수에 저장 */
                        if (request.mShortKey != null && !request.mShortKey.equals("")) {
                            mShortKey = request.mShortKey;
                        }
                    }
                }

                return data;
            }

            @Override
            public BaseData onFail(BaseData data) {
                if (data != null) {
                    SOSContentData.SOSContentRequest request = (SOSContentData.SOSContentRequest) data.getmDataClass();
                    if ("false".equals(request.mIsSuccess)) { // 실패한 경우
                        if (request != null) {
                            // 데이터 불러오기 실패
                        } else {
                            Toast.makeText(mContext, "불러올 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                            return data;
                        }
                    }
                }
                return data;
            }
        });

        protocol.execute(contentData);
    }

    /*리스너*/
    public void setCIViewPagerData(IShortKeyData listener) {
        mShortKeyData = listener;
    }

    public void setData(CustomViewData customViewData) {
        mCustomViewData = customViewData;
        Log.d("TEST", "customData: " + mCustomViewData);
    }
}
