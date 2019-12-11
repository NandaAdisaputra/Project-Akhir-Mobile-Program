package com.nandaadisaputra.projectakhir.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.nandaadisaputra.projectakhir.R;
import com.nandaadisaputra.projectakhir.ui.activity.login.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {

    Animation frombottom, fromtop;
    @BindView(R.id.resume)
    ImageView resume;
    @BindView(R.id.get_started)
    Button getStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this, R.anim.fromtop);

        getStarted.setAnimation(frombottom);
        resume.setAnimation(fromtop);

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashScreenActivity.this, RegisterActivity.class);
                SplashScreenActivity.this.startActivity(intent);
            }
        });
    }
}
