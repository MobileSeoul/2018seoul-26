package com.example.ljs.mytest;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.flavienlaurent.discrollview.lib.Discrollvable;

public class intro_lucky extends FrameLayout implements Discrollvable{

    private static final String TAG = "DiscrollvableGreenLayout";

    private TextView mLuckyView1;
    private float mLuckyView1TranslationY;
    private int mGreenColor;
    private int mBlackColor = Color.BLACK;
    private ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();

    public intro_lucky(Context context) {
        super(context);
    }

    public intro_lucky(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public intro_lucky(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mLuckyView1 = (TextView) findViewById(R.id.lucky_view);
        mLuckyView1TranslationY = mLuckyView1.getTranslationY();
        mGreenColor = getResources().getColor(android.R.color.holo_green_light);
    }

    @Override
    public void onResetDiscrollve() {
        mLuckyView1.setTranslationY(mLuckyView1TranslationY);
        mLuckyView1.setTextColor(mGreenColor);
        setBackgroundColor(mBlackColor);
    }

    @Override
    public void onDiscrollve(float ratio) {
        mLuckyView1.setTranslationY(mLuckyView1TranslationY * (1 - ratio));
        if(ratio >= 0.5f) {
            ratio = (ratio - 0.5f) / 0.5f;
            mLuckyView1.setTextColor((Integer) mArgbEvaluator.evaluate(ratio, mBlackColor, mGreenColor));
            setBackgroundColor((Integer) mArgbEvaluator.evaluate(ratio, mGreenColor, mBlackColor));
        }
    }
}
