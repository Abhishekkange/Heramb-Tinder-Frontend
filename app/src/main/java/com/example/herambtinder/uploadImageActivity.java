package com.example.herambtinder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class uploadImageActivity extends AppCompatActivity {

    ImageView image1,image2,image3,image4,image5;
    List<Uri> imageUris;
    Button continueButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    private int currentImageIndex;
    private ImageView selectedImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload_image);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageUris = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            imageUris.add(null); // Initialize all entries to null
        }


        //finding Ids
        image1 = findViewById(R.id.image2);
        image2 = findViewById(R.id.image3);
        image3 = findViewById(R.id.image4);
        image4 = findViewById(R.id.image5);
        image5 = findViewById(R.id.image6);

        continueButton = findViewById(R.id.continueBtn);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(getUrlListSize(imageUris)>=3)
                {
                    Intent intent = new Intent(uploadImageActivity.this,setupProfileActivity.class);
                    startActivity(intent);
                }
                else{

                    Toast.makeText(uploadImageActivity.this, "Add Atleast 3 Images", Toast.LENGTH_SHORT).show();
                }
            }
        });

        image1.setOnClickListener(view -> openGallery(0));
        image2.setOnClickListener(view -> openGallery(1));
        image3.setOnClickListener(view -> openGallery(2));
        image4.setOnClickListener(view -> openGallery(3));
        image5.setOnClickListener(view -> openGallery(4));


    }
    private void openGallery(int imageIndex) {
        currentImageIndex = imageIndex;
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            imageUris.set(currentImageIndex, imageUri);
            getSelectedImageView(currentImageIndex).setImageURI(imageUri);
        }
    }
    private ImageView getSelectedImageView(int index) {
        switch (index) {
            case 0:
                return image1;
            case 1:
                return image2;
            case 2:
                return image3;
            case 3:
                return image4;
            case 4:
                return image5;
            default:
                return null;
        }
    }
    private int getUrlListSize(List<Uri> imageUris) {


        int count = 0;

        for (int i = 0; i < 5; i++) {

            if(imageUris.get(i)==null){
                continue;
            }
            else{
                count++;
            }
        }


        return count;

    }
}