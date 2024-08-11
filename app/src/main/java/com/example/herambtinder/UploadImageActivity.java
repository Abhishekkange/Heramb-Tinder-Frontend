package com.example.herambtinder;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.herambtinder.MultipartRequest;
import com.example.herambtinder.R;
import com.example.herambtinder.swipeActivity;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UploadImageActivity extends AppCompatActivity {

    ImageView image1, image2, image3, image4, image5;
    List<Uri> imageUris;
    Button continueButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    private int currentImageIndex;

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

        // Finding IDs
        image1 = findViewById(R.id.image2);
        image2 = findViewById(R.id.image3);
        image3 = findViewById(R.id.image4);
        image4 = findViewById(R.id.image5);
        image5 = findViewById(R.id.image6);

        continueButton = findViewById(R.id.continueBtn);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getUrlListSize(imageUris) >= 3) {
                    for (Uri uri : imageUris) {
                        if (uri != null) {
                            uploadImage(uri);

                        }
                    }
                    Intent intent = new Intent(UploadImageActivity.this, swipeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(UploadImageActivity.this, "Add At least 3 Images", Toast.LENGTH_SHORT).show();
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
            if (imageUris.get(i) != null) {
                count++;
            }
        }
        return count;
    }

    private void uploadImage(Uri imageUri) {
        String realPath = getRealPathFromURI(imageUri);
        if (realPath == null || realPath.isEmpty()) {
            Toast.makeText(this, "Invalid file path", Toast.LENGTH_SHORT).show();
            return;
        }
        File imageFile = new File(realPath);
        Log.d("File Path", realPath);

        String uploadUrl = "https://herambtinder.vercel.app/app/api/v1/upload";
        RequestQueue queue = Volley.newRequestQueue(this);

        MultipartRequest multipartRequest = new MultipartRequest(uploadUrl, imageFile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(UploadImageActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                        Log.d("Upload Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Upload Error", "Error: " + error.getMessage());
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            Log.e("Upload Error", "Status Code: " + error.networkResponse.statusCode);
                            Log.e("Upload Error", "Error Response: " + new String(error.networkResponse.data));
                        }
                        Toast.makeText(UploadImageActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(multipartRequest);
    }


    private String getRealPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        }
        return null;
    }
}
