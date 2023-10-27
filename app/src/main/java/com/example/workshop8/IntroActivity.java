package com.example.workshop8;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.animation.ValueAnimator;

public class IntroActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Button continueButton;
    private float initialX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewPager = findViewById(R.id.viewPager);
        continueButton = findViewById(R.id.btn_continue);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateArrow();
            }
        });

        // Tracks user touch event
       /* continueButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = x;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float finalX = x - initialX;

                        float translationX = continueButton.getTranslationX() + finalX;
                        if (translationX >= 0 && translationX <= 0.8f) {
                            continueButton.setTranslationX(translationX);
                        }

                        initialX = x;
                        break;

                    case MotionEvent.ACTION_UP:
                        // When drag is released, you can add any logic you need here.
                        break;
                }
                return true;
            }
        });*/
    }

    private void animateArrow() {
        ValueAnimator animator = ValueAnimator.ofFloat(continueButton.getTranslationX(), 0.8f);
        animator.setDuration(1000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                continueButton.setTranslationX(progress);
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        animator.start();
    }
}
