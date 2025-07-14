package com.example.myapplication;

import android.content.Context;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.VH> {
    private final List<Product> fullList;    // Toàn bộ data
    private final List<Product> list;        // Data hiển thị
    private final Context ctx;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Product p);
    }

    public ProductAdapter(Context ctx, List<Product> list, OnItemClickListener l) {
        this.ctx      = ctx;
        this.list     = list;
        this.listener = l;
        this.fullList = new ArrayList<>();
    }

    /** Gọi sau khi lấy xong data để cập nhật cả fullList và list */
    public void updateData(List<Product> newData) {
        fullList.clear();
        fullList.addAll(newData);

        list.clear();
        list.addAll(newData);

        notifyDataSetChanged();
    }

    /** Lọc theo title, query empty → show lại all */
    public void filter(String query) {
        list.clear();
        if (query.isEmpty()) {
            list.addAll(fullList);
        } else {
            String q = query.toLowerCase();
            for (Product p : fullList) {
                if (p.getTitle().toLowerCase().contains(q)) {
                    list.add(p);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_product, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Product p = list.get(pos);
        h.tvTitle.setText(p.getTitle());
        h.tvPrice.setText(String.format("%,d VND", (int)p.getPrice()).replace(',', '.'));
        h.rbRate.setRating((float)p.getRatingRate());
        Glide.with(ctx).load(p.getImageUrl()).into(h.ivImage);
        h.itemView.setOnClickListener(v -> listener.onItemClick(p));
    }

    @Override
    public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvTitle, tvPrice;
        RatingBar rbRate;
        VH(@NonNull View v) {
            super(v);
            ivImage = v.findViewById(R.id.ivImage);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvPrice = v.findViewById(R.id.tvPrice);
            rbRate  = v.findViewById(R.id.rbRate);
        }
    }
}
