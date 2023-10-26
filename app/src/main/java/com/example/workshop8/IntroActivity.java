package com.example.workshop8;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.Button;
import android.animation.ValueAnimator;
import android.animation.AnimatorListenerAdapter;

public class IntroActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Button continueButton;
    //private ImageView arrowImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewPager = findViewById(R.id.viewPager);
        continueButton = findViewById(R.id.btn_continue);
        //arrowImageView = findViewById(R.id.btn_continue);

        //Set an onClickListener to the continue button
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Animate the arrow
                animateArrow();
            }
        });
    }

    private void animateArrow() {
        // Create a ValueAnimator to move the arrow from the start to the end
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(1000); // Animation duration in milliseconds

        // Update the arrow's position as the animation progresses
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                // Adjust the final position as needed
                float finalPosition = 0.8f; // 0.8 is an example, adjust it accordingly
                //arrowImageView.setTranslationX(progress * finalPosition);
            }
        });

        // Start the animation
        animator.start();

        // Launch the next activity when the animation completes
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finish the IntroActivity to prevent going back
            }
        });
    }
}
