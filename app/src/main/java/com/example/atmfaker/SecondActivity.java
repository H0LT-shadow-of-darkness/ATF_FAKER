package com.example.atmfaker;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class SecondActivity extends AppCompatActivity {

    private TextView min, cost, zone, o_termine, data, o_inizio, rimanente;
    private View sidebarL, sidebarR;
    private String zona, minuti, costo;
    private int hour, minute, month, day, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setIntentData(getIntent());
        setCalendarData();
        initializer();
        dataSet();
        startCountdown(((0 * 60 * 60 * 1000) + (59 * 60 * 1000) + (30* 1000)), rimanente);
        animateAlpha(sidebarL,1000);
        animateAlpha(sidebarR,1000);
    }

    private void startCountdown(long durationInMillis, final TextView textView) {
        new CountDownTimer(durationInMillis, 1000) {

            public void onTick(long millisUntilFinished) {
                int hours = (int) (millisUntilFinished / (1000 * 60 * 60));
                int minutes = (int) (millisUntilFinished / (1000 * 60)) % 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;

                String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);

                textView.setText(timeFormatted);
            }

            public void onFinish() {
                rimanente.setText("00:00:00");
            }

        }.start();
    }
    private void animateAlpha(final View view, long duration) {

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        fadeIn.setDuration(duration);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        fadeOut.setDuration(duration);

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(fadeIn, fadeOut);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorSet.start();
    }
    private void setIntentData(Intent i){
         zona = i.getStringExtra("ZONA");
         minuti = i.getStringExtra("MINUTI");
         costo = i.getStringExtra("COSTO");
    }
    private void setCalendarData(){
        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH)+1;
        year = cal.get(Calendar.YEAR);
    }
    private void initializer(){
        min = findViewById(R.id.textView4);
        cost = findViewById(R.id.textView5);
        zone = findViewById(R.id.textView7);
        o_termine = findViewById(R.id.textView6);
        o_inizio = findViewById(R.id.textView10);
        data = findViewById(R.id.textView8);
        rimanente = findViewById(R.id.textView11);
        sidebarL = findViewById(R.id.view1);
        sidebarR = findViewById(R.id.view2);
    }
    private void dataSet(){
        min.setText(minuti);
        cost.setText(costo);
        zone.setText("Zone " + zona);
        if (minute <= 9) {
            o_termine.setText((hour+1) + ":0" + (minute));
        } else {
            o_termine.setText((hour+1) + ":" + (minute));
        }

        data.setText(day + "/" + month + "/" +year);
        data = findViewById(R.id.textView9);
        data.setText(day + "/" + month + "/" +year);

        if (minute<= 9) {
            o_inizio.setText((hour-1) + ":0" + (minute));
        } else {
            o_inizio.setText((hour-1) + ":" + (minute));
        }
    }
}