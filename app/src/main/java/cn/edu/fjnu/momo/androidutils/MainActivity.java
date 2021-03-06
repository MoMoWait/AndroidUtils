package cn.edu.fjnu.momo.androidutils;

import android.content.Intent;
import android.content.ServiceConnection;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import cn.edu.fjnu.momo.androidutils.javatest.CountService;
import cn.edu.fjnu.momo.androidutils.view.CustomTestView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private TextView mTextHello;
    private CustomTestView mCustomView;
    private ServiceConnection mCountConnection;
    private ICountService mCountService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextHello = (TextView) findViewById(R.id.text_hello);
        mTextHello.setText("");
        testCountService();

    }

    private void testCountService(){
        Intent intent = new Intent(this, CountService.class);
        startService(intent);
        /*
        mCountConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(TAG, "onServiceConnected");
                mCountService =  ICountService.Stub.asInterface(service);
                for(int i = 0; i != 20; ++i){
                    try{
                        int count = mCountService.getCount();
                        mTextHello.setText(mTextHello.getText().toString() + count + "\n");
                    }catch (Exception e){

                    }
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(intent, mCountConnection, Service.BIND_AUTO_CREATE);*/
    }

}
