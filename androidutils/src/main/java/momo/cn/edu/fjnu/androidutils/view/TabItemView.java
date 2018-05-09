package momo.cn.edu.fjnu.androidutils.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import momo.cn.edu.fjnu.androidutils.R;
import momo.cn.edu.fjnu.androidutils.utils.SizeUtils;

/**
 * Created by gaofei on 2017/9/10.
 * 底部导航栏项
 */
public class TabItemView extends LinearLayout {

    private Context mContext;
    private ImageView mImgBottom;
    private TextView mTextDes;
    private LinearLayout mLayoutContainer;

    public TabItemView(Context context) {
        super(context);
        init(context);
    }

    public TabItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TabItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TabItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        inflate(mContext, R.layout.view_tab_item, this);
        //初始化控件
        mImgBottom = (ImageView) findViewById(R.id.img_bottom);
        mTextDes = (TextView) findViewById(R.id.text_des);
        mLayoutContainer = (LinearLayout) findViewById(R.id.layout_container);
    }

    public void setImgText(int bottomImg, String textDes){
        mImgBottom.setImageResource(bottomImg);
        mTextDes.setText(textDes);
    }

    public void setDesTextColor(int color){
        mTextDes.setTextColor(color);
    }

    public void setBottomImg(int resImg){
        mImgBottom.setImageResource(resImg);
    }

    /**
     * 隐藏文字
     */
    public void hideText(){
        mTextDes.setVisibility(View.GONE);
        mImgBottom.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mLayoutContainer.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams imgParams = (LinearLayout.LayoutParams)mImgBottom.getLayoutParams();
        imgParams.width = SizeUtils.dp2px(60);
        imgParams.height = SizeUtils.dp2px(60);
        mImgBottom.setLayoutParams(imgParams);
    }
}