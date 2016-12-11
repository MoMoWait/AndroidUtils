package cn.edu.fjnu.momo.androidutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class GridTestActivity extends AppCompatActivity {

    @ViewInject(R.id.grid_photo)
    private GridView mGridPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_test);
        x.view().inject(this);
        initView();
    }

    public void initView(){

    }
}
