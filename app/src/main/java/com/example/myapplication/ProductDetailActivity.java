package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {
    TextView tvName, tvDesc, tvPrice;
    ImageView ivImage;
    Button btnAddToCart, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        tvName = findViewById(R.id.tvName);
        tvDesc = findViewById(R.id.tvDesc);
        tvPrice = findViewById(R.id.tvPrice);
        ivImage = findViewById(R.id.ivImage);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnBack = findViewById(R.id.btnBack);

        Product product = (Product) getIntent().getSerializableExtra("product");
        if (product != null) {
            tvName.setText(product.getName());
            tvDesc.setText(product.getDescription());
            tvPrice.setText(formatPrice(product.getPrice()));
            ivImage.setImageResource(product.getImageResId());
        }

        btnAddToCart.setOnClickListener(v -> {
            Cart.cartItems.add(product);
            finish();
        });

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

    }

    private String formatPrice(double price) {
        int priceInt = (int) price;
        return String.format("%,d", priceInt).replace(',', '.') + " VND";
    }
}
