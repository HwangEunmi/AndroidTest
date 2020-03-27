package com.communication.servercommunication.view;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.communication.servercommunication.R;
import com.communication.servercommunication.common.Constant;
import com.communication.servercommunication.common.DBContract;
import com.communication.servercommunication.common.DBManager;
import com.communication.servercommunication.common.HttpMultiProtocol;
import com.communication.servercommunication.common.Utils;
import com.communication.servercommunication.model.BaseData;
import com.communication.servercommunication.model.DBData;
import com.communication.servercommunication.model.SOSListData;

import java.util.ArrayList;
import java.util.List;

public class AView extends LinearLayout implements View.OnClickListener {

    private SOSActionbar mActionbar;

    private Button mBTNDownload;

    private DBManager mDBManger;

    private LayoutInflater mInflater;

    private int mDbSEQ = 0;

    private Context mContext;

    public AView(Context context) {
        super(context);
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        init();
    }

    private void init() {
        mInflater.inflate(R.layout.activity_a, this, true);
        mDBManger = new DBManager(mContext);
        Log.d("TEST", "onCreate A");

        /* 액션바 셋팅 */
        mActionbar = (SOSActionbar) findViewById(R.id.view_actionbar);
        mActionbar.setActionbarTitle("A Activity");
        mActionbar.setNaviBack(mContext, View.VISIBLE);

        mBTNDownload = (Button) findViewById(R.id.btn_download);
        mBTNDownload.setOnClickListener(this);
    }

    /* 서버로부터 데이터를 가져와서 어댑터에 셋팅 */
    public void setDataList(final Context context) {
        SOSListData listData = new SOSListData(Constant.METHOD_POST,
                Constant.GET_BOARD_LIST_URL,
                "count=2&currentPageNo=3");
        HttpMultiProtocol protocol = new HttpMultiProtocol(new Utils.NetworkCheckCallback() {

            @Override
            public BaseData onSuccess(BaseData data) {
                if (data != null) {
                    SOSListData.SOSListRequest request = (SOSListData.SOSListRequest) data.mDataClass;
                    if ("true".equals(request.mIsSuccess)) { // 성공한 경우
                        if (request.mData != null) {
                            Log.d("TEST", String.valueOf(request.mMessage));

                            Cursor tempCursorValue = null;

                            List<DBData> tempList = null;

                            int count = 0;

                            /* 해당 데이터가 DB에 존재하는지 검색 */
                            int[] tempInt = new int[request.mData.size()];

                            if (DBManager.getInstance(mContext).getSOSData().getCount() < 0) {
                                tempList = new ArrayList<>();
                                tempList.addAll(DBManager.getInstance(mContext).getSOSListData());

                            } else {

                                for (int i = 0; i < request.mData.size(); i++) {
                                    tempInt[i] = request.mData.get(i).mAnsim_info_seq;

                                    tempCursorValue = DBManager.getInstance(mContext)
                                            .getKeywordSOSData(request.mData.get(i).mAnsim_info_seq);

                                    //                                    tempCursorValue.moveToFirst();

                                    while (tempCursorValue.moveToNext()) {
                                        mDbSEQ =
                                                tempCursorValue.getInt(tempCursorValue.getColumnIndex(DBContract.SOSListItem.COLUMN_NAME_SOS_ANSIM_INFO_SEQ));

                                        break;
                                    }

                                    if (mDbSEQ != tempInt[i]) {
                                        /* DB에 저장 */
                                        mDBManger.insertSOSData(request.mData.get(i));
                                        Log.d("TEST", mDbSEQ + ", " + tempInt[i]);
                                        count++;
                                    } else {
                                        Log.d("TEST", "Not insert");
                                    }
                                }
                                Toast.makeText(mContext, count + " 개의 데이터를 DB에 저장했습니다.", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(context, "불러올 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                            return data;
                        }
                    }
                }

                /*
                 * null을 리턴하는것은 좋지 않음, 차라리 빈 컬렉션 리턴하기
                 */
                return data;
            }

            @Override
            public BaseData onFail(BaseData data) {
                if (data != null) {
                    SOSListData.SOSListRequest request = (SOSListData.SOSListRequest) data.mDataClass;
                    if ("false".equals(request.mIsSuccess)) { // 실패한 경우
                        if (request.mData != null) {
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

        protocol.execute(listData);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_download:
                setDataList(mContext);

                break;
        }
    }
}
