package com.example.ljs.mytest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.flavienlaurent.discrollview.lib.Discrollvable;

public class intro_food extends FrameLayout implements Discrollvable{

    private static final String TAG = "DiscrollvableRedLayout";

    private View mRedView1;
    private View mRedView2;
    private float mRedView1TranslationY;

    public intro_food(@NonNull Context context) {
        super(context);
    }

    public intro_food(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public intro_food(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mRedView1 = findViewById(R.id.intro_food_text);
        mRedView1TranslationY = mRedView1.getTranslationY();
        mRedView2 = findViewById(R.id.intro_food_img);
    }
    @Override
    public void onDiscrollve(float ratio) {
        if(ratio <= 0.2f) {
            mRedView1.setTranslationY(-1 * (mRedView1.getHeight()/1.5f) * (ratio / 0.2f));
        } else {
            float rratio = (ratio - 0.2f) / 0.2f;
            rratio = Math.min(rratio, 1.0f);
            mRedView1.setTranslationY(-1 * (mRedView1.getHeight()/1.5f));
            mRedView2.setAlpha(1 * rratio);
            mRedView2.setScaleX(1.0f * rratio);
            mRedView2.setScaleY(1.0f * rratio);
        }
    }

    @Override
    public void onResetDiscrollve() {

    }
}
