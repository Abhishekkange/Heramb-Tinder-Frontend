package com.example.herambtinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    SignInButton loginBtn;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginBtn = findViewById(R.id.signInButton);

        // Configure sign-in to request the user's ID, email address, and basic profile.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();


            }
        });

    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            updateUI(account);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
        }
    }
    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            // The user is signed in successfully, retrieve the account information.
            String displayName = account.getDisplayName();
            String email = account.getEmail();
            String idToken = account.getId();



            LoginUser(email,idToken,"https://herambtinder.vercel.app/app/api/v1/login");

        } else {
            Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void LoginUser(String email,String userId,String LOGIN_URL)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Create the request JSON object
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("email", email);
            requestBody.put("token", userId);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "error3", Toast.LENGTH_SHORT).show();
        }

        // Create the JsonObjectRequest
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                LOGIN_URL,
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            String type = response.getString("type");
                            if(type == "error")
                            {
                                Toast.makeText(Login.this, "SignIn Failed", Toast.LENGTH_SHORT).show();
                            }
                            else{

                                SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putString("email",email);
                                myEdit.putString("token",message);
                                myEdit.commit();
                                Toast.makeText(Login.this, type, Toast.LENGTH_SHORT).show();

                                    if(type.equals("login"))
                                    {
                                        Intent intent = new Intent(getApplicationContext(),swipeActivity.class);
                                        startActivity(intent);
                                    }
                                    else if(type.equals("signedup"))
                                    {
                                        Intent intent = new Intent(getApplicationContext(),setupProfileActivity.class);
                                        startActivity(intent);
                                    }
                                    else{

                                        Toast.makeText(Login.this, "2", Toast.LENGTH_SHORT).show();
                                    }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Login.this, "This error", Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, "Error Sign In"+error, Toast.LENGTH_SHORT).show();
                        Log.d("srk",error.toString());
                    }
                }
        );

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }

}