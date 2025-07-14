package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView rvProducts;
    private ProductAdapter adapter;
    private List<Product> productList;
    private static final String URL = "https://fakestoreapi.com/products";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 1. Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 2. RecyclerView
        rvProducts = findViewById(R.id.rvProducts);
        rvProducts.setLayoutManager(new GridLayoutManager(this, 2));

        // 3. Adapter & list
        productList = new ArrayList<>();
        adapter = new ProductAdapter(this, productList, product -> {
            Intent i = new Intent(this, ProductDetailActivity.class);
            i.putExtra("product", product);
            startActivity(i);
        });
        rvProducts.setAdapter(adapter);

        // 4. Nút giỏ hàng:
        //    - nếu rỗng => show Snackbar cam
        //    - nếu có item => sang CartActivity
        ImageButton btnCart = findViewById(R.id.btnCart);
        btnCart.setOnClickListener(v -> {
            if (Cart.cartItems.isEmpty()) {
                Snackbar sb = Snackbar.make(
                        findViewById(android.R.id.content),
                        "Hãy thêm sản phẩm vào giỏ hàng",
                        Snackbar.LENGTH_SHORT
                );
                sb.setBackgroundTint(
                        ContextCompat.getColor(this, R.color.orange_primary)
                );
                sb.setTextColor(
                        ContextCompat.getColor(this, android.R.color.white)
                );
                sb.show();
            } else {
                startActivity(new Intent(this, CartActivity.class));
            }
        });

        // 5. Tìm kiếm
        EditText etSearch = findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int st, int b, int cnt) {
                adapter.filter(s.toString().trim());
            }
        });

        // 6. Lấy data ngay khi vào Home
        fetchData();
    }

    private void fetchData() {
        JsonArrayRequest req = new JsonArrayRequest(
                Request.Method.GET, URL, null,
                response -> {
                    List<Product> temp = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject o = response.getJSONObject(i);
                            JSONObject r = o.getJSONObject("rating");
                            temp.add(new Product(
                                    o.getInt("id"),
                                    o.getString("title"),
                                    o.getString("description"),
                                    o.getDouble("price"),
                                    o.getString("category"),
                                    o.getString("image"),
                                    r.getDouble("rate"),
                                    r.getInt("count")
                            ));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.updateData(temp);
                },
                error -> Toast.makeText(this,
                        "Lỗi tải dữ liệu",
                        Toast.LENGTH_SHORT
                ).show()
        );
        Volley.newRequestQueue(this).add(req);
    }
}
