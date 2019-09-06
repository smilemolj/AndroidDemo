package com.yuehai.android.widget.recyclerhelper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 普通的RecycleAdapter
 * Created by 月海 on 2016/11/14.
 */

public abstract class CommonRecycleAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {
    private List<T> mObjects;
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;

    public CommonRecycleAdapter() {
        this.mObjects = new ArrayList<>();
    }

    public CommonRecycleAdapter(List<T> mList) {
        this.mObjects = mList;
    }

    private final Object mLock = new Object();

    public void addAll(Collection<? extends T> collection) {
        if (collection != null && collection.size() != 0) {
            synchronized (mLock) {
                mObjects.addAll(collection);
            }
            int dataCount = collection.size();
            notifyItemRangeInserted(getItemCount() - dataCount + 1, dataCount);
        }
    }

    public List<T> getList() {
        return mObjects;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    /**
     * 删除，不会触发任何事情
     *
     * @param object The object to remove.
     */
    public void remove(T object) {
        int position = mObjects.indexOf(object);
        synchronized (mLock) {
            if (mObjects.remove(object)) {
                notifyItemRemoved(position);
            }
        }
    }

    /**
     * 删除，不会触发任何事情
     *
     * @param position The position of the object to remove.
     */
    public void remove(int position) {
        synchronized (mLock) {
            mObjects.remove(position);
        }
        notifyItemRemoved(position);
    }

    /**
     * 触发清空
     */
    public void clear() {
        synchronized (mLock) {
            mObjects.clear();
        }
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mItemLongClickListener = listener;
    }

    @Override
    public final void onBindViewHolder(@NonNull final BaseViewHolder<T> holder, int position) {
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> mItemClickListener.onItemClick(holder.getAdapterPosition()));
        }
        if (mItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(v -> {
                mItemLongClickListener.onItemLongClick(holder.getAdapterPosition());
                return true;
            });
        }
        onMyBindViewHolder(holder);
        T item = getItem(position);
        holder.setData(item);
    }

    protected void onMyBindViewHolder(BaseViewHolder<T> holder) {
    }

    public T getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

}
