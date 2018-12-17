package com.example.ljs.mytest;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class speed extends AppCompatActivity {

    TextView textView;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);
        textView= findViewById(R.id.TimeText);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);

        System.out.println("sdlkfaslkdflsakfsjdlfsdfsd@@@@@");


        textView.setText(getCurrentTime());
        SharedPreferences pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE);
        boolean first = pref.getBoolean("isFirst", false);
        if(first==false){
            Log.d("Is first Time?", "first");
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst",true);
            editor.commit();
            final Display display = getWindowManager().getDefaultDisplay();
            final Drawable droid = ContextCompat.getDrawable(this, R.drawable.ic_radio_button_unchecked_black_24dp);
            final Rect droidTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 2, droid.getIntrinsicHeight() * 2);
            // Using deprecated methods makes you look way cool
            droidTarget.offset(display.getWidth() / 2, display.getHeight() / 2);

            final SpannableString spannedDesc1 = new SpannableString("Find nearby halal food.");

            final SpannableString spannedDesc2 = new SpannableString("Allahu Akbar.");

            final SpannableString spannedDesc3 = new SpannableString("Hospitals, grocery stores, hotels, embassies for Muslim");

            final SpannableString spannedDesc4 = new SpannableString("\n" +"Enjoy Seoul.");

            final TapTargetSequence sequence = new TapTargetSequence(this)
                    .targets(
                            // This tap target will target the back button, we just need to pass its containing toolbar
                            TapTarget.forView(findViewById(R.id.imageView1), "Enjoy Haral Food!", spannedDesc1)
                                    .outerCircleColor(R.color.yellow)
                                    .outerCircleAlpha(0.95f)
                                    .targetCircleColor(R.color.white)
                                    .titleTextColor(R.color.green)
                                    .cancelable(true)
                                    .drawShadow(true)
                                    .titleTextDimen(R.dimen.title_text_size)
                                    .descriptionTextColor(R.color.green)
                                    .tintTarget(false)
                                    .transparentTarget(true)
                                    .id(1)
                                    .icon(droid)
                            .targetRadius(60)
                            ,

                            TapTarget.forView(findViewById(R.id.imageView2), "Check out the mosque and prayer room.", spannedDesc2)
                                    .outerCircleColor(R.color.haeul)
                                    .outerCircleAlpha(0.95f)
                                    .targetCircleColor(R.color.white)
                                    .titleTextColor(R.color.white)
                                    .cancelable(false)
                                    .drawShadow(true)
                                    .titleTextDimen(R.dimen.title_text_size)
                                    .descriptionTextColor(R.color.white)
                                    .transparentTarget(true)
                                    .tintTarget(false)
                                    .targetRadius(60)
                                    .id(2)
                                    //  .targetRadius(60)
                                    .icon(droid) ,
                            TapTarget.forView(findViewById(R.id.imageView3), "Check out a number of helpful facilities.", spannedDesc3)
                                    .outerCircleColor(R.color.water)
                                    .outerCircleAlpha(0.95f)
                                    .targetCircleColor(R.color.white)
                                    .titleTextColor(R.color.white)
                                    .cancelable(false)
                                    .drawShadow(true)
                                    .titleTextDimen(R.dimen.title_text_size)
                                    .descriptionTextColor(R.color.white)
                                    .transparentTarget(true)
                                    .tintTarget(false)
                                    .targetRadius(60)
                                    .icon(droid)
                                    .id(3),
                            TapTarget.forView(findViewById(R.id.imageView4), "Experience the sights of Seoul!", spannedDesc4)
                                    .outerCircleColor(R.color.jinsub)
                                    .outerCircleAlpha(0.95f)
                                    .targetCircleColor(R.color.white)
                                    .titleTextColor(R.color.black)
                                    .cancelable(false)
                                    .drawShadow(true)
                                    .titleTextDimen(R.dimen.title_text_size)
                                    .descriptionTextColor(R.color.black)
                                    .transparentTarget(true)
                                    .tintTarget(false)
                                    .targetRadius(60)
                                    .icon(droid)
                                    .id(4)

                    )
                    .listener(new TapTargetSequence.Listener() {
                        // This listener will tell us when interesting(tm) events happen in regards
                        // to the sequence
                        @Override
                        public void onSequenceFinish() {
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.test,R.anim.test);
                            finish();
                            overridePendingTransition(R.anim.test,R.anim.test);
                        }

                        @Override
                        public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                            Log.d("TapTargetView", "Clicked on " + lastTarget.id());
                        }

                        @Override
                        public void onSequenceCanceled(TapTarget lastTarget) {
                            final AlertDialog dialog = new AlertDialog.Builder(speed.this)
                                    .setTitle("Uh oh")
                                    .setMessage("You canceled the sequence")
                                    .setPositiveButton("Oops", null).show();
                            TapTargetView.showFor(dialog,
                                    TapTarget.forView(dialog.getButton(DialogInterface.BUTTON_POSITIVE), "Uh oh!", "You canceled the sequence at step " + lastTarget.id())
                                            .cancelable(false)
                                            .tintTarget(false), new TapTargetView.Listener() {
                                        @Override
                                        public void onTargetClick(TapTargetView view) {
                                            super.onTargetClick(view);
                                            dialog.dismiss();
                                        }
                                    });
                        }
                    });
            final SpannableString spannedDesc = new SpannableString("Click to see the prayer times in Seoul.");
            spannedDesc.setSpan(new UnderlineSpan(), spannedDesc.length() - "TapTargetView".length(), spannedDesc.length(), 0);
            TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.TimeText), "Check Prayer Time!", spannedDesc)
                    .outerCircleColor(R.color.red)
                    .outerCircleAlpha(0.9f)
                    .titleTextColor(R.color.white)
                    .cancelable(false)
                    .drawShadow(true)
                    .titleTextDimen(R.dimen.title_text_size)
                    .descriptionTextColor(R.color.white)
                    .transparentTarget(true)
                    .tintTarget(false), new TapTargetView.Listener() {
                @Override
                public void onTargetClick(TapTargetView view) {
                    super.onTargetClick(view);
                    // .. which evidently starts the sequence we defined earlier
                    sequence.start();
                }

                @Override
                public void onOuterCircleClick(TapTargetView view) {
                    super.onOuterCircleClick(view);
                    Toast.makeText(view.getContext(), "You clicked the outer circle!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                    Log.d("TapTargetViewSample", "You dismissed me :(");
                }
            });
        }else{
            Log.d("wpqkf","@#$@#$@#$@#$@#$@#$");
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.test,R.anim.test);
            finish();
            overridePendingTransition(R.anim.test,R.anim.test);
        }




    }

    private String getCurrentTime() {
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("HH:mm");
        final String str = dayTime.format(new Date(time));
        return str;
    }
}
