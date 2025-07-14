package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final String AUTH_URL = "https://fakestoreapi.com/auth/login";

    private EditText etUsername, etPassword;
    private Button btnLogin, btnToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername    = findViewById(R.id.etUsername);
        etPassword    = findViewById(R.id.etPassword);
        btnLogin      = findViewById(R.id.btnLogin);
        btnToRegister = findViewById(R.id.btnToRegister);

        btnLogin.setOnClickListener(v -> {
            String user = etUsername.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this,
                        "Vui lòng nhập đầy đủ tên và mật khẩu",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Test với: user = "mor_2314", pass = "83r5^_"
            doLogin(user, pass);
        });

        btnToRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class))
        );
    }

    private void doLogin(String username, String password) {
        RequestQueue queue = Volley.newRequestQueue(this);

        // Chuẩn bị JSON body
        JSONObject body = new JSONObject();
        try {
            body.put("username", username);
            body.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.POST,
                AUTH_URL,
                body,
                response -> {
                    // Server trả về {"token":"..."} nếu login thành công
                    if (response.has("token")) {
                        Toast.makeText(this,
                                "Đăng nhập thành công",
                                Toast.LENGTH_SHORT).show();

                        startActivity(
                                new Intent(this, HomeActivity.class)
                        );
                        finish();
                    } else {
                        Toast.makeText(this,
                                "Đăng nhập thất bại",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // Bắt lỗi 4xx/5xx hoặc network error
                    NetworkResponse nr = error.networkResponse;
                    if (nr != null) {
                        int status = nr.statusCode;
                        String data = new String(nr.data);
                        Log.e(TAG, "HTTP " + status + " → " + data);
                        Toast.makeText(this,
                                "Lỗi " + status + ": kiểm tra username/password",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Log.e(TAG, "Volley error", error);
                        Toast.makeText(this,
                                "Không thể kết nối server. Kiểm tra Internet.",
                                Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Đảm bảo header Content-Type đúng
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        queue.add(req);
    }
}
