package com.lessons.gar.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Garik on 25-Oct-18, 11:37
 */
public class AdapterItems extends RecyclerView.Adapter {
    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    enum VIEW_TYPE {
        VIEW_ITEM, VIEW_PROG
    }

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    private ArrayList<ListItemModel> mItems = new ArrayList<>();
    private Context mContext;

    AdapterItems(Context mContext, RecyclerView recyclerView) {
        this.mContext = mContext;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        //ToDo: This is the end, my little friend, the end
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                            loading = true;
                        }
                    }
                }
            });
        }
    }

    void setmItems(ArrayList<ListItemModel> mItems) {
        this.mItems = mItems;
    }

    void setLoaded() {
        loading = false;
    }

    void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position) != null ? VIEW_TYPE.VIEW_ITEM.ordinal() : VIEW_TYPE.VIEW_PROG.ordinal();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE.VIEW_ITEM.ordinal()) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, viewGroup, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.progress_bar, viewGroup, false);
            return new ProgressViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ItemViewHolder) {
            ListItemModel model = mItems.get(i);
            ((ItemViewHolder) viewHolder).mItemImage.setImageResource(model.getImgResId());
            ((ItemViewHolder) viewHolder).mItemTitle.setText("Title " + model.getTitle());
            ((ItemViewHolder) viewHolder).mItemDesc.setText("Desc " + model.getDescription());
        } else if (viewHolder instanceof ProgressViewHolder) {
            ((ProgressViewHolder) viewHolder).mProgress.setIndeterminate(true);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView mItemImage;
        TextView mItemTitle;
        TextView mItemDesc;

        ItemViewHolder(View v) {
            super(v);
            mItemImage = v.findViewById(R.id.item_image);
            mItemTitle = v.findViewById(R.id.item_title);
            mItemDesc = v.findViewById(R.id.item_desc);
        }
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        ProgressBar mProgress;

        ProgressViewHolder(View v) {
            super(v);
            mProgress = v.findViewById(R.id.progress_bar);
        }
    }
}
