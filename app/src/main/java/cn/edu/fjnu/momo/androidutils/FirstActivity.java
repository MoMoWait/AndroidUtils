package cn.edu.fjnu.momo.androidutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class FirstActivity extends AppCompatActivity {

    public static final String TAG = FirstActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Log.i(TAG, "onCreate->staceTrace:" + Log.getStackTraceString(new Throwable()));
    }
}
