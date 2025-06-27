package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ListView lvProducts;
    List<Product> productList;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lvProducts = findViewById(R.id.lvProducts);
        EditText etSearch = findViewById(R.id.etSearch);
        Button btnCart = findViewById(R.id.btnCart);
        etSearch.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        productList = new ArrayList<>();
        productList.add(new Product("Áo vest nâu", "Lịch sự sang trọng", 95000, R.drawable.c1));
        productList.add(new Product("Áo vest xanh", "Mang phong cách châu âu", 120000, R.drawable.c2));
        productList.add(new Product("Áo vest xám", "Lịch lãm, quý phái", 87000, R.drawable.c3));
        productList.add(new Product("Giày da", "Thanh lịch, thời trang", 180000, R.drawable.s1));
        productList.add(new Product("Giày da báo", "Phong cách trẻ trung", 115000, R.drawable.s2));
        productList.add(new Product("Giày đen", "Sang trọng, lịch lãm", 115000, R.drawable.s5));
        productList.add(new Product("Giày thể thao", "Êm ái, thích hợp cho việc chạy bộ", 115000, R.drawable.s5));
        productList.add(new Product("Áo thun trơn", "Thoáng mát dành cho những buổi hè, 2 màu đỏ và đen", 89000, R.drawable.t1));
        productList.add(new Product("Áo thun trơn", "Thoáng mát dành cho những buổi hè, 2 màu đen và xanh xám", 89000, R.drawable.t2));
        productList.add(new Product("Áo thun trơn", "Thoáng mát dành cho những buổi hè, 2 màu nâu và đen", 89000, R.drawable.t3));
        productList.add(new Product("Áo thun trơn", "Thoáng mát dành cho những buổi hè, 2 màu xanh lá và đen", 89000, R.drawable.t4));

        adapter = new ProductAdapter(this, productList);
        lvProducts.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().toLowerCase().trim();
                List<Product> filtered = new ArrayList<>();
                if (query.isEmpty()) {
                    filtered.addAll(productList);
                } else {
                    for (Product p : productList) {
                        if (p.getName().toLowerCase().contains(query)) {
                            filtered.add(p);
                        }
                    }
                }
                adapter.filterList(filtered);
            }
        });

        btnCart.setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class));
        });

        lvProducts.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(this, ProductDetailActivity.class);
            i.putExtra("product", adapter.getItem(position));
            startActivity(i);
        });
    }
}
