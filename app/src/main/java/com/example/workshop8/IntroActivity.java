package com.example.workshop8;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;


public class IntroActivity extends AppCompatActivity {
    private Button continueButton;
    private float initialX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        continueButton = findViewById(R.id.btn_continue);

        // Handles arrow slider
        continueButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = event.getX();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float finalX = event.getX();
                        float delta = finalX - initialX;
                        initialX = finalX;

                        // Translate the button along the X-axis
                        translateButton(delta);

                        break;

                    case MotionEvent.ACTION_UP:
                        // Check if the button is swiped far enough to trigger the action
                        if (continueButton.getTranslationX() >= continueButton.getWidth() / 2) {
                            // Trigger the action
                            animateArrow();
                        } else {
                            // Return the button to initial position
                            resetButton();
                        }

                        break;
                }
                return true;
            }
        });
    }

    private void translateButton(float delta) {
        // Translate the button by delta value
        continueButton.setTranslationX(continueButton.getTranslationX() + delta);
    }

    private void resetButton() {
        // Reset the button
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 0);
        animation.setDuration(200);
        continueButton.startAnimation(animation);
    }
    // Animation
    private void animateArrow() {
        // Fades out button and opens main page
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 0f);
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                continueButton.setAlpha(alpha);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // Triggers main page after animation
                openMainPage();
            }
        });
        animator.start();
    }

    private void openMainPage() {
        // Start the MainActivity
        Intent intent = new Intent(IntroActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
