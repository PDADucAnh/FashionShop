package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

public class ProductDetailActivity extends AppCompatActivity {
    ImageView ivImage;
    TextView tvTitle, tvCategory, tvRatingCount, tvPrice, tvDesc;
    RatingBar rbRate;
    Product product;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_product_detail);
        // ------- toolbar & back navigation -------
        MaterialToolbar toolbar = findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        // -----------------------------------------

        // phần mapping view và load dữ liệu của bạn tiếp ở đây…
        ivImage = findViewById(R.id.ivImage);
        tvTitle = findViewById(R.id.tvTitle);
        tvCategory = findViewById(R.id.tvCategory);
        rbRate = findViewById(R.id.rbRate);
        tvRatingCount = findViewById(R.id.tvRatingCount);
        tvPrice = findViewById(R.id.tvPrice);
        tvDesc = findViewById(R.id.tvDesc);
        Button btnAdd = findViewById(R.id.btnAddToCart);

        product = (Product) getIntent().getSerializableExtra("product");
        if (product != null) {
            Glide.with(this).load(product.getImageUrl()).into(ivImage);
            tvTitle.setText(product.getTitle());
            tvCategory.setText("Thể loại: " + product.getCategory());
            rbRate.setRating((float)product.getRatingRate());
            tvRatingCount.setText("(" + product.getRatingCount() + " đánh giá)");
            tvPrice.setText(String.format("%,d VND", (int)product.getPrice()).replace(',', '.'));
            tvDesc.setText(product.getDescription());
        }

        btnAdd.setOnClickListener(v -> {
            Cart.cartItems.add(product);
            Toast.makeText(this, "Đã thêm vào giỏ", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
