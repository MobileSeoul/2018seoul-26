package com.example.ljs.mytest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.flavienlaurent.discrollview.lib.Discrollvable;

public class intro_translation extends FrameLayout implements Discrollvable{

    private static final String TAG = "DiscrollvablePurpleLayout";

    private View mPurpleView1;
    private View mPurpleView2;
    private View mPurpleView3;
    private float mPurpleView1TranslationX;
    private float mPurpleView2TranslationX;
    private float mPurpleView3TranslationX;
    public intro_translation(Context context) {
        super(context);
    }

    public intro_translation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public intro_translation(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mPurpleView1 = findViewById(R.id.translation_Image1);
        mPurpleView1TranslationX = mPurpleView1.getTranslationX();
        mPurpleView2 = findViewById(R.id.translation_Image2);
        mPurpleView2TranslationX = mPurpleView2.getTranslationX();
        mPurpleView3 = findViewById(R.id.translation_Image3);
        mPurpleView3TranslationX = mPurpleView3.getTranslationX();
    }

    @Override
    public void onResetDiscrollve() {
        mPurpleView1.setAlpha(0);
        mPurpleView2.setAlpha(0);
        mPurpleView3.setAlpha(0);
        mPurpleView1.setTranslationX(mPurpleView1TranslationX);
        mPurpleView2.setTranslationX(mPurpleView2TranslationX);
        mPurpleView3.setTranslationX(mPurpleView3TranslationX);
    }

    @Override
    public void onDiscrollve(float ratio) {
        if(ratio <= 0.33f) {
            mPurpleView3.setTranslationX(0);
            mPurpleView3.setAlpha(0.0f);
            mPurpleView2.setTranslationX(0);
            mPurpleView2.setAlpha(0.0f);
            float rratio = ratio/0.33f;
            mPurpleView1.setTranslationX(mPurpleView1TranslationX * (1 - rratio));
            mPurpleView1.setAlpha(rratio);
        } else if(ratio<=0.66f){
            mPurpleView1.setTranslationX(0);
            mPurpleView1.setAlpha(1.0f);
            mPurpleView3.setTranslationX(0);
            mPurpleView3.setAlpha(0.0f);
            float rratio = (ratio - 0.33f)/0.33f;
            mPurpleView2.setTranslationX(mPurpleView2TranslationX * (1 - rratio));
            mPurpleView2.setAlpha(rratio);
        } else{
            mPurpleView2.setTranslationX(0);
            mPurpleView2.setAlpha(1.0f);
            float rratio = (ratio - 0.33f)/ 0.66f;
            mPurpleView3.setTranslationX(mPurpleView3TranslationX * (1 - rratio));
            mPurpleView3.setAlpha(rratio);
        }
    }
}
