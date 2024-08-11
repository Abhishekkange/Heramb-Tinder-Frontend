package com.example.herambtinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.herambtinder.Fragments.birthdayFrag;
import com.example.herambtinder.Fragments.selectUniversityFrag;
import com.example.herambtinder.Fragments.selecturgender;

public class setupProfileActivity extends AppCompatActivity {

    FrameLayout profileFrame;
    ImageView nextBtn;
    public static int frag_checker = 0;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setup_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;});

        profileFrame = findViewById(R.id.profileFrame);
        nextBtn = findViewById(R.id.settiingProfileNextBtn);
        birthdayFrag birthfrag = new birthdayFrag();
        selecturgender urGender = new selecturgender();


        getSupportFragmentManager().beginTransaction()
                .add(R.id.profileFrame, birthfrag).commit();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(frag_checker ==0){

                    frag_checker++;
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.profileFrame, urGender).commit();
                }
                else if(frag_checker ==1)
                {
                    Intent intent = new Intent(getApplicationContext(), UploadImageActivity.class);
                    startActivity(intent);
                }
            }
        });


    }
}