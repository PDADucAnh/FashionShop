package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rvCart;
    private TextView tvTotal;
    private Button btnCheckout;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // 1. Toolbar + nút back
        MaterialToolbar toolbar = findViewById(R.id.toolbarCart);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // 2. RecyclerView
        rvCart = findViewById(R.id.rvCart);
        rvCart.setLayoutManager(new LinearLayoutManager(this));

        // 3. Các view khác
        tvTotal     = findViewById(R.id.tvTotal);
        btnCheckout = findViewById(R.id.btnCheckout);

        // 4. Adapter và dữ liệu
        List<Product> items = Cart.cartItems;
        cartAdapter = new CartAdapter(this, items, this::updateTotal);
        rvCart.setAdapter(cartAdapter);

        // 5. Xử lý nút Thanh toán
        btnCheckout.setOnClickListener(v -> {
            if (items.isEmpty()) {
                Toast.makeText(
                        this,
                        "Giỏ hàng đang trống",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }
            Toast.makeText(
                    this,
                    "Thanh toán thành công",
                    Toast.LENGTH_LONG
            ).show();

            items.clear();
            updateTotal();

            Intent intent = new Intent(
                    this, HomeActivity.class
            );
            intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_NEW_TASK
            );
            startActivity(intent);
            finish();
        });

        // 6. Tính tổng ngay khi mở
        updateTotal();
    }

    private void updateTotal() {
        double sum = 0;
        for (Product p : Cart.cartItems) {
            sum += p.getPrice();
        }
        String formatted = String
                .format("%,d VND", (int) sum)
                .replace(',', '.');
        tvTotal.setText("Tổng: " + formatted);

        cartAdapter.notifyDataSetChanged();
    }
}
