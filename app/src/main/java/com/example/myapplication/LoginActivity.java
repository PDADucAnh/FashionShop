//package com.example.myapplication;
//
//import static android.content.ContentValues.TAG;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//public class LoginActivity extends AppCompatActivity {
//    EditText etUsername, etPassword;
//    Button btnLogin, btnToRegister;
//
//    private RequestQueue mRequestQueue;
//    private StringRequest mStringRequest;
//    private String url = "https://fakestoreapi.com/users";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
////        getData();
//        etUsername = findViewById(R.id.etUsername);
//        etPassword = findViewById(R.id.etPassword);
//        btnLogin = findViewById(R.id.btnLogin);
//        btnToRegister = findViewById(R.id.btnToRegister);
//
//        btnLogin.setOnClickListener(v -> {
//            String inputUser = etUsername.getText().toString().trim();
//            String inputPass = etPassword.getText().toString().trim();
//
//            if (inputUser.isEmpty() || inputPass.isEmpty()) {
//                Toast.makeText(this, "Vui lòng nhập đầy đủ tên và mật khẩu", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
//            String savedUser = prefs.getString("username", null);
//            String savedPass = prefs.getString("password", null);
//
//            if (savedUser == null || savedPass == null) {
//                Toast.makeText(this, "Chưa có tài khoản, vui lòng đăng ký", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            if (inputUser.equals(savedUser) && inputPass.equals(savedPass)) {
//                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(this, HomeActivity.class));
//                finish();
//            } else {
//                Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        btnToRegister.setOnClickListener(v -> {
//            startActivity(new Intent(this, RegisterActivity.class));
//        });
//    }
//    private void getData() {
//        // RequestQueue initialized
//        mRequestQueue = Volley.newRequestQueue(this);
//
//        // String Request initialized
//        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                Toast.makeText(getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.i(TAG, "Error :" + error.toString());
//            }
//        });
//
//        mRequestQueue.add(mStringRequest);
//    }
//}
package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnLogin, btnToRegister;

    private RequestQueue mRequestQueue;
    private String url = "https://fakestoreapi.com/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnToRegister = findViewById(R.id.btnToRegister);

        btnLogin.setOnClickListener(v -> {
            String inputUser  = etUsername.getText().toString().trim();
            String inputPass = etPassword.getText().toString().trim();

            if (inputUser .isEmpty() || inputPass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ tên và mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            getData(inputUser , inputPass);
        });

        btnToRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void getData(String username, String password) {
        mRequestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        boolean isValidUser  = false;

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject user = response.getJSONObject(i);
                                String apiUsername = user.getString("username");
                                String apiPassword = user.getString("password"); // Assuming password is available

                                if (username.equals(apiUsername) && password.equals(apiPassword)) {
                                    isValidUser  = true;
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                    finish();
                                    break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (!isValidUser ) {
                            Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "Error :" + error.toString());
                    }
                });

        mRequestQueue.add(jsonArrayRequest);
    }
}

