package cn.edu.fjnu.momo.androidutils.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.SimpleCursorAdapter;

import momo.cn.edu.fjnu.androidutils.utils.DeviceInfoUtils;
import momo.cn.edu.fjnu.androidutils.utils.LogUtils;
import momo.cn.edu.fjnu.androidutils.utils.SizeUtils;

/**
 * 自定义视图
 * Created by gaofei on 2016/10/23.
 */

public class CustomTestView extends View {
    public static final String TAG = CustomTestView.class.getSimpleName();
    public CustomTestView(Context context) {
        super(context);
    }

    public CustomTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtils.i(TAG, "onMeasure");
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        LogUtils.i(TAG, "onMeasure->widthMode:" + widthMode);
        LogUtils.i(TAG, "onMeasure->heightMode:" + heightMode);
        switch (widthMode){
            case MeasureSpec.AT_MOST:
                LogUtils.i(TAG, "widthMode = AT_MOST " );
                break;
            case MeasureSpec.EXACTLY:
                LogUtils.i(TAG, "widthMode = EXACTLY");
                break;
            case MeasureSpec.UNSPECIFIED:
                LogUtils.i(TAG, "widthMode = UNSPECIFIED");
                break;
        }

        switch (heightMode){
            case MeasureSpec.AT_MOST:
                LogUtils.i(TAG, "widthMode = AT_MOST " );
                break;
            case MeasureSpec.EXACTLY:
                LogUtils.i(TAG, "widthMode = EXACTLY " );
                break;
            case MeasureSpec.UNSPECIFIED:
                LogUtils.i(TAG, "widthMode = UNSPECIFIED " );
                break;
        }

        setMeasuredDimension(SizeUtils.dp2px(100), SizeUtils.dp2px(50));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawColor(Color.GREEN);
        LogUtils.i(TAG, "onDraw");
        LogUtils.i(TAG, "onDraw->x:" + getX());
        LogUtils.i(TAG, "onDraw->y:" + getY());
        //LogUtils.i(TAG, "onDraw->z:" + getZ());


    }


/*    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed)
            super.layout(200, 800, 200 + (right - left), 800 + (bottom - top));
        LogUtils.i(TAG, "onLayout");
    }*/


    @Override
    public void layout(int l, int t, int r, int b) {
        int screenWidth = DeviceInfoUtils.getScreenWidth(getContext());
        int screenHeight = DeviceInfoUtils.getScreenWidth(getContext());
        int left = (screenWidth - SizeUtils.dp2px(100)) / 2;
        int top = t;
        int right = left + SizeUtils.dp2px(100);
        int bottom = b;
        LogUtils.i(TAG, "layout->l:" + l + " layout->t:" + t + " layout->r:" + r + " layout->b:" + b);
        super.layout(l, t, r, b);
    }


}
