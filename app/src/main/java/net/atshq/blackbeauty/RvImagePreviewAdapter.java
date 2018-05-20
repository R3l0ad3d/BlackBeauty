package net.atshq.blackbeauty;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RvImagePreviewAdapter extends RecyclerView.Adapter<RvImagePreviewAdapter.ViewHolder> {
    private final Context context;
    private List<ImagePreviewItem> items;

    private int previousCheck=0;

    public RvImagePreviewAdapter(List<ImagePreviewItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ImagePreviewItem item = items.get(position);
        //TODO Fill in your logic for binding the view.
        holder.ivFilterPreview.setImageBitmap(item.getBitmap());
        holder.tvFilterName.setText(item.getName());

        if(item.isSelect()){
            holder.layout.setBackgroundColor(Color.WHITE);
        }else {
            holder.layout.setBackgroundColor(Color.BLACK);
        }

        holder.ivFilterPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.get(position).setSelect(true);
                items.get(previousCheck).setSelect(false);
                notifyItemChanged(position);
                notifyItemChanged(previousCheck);
                previousCheck=position;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //create variable your view item
        public ImageView ivFilterPreview;
        public TextView tvFilterName;
        public LinearLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            //initial variabls by findViewById or dataBinding
            ivFilterPreview = itemView.findViewById(R.id.ivFilterPreview);
            tvFilterName = itemView.findViewById(R.id.tvFilterName);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}