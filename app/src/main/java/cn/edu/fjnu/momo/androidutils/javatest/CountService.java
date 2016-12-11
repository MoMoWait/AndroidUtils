package cn.edu.fjnu.momo.androidutils.javatest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.edu.fjnu.momo.androidutils.ICountService;
import cn.edu.fjnu.momo.androidutils.base.TestApplication;
import cn.edu.fjnu.momo.androidutils.bean.Person;

public class CountService extends Service{
    public static final String TAG = CountService.class.getSimpleName();
    private LocalService mBindService;
    private DbInsertThread mDbInsertThread;

    public CountService() {
        mBindService = new LocalService();
        mDbInsertThread = new DbInsertThread();
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "CountService is created", Toast.LENGTH_SHORT).show();
        mDbInsertThread.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "CountService is bind", Toast.LENGTH_SHORT).show();
        return mBindService;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "CountService is start", Toast.LENGTH_SHORT).show();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "CountService is destory", Toast.LENGTH_SHORT).show();
    }

    private class LocalService extends ICountService.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int getCount() throws RemoteException {
            return new Random().nextInt(Integer.MAX_VALUE);
        }
    }


    class DbInsertThread extends Thread{
        @Override
        public void run() {
            Log.i(TAG, "DBInsertThread is running");
            List<Person> personList = new ArrayList<>();
            String[] names = {"GaoFei", "ZhangQuanChun", "ZhengYin"};
            Random random = new Random();
            for(int i = 0; i != 1000000; ++i){
                Person person = new Person();
                person.setAge(random.nextInt(30));
                person.setBirthday(random.nextInt(9999));
                person.setHeight(random.nextInt(200));
                person.setName(names[random.nextInt(3)]);
                person.setWeight(random.nextInt(100));
                personList.add(person);
                if(personList.size() >= 100){
                    try{
                        TestApplication.mDbmanager.save(personList);
                    }catch (Exception e){

                    }

                    personList.clear();
                }
            }

            Log.i(TAG, "DBInsertThread is finished");
        }
    }


}
