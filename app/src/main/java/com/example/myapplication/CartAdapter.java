// CartAdapter.java
package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.VH> {
    private List<Product> list;
    private Context ctx;
    private Runnable updateCb;

    public CartAdapter(Context c, List<Product> l, Runnable cb) {
        this.ctx = c;
        this.list = l;
        this.updateCb = cb;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_product, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Product p = list.get(pos);
        h.tvTitle.setText(p.getTitle());
        h.tvPrice.setText(String.format("%,d VND", (int)p.getPrice()).replace(',', '.'));
        Glide.with(ctx).load(p.getImageUrl()).into(h.ivImage);

        h.itemView.setOnLongClickListener(v -> {
            list.remove(pos);
            updateCb.run();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvTitle, tvPrice;

        VH(@NonNull View v) {
            super(v);
            ivImage = v.findViewById(R.id.ivImage);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvPrice = v.findViewById(R.id.tvPrice);
        }
    }
}
