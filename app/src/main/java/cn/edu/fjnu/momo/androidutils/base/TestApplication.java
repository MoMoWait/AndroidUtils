package cn.edu.fjnu.momo.androidutils.base;

import android.os.Environment;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

import momo.cn.edu.fjnu.androidutils.base.BaseApplication;

/**
 * Created by gaofei on 2016/10/23.
 */

public class TestApplication extends BaseApplication {
    public static DbManager mDbmanager;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        initDB();
    }


    private void initDB(){
        mDbmanager = x.getDb(new DbManager.DaoConfig().setAllowTransaction(true)
        .setDbDir(new File(Environment.getExternalStorageDirectory().getPath() + "/db"))
        .setDbName("persons.db").setDbVersion(1));
    }
}
