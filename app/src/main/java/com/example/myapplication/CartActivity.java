package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CartActivity extends AppCompatActivity {
    ListView lvCart;
    TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lvCart = findViewById(R.id.lvCart);
        tvTotal = findViewById(R.id.tvTotal);

        ProductAdapter adapter = new ProductAdapter(this, Cart.cartItems);
        lvCart.setAdapter(adapter);

        final double[] total = {0};
        for (Product p : Cart.cartItems) {
            total[0] += p.getPrice();
        }
        tvTotal.setText("Tổng: " + total[0] + " VND");
        lvCart.setOnItemLongClickListener((parent, view, position, id) -> {
            Cart.cartItems.remove(position);
            adapter.notifyDataSetChanged();
            for (Product p : Cart.cartItems) {
                total[0] += p.getPrice();
            }
            tvTotal.setText("Tổng: " + total[0] + " VND");
            return true;
        });
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

    }

}
