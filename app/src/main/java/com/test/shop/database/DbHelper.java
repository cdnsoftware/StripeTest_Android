package com.test.shop.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.test.shop.model.PaymentInformationBean;
import com.test.shop.utils.SharedPrefrence;

import java.sql.SQLException;
import java.util.ArrayList;

public class DbHelper extends OrmLiteSqliteOpenHelper {
    private static DbHelper instance = null;
    private static SQLiteDatabase database = null;
    private RuntimeExceptionDao<PaymentInformationBean, String> paymentInformationBeanStringRuntimeExceptionDao = null;
    private Context mContext;

    public DbHelper(Context context) {
        super(context, DatabaseConstant.DATABASE_NAME, null, DatabaseConstant.DATABASE_VERSION);
        mContext = context;
        database = getWritableDatabase();
    }

    public static DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context);
        }
        return instance;
    }

    public SelectArg getSelectArg(String string) {
        return new SelectArg(string);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, PaymentInformationBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }

    // method for inserting data in Product Table
    public int insertPaymentInfo(PaymentInformationBean paymentInformationBean) {

        RuntimeExceptionDao<PaymentInformationBean, String> dao = getPaymentInfoDao();
        if (!SharedPrefrence.getInstance(mContext).readBooleanPrefs(SharedPrefrence.IS_SOCIAL)) {
            database.delete(paymentInformationBean.getClass().getSimpleName(), "userName" + "=" + "'" + paymentInformationBean.getUserName() + "'", null);
            dao.create(paymentInformationBean);
        } else {
            database.delete(paymentInformationBean.getClass().getSimpleName(), "social_id" + "=" + "'" + paymentInformationBean.getSocial_id() + "'", null);
            dao.create(paymentInformationBean);
        }
        return 1;
    }

    public RuntimeExceptionDao<PaymentInformationBean, String> getPaymentInfoDao() {
        if (paymentInformationBeanStringRuntimeExceptionDao == null) {
            paymentInformationBeanStringRuntimeExceptionDao = getRuntimeExceptionDao(PaymentInformationBean.class);
        }
        return paymentInformationBeanStringRuntimeExceptionDao;
    }

    private String generateParams(String[] keys, String[] value) {
        if (keys == null)
            return null;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < keys.length; i++) {
            if (i == 0)
                stringBuilder.append(keys[i] + " = " + value[i]);
            else
                stringBuilder.append(" AND " + keys[i] + " = " + value[i]);

        }
        if (keys.length == 0) {
            return null;
        } else {
            return stringBuilder.toString();

        }
    }

    public Object getPaymentInfo(String email) {

        RuntimeExceptionDao<PaymentInformationBean, String> simpleDao =
                getPaymentInfoDao();
        String key;
        if (!SharedPrefrence.getInstance(mContext).readBooleanPrefs(SharedPrefrence.IS_SOCIAL)) {
            key = "userName";
        } else {
            key = "social_id";
        }
        ArrayList<PaymentInformationBean> list = (ArrayList<PaymentInformationBean>) simpleDao
                .queryForEq("userName", email);
        if (list.size() > 0) {
            return list.get(0);

        } else {
            return null;

        }
    }

}
