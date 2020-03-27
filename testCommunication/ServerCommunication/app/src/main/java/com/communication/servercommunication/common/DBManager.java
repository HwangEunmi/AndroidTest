package com.communication.servercommunication.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.communication.servercommunication.model.DBData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hwangem on 2017-01-25.
 */

/* DB는 TABLE 구조로 Data를 관리하고 있으므로, 우선 해야 할 작업이 TABLE 구조를 만드는 일임 */
public class DBManager extends SQLiteOpenHelper {

    private SQLiteDatabase mDb;

    private static final String mDB_NAME = "sos_data_db"; // DB 이름

    private static final int mDB_VERSION = 1; // DB 버전


    private static DBManager mInstance;

    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBManager(context);
        }
        return mInstance;
    }

    public DBManager(Context context) {
        super(context, mDB_NAME, null, mDB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("TEST", "call oncreate");

        try {
            db.beginTransaction();
            StringBuffer sql = new StringBuffer();

            sql.append("CREATE TABLE ").append(DBContract.SOSListItem.TABLE_NAME_SOS).append(" (");
            sql.append(DBContract.SOSListItem.COLUMN_NAME_SOS_SEQ).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
            sql.append(DBContract.SOSListItem.COLUMN_NAME_SOS_ANSIM_INFO_SEQ).append(" INTEGER NOT NULL, ");
            sql.append(DBContract.SOSListItem.COLUMN_NAME_SOS_SHORT_KEY).append(" TEXT, "); // 얘도  not null인지 확인(again)
            sql.append(DBContract.SOSListItem.COLUMN_NAME_SOS_TITLE).append(" TEXT NOT NULL, ");
            sql.append(DBContract.SOSListItem.COLUMN_NAME_SOS_CONTENT).append(" TEXT NOT NULL)");

            db.execSQL(sql.toString());

            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // (디비내용의 변경으로)버전이 업데이트되면 db를 다시 만들어줘야함(기존의 table을 삭제하고 새로운 table만들기)
    }

    /**
     * SOS누르미 데이터 등록
     *
     * @param data
     * @return
     */
    /*boardSeq == mAnsim_info_seq*/
    public int insertSOSData(DBData data) {
        Log.d("TEST", "insert");
        mDb = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.clear();
        values.put(DBContract.SOSListItem.COLUMN_NAME_SOS_ANSIM_INFO_SEQ, data.getmAnsim_info_seq());
        values.put(DBContract.SOSListItem.COLUMN_NAME_SOS_SHORT_KEY, data.getmShort_key());
        values.put(DBContract.SOSListItem.COLUMN_NAME_SOS_TITLE, data.getmTitle());
        values.put(DBContract.SOSListItem.COLUMN_NAME_SOS_CONTENT, data.getmContent());

        long lRow = -1;

        try {
            mDb.beginTransaction();
            lRow = mDb.insert(DBContract.SOSListItem.TABLE_NAME_SOS, null, values);
            mDb.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDb.endTransaction();
        }

        return (int) lRow;
    }

    /**
     * SOS누르미 데이터 갱신
     *
     * @param data
     * @return
     */
    public int updateSOSData(DBData data) {

        mDb = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.clear();
        values.put(DBContract.SOSListItem.COLUMN_NAME_SOS_ANSIM_INFO_SEQ, data.getmAnsim_info_seq());
        values.put(DBContract.SOSListItem.COLUMN_NAME_SOS_SHORT_KEY, data.getmShort_key());
        values.put(DBContract.SOSListItem.COLUMN_NAME_SOS_TITLE, data.getmTitle());
        values.put(DBContract.SOSListItem.COLUMN_NAME_SOS_CONTENT, data.getmContent());

        /*수정될 조건*/
        String strWhere = DBContract.SOSListItem.COLUMN_NAME_SOS_SEQ + " = ?";
        String[] strWhereArgs = {data.getmAnsim_info_seq() + ""};

        long lRow = -1;

        try {
            mDb.beginTransaction();
            mDb.update(DBContract.SOSListItem.TABLE_NAME_SOS, values, strWhere, strWhereArgs);
            mDb.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDb.endTransaction();
        }

        return (int) lRow;
    }

    /**
     * SOS누르미 데이터 삭제 (조건 있는)
     *
     * @param data
     */
    public void deleteSOSData(DBData data) {
        mDb = getWritableDatabase();

        long lRow = -1;

        try {
            mDb.beginTransaction();

            StringBuffer sbSql = new StringBuffer();
            sbSql.append("DELETE FROM ");
            sbSql.append(DBContract.SOSListItem.TABLE_NAME_SOS);
            sbSql.append(" WHERE ")
                    .append(DBContract.SOSListItem.COLUMN_NAME_SOS_ANSIM_INFO_SEQ).append(" = ").append(data.getmAnsim_info_seq());// ANSIM_INFO_SEQ

            mDb.execSQL(sbSql.toString());
            mDb.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDb.endTransaction();
        }
    }

    /**
     * SOS누르미 데이터 삭제 (조건 없는)
     */
    public void deleteAllData() {
        mDb = getWritableDatabase();

        try {
            mDb.beginTransaction();

            mDb.delete(DBContract.SOSListItem.TABLE_NAME_SOS, null, null);
            mDb.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDb.endTransaction();
        }
    }

    /**
     * SOS누르미 데이터 조회 (조건 있는)
     *
     * @return
     */
    public Cursor getKeywordSOSData(int boardSeq) {
        mDb = getReadableDatabase();

        String[] strSelectionArgs = {boardSeq + ""};

        StringBuffer sbSql = new StringBuffer();
        sbSql.append("SELECT ");
        sbSql.append(DBContract.SOSListItem.COLUMN_NAME_SOS_SEQ).append(", ");
        sbSql.append(DBContract.SOSListItem.COLUMN_NAME_SOS_ANSIM_INFO_SEQ).append(", ");
        sbSql.append(DBContract.SOSListItem.COLUMN_NAME_SOS_SHORT_KEY).append(", ");
        sbSql.append(DBContract.SOSListItem.COLUMN_NAME_SOS_TITLE).append(", ");
        sbSql.append(DBContract.SOSListItem.COLUMN_NAME_SOS_CONTENT);

        sbSql.append(" FROM ").append(DBContract.SOSListItem.TABLE_NAME_SOS);
        sbSql.append(" WHERE ").append(DBContract.SOSListItem.COLUMN_NAME_SOS_ANSIM_INFO_SEQ).append(" = ?");
        sbSql.append(" ORDER BY ").append(DBContract.SOSListItem.COLUMN_NAME_SOS_SEQ).append(" ASC ");

        Cursor cursor = null;

        try {
            mDb.beginTransaction();

            cursor = mDb.rawQuery(sbSql.toString(), strSelectionArgs);
            mDb.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDb.endTransaction();
        }

        return cursor;
    }

    /**
     * SOS누르미 데이터 조회 (조건 없는)
     *
     * @return
     */
    public Cursor getSOSData() {
        mDb = getReadableDatabase();

        Cursor cursor = null;

        StringBuffer sbSql = new StringBuffer();
        sbSql.append("SELECT ");
        sbSql.append(DBContract.SOSListItem.COLUMN_NAME_SOS_SEQ).append(", ");
        sbSql.append(DBContract.SOSListItem.COLUMN_NAME_SOS_ANSIM_INFO_SEQ).append(", ");
        sbSql.append(DBContract.SOSListItem.COLUMN_NAME_SOS_SHORT_KEY).append(", ");
        sbSql.append(DBContract.SOSListItem.COLUMN_NAME_SOS_TITLE).append(", ");
        sbSql.append(DBContract.SOSListItem.COLUMN_NAME_SOS_CONTENT);

        sbSql.append(" FROM ").append(DBContract.SOSListItem.TABLE_NAME_SOS);
        sbSql.append(" ORDER BY ").append(DBContract.SOSListItem.COLUMN_NAME_SOS_SEQ).append(" ASC ");

        try {
            mDb.beginTransaction();

            cursor = mDb.rawQuery(sbSql.toString(), null);
            mDb.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDb.endTransaction();
        }

        return cursor;
    }

    /**
     * SOS누르미 데이터 리스트 조회 (조건 없는)
     *
     * @return
     */
    public List<DBData> getSOSListData() {
        mDb = getReadableDatabase();

        Cursor cursor = null;

        StringBuffer sbSql = new StringBuffer();
        sbSql.append("SELECT ");
        sbSql.append(DBContract.SOSListItem.COLUMN_NAME_SOS_SEQ).append(", ");
        sbSql.append(DBContract.SOSListItem.COLUMN_NAME_SOS_ANSIM_INFO_SEQ).append(", ");
        sbSql.append(DBContract.SOSListItem.COLUMN_NAME_SOS_SHORT_KEY).append(", ");
        sbSql.append(DBContract.SOSListItem.COLUMN_NAME_SOS_TITLE).append(", ");
        sbSql.append(DBContract.SOSListItem.COLUMN_NAME_SOS_CONTENT);

        sbSql.append(" FROM ").append(DBContract.SOSListItem.TABLE_NAME_SOS);
        sbSql.append(" ORDER BY ").append(DBContract.SOSListItem.COLUMN_NAME_SOS_SEQ).append(" ASC ");

        List<DBData> listData = null;

        try {
            cursor = mDb.rawQuery(sbSql.toString(), null);

            listData = new ArrayList<>();

            if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
                do {
                    DBData dbData = new DBData();

                    dbData.setmSosSEQ(cursor.getInt(cursor.getColumnIndex(DBContract.SOSListItem.COLUMN_NAME_SOS_SEQ)));
                    dbData.setmAnsim_info_seq(cursor.getInt(cursor.getColumnIndex(DBContract.SOSListItem.COLUMN_NAME_SOS_ANSIM_INFO_SEQ)));
                    dbData.setmShort_key(cursor.getString(cursor.getColumnIndex(DBContract.SOSListItem.COLUMN_NAME_SOS_SHORT_KEY)));
                    dbData.setmTitle(cursor.getString(cursor.getColumnIndex(DBContract.SOSListItem.COLUMN_NAME_SOS_TITLE)));
                    dbData.setmContent(cursor.getString(cursor.getColumnIndex(DBContract.SOSListItem.COLUMN_NAME_SOS_CONTENT)));

                    listData.add(dbData);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        return listData;
    }
}
