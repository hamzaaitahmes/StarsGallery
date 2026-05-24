package ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

import androidx.appcompat.app.AppCompatActivity;
import com.example.starsgallery.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class SplashActivity extends AppCompatActivity {

    private CircleImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);

        // Animation de rotation
        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1500);
        rotate.setFillAfter(true);

        // Animation de scale (zoom)
        ScaleAnimation scale = new ScaleAnimation(0.5f, 1.5f, 0.5f, 1.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(1500);
        scale.setFillAfter(true);

        // Animation de fade
        AlphaAnimation fadeOut = new AlphaAnimation(1f, 0f);
        fadeOut.setDuration(1000);
        fadeOut.setStartOffset(2000);

        // Exécution des animations
        logo.startAnimation(rotate);
        logo.startAnimation(scale);
        logo.startAnimation(fadeOut);

        // Redirection vers ListActivity
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, ListActivity.class));
            finish();
        }, 3000);
    }
}