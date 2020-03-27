package com.communication.servercommunication.common;

import android.app.Activity;
import android.widget.Toast;

import com.communication.servercommunication.model.BaseData;

/**
 * Created by 은미 on 2017-01-20.
 */

public class Utils {

    private static Utils mInstance;

    public static Utils getmInstance() {
        if (mInstance == null) {
            mInstance = new Utils();

        }
        return mInstance;
    }

    /*Toast 메소드*/
    public void setFloatingToast(Activity context) {
        Toast.makeText(context, "현재 화면은 " + context.getLocalClassName() + " 입니다.", Toast.LENGTH_SHORT).show();
    }

    /*AsyncTask의 onPostExecute()의 인터페이스 연결을 위한
    * (네트워크 성공/실패 처리)*/
    public interface NetworkCheckCallback {
        public BaseData onSuccess(BaseData data); // 성공일 경우

        public BaseData onFail(BaseData data); // 실패일 경우
    }
}
