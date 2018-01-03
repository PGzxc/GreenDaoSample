package com.example.greendaosample.db;

import android.content.Context;

import com.example.greendaosample.gen.DaoMaster;
import com.example.greendaosample.gen.DaoSession;

/**
 * Created by admin on 2018/1/2.
 */

public class DBHelper {
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    /**
     * 取得DaoMaster
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,
                    "notes.db", null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }
    /**
     * 取得DaoSession
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}