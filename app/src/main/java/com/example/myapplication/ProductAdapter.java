package com.example.myapplication;

import android.content.Context;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> list) {
        super(context, 0, list);
        this.context = context;
        this.productList = new ArrayList<>(list);
    }

    private String formatPrice(double price) {
        int priceInt = (int) price;
        return String.format("%,d", priceInt).replace(',', '.') + " VND";
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product product = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);
        ImageView ivImage = convertView.findViewById(R.id.ivImage);

        tvName.setText(product.getName());
        tvPrice.setText(formatPrice(product.getPrice()));
        ivImage.setImageResource(product.getImageResId());

        return convertView;
    }

    public void filterList(List<Product> filteredList) {
        productList.clear();
        productList.addAll(filteredList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Nullable
    @Override
    public Product getItem(int position) {
        return productList.get(position);
    }
}
