/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yuehai.android.widget.recyclerhelper;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class RecyclerArrayAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected List<T> mObjects;
    protected ArrayList<ItemView> headers = new ArrayList<>();
    protected ArrayList<ItemView> footers = new ArrayList<>();

    protected OnItemClickListener mItemClickListener;
    protected OnItemLongClickListener mItemLongClickListener;

    public interface ItemView {
        View onCreateView(ViewGroup parent);

        void onBindView(View headerView);
    }

    /**
     * Lock used to modify the content of {@link #mObjects}. Any write operation
     * performed on the array should be synchronized on this lock.
     */
    private final Object mLock = new Object();


    /**
     * Indicates whether or not {@link #notifyDataSetChanged()} must be called whenever
     * {@link #mObjects} is modified.
     */
    private boolean mNotifyOnChange = true;


    /**
     * Constructor
     */
    public RecyclerArrayAdapter() {
        init(new ArrayList<T>());
    }


    /**
     * Constructor
     *
     * @param objects The objects to represent in the ListView.
     */
    public RecyclerArrayAdapter(T[] objects) {
        init(Arrays.asList(objects));
    }


    private void init(List<T> objects) {
        mObjects = objects;
    }

    public void addHeader(ItemView view) {
        if (view == null) throw new NullPointerException("ItemView can't be null");
        headers.add(view);
        notifyItemInserted(headers.size() - 1);
    }

    public void addFooter(ItemView view) {
        if (view == null) throw new NullPointerException("ItemView can't be null");
        footers.add(view);
        notifyItemInserted(headers.size() + getCount() + footers.size() - 1);
    }

    public void removeAllHeader() {
        int count = headers.size();
        headers.clear();
        notifyItemRangeRemoved(0, count);
    }

    public void removeAllFooter() {
        int count = footers.size();
        footers.clear();
        notifyItemRangeRemoved(headers.size() + getCount(), count);
    }

    public ItemView getHeader(int index) {
        return headers.get(index);
    }

    public ItemView getFooter(int index) {
        return footers.get(index);
    }

    public int getHeaderCount() {
        return headers.size();
    }

    public int getFooterCount() {
        return footers.size();
    }

    public void removeHeader(ItemView view) {
        int position = headers.indexOf(view);
        headers.remove(view);
        notifyItemRemoved(position);
    }

    public void removeFooter(ItemView view) {
        int position = headers.size() + getCount() + footers.indexOf(view);
        footers.remove(view);
        notifyItemRemoved(position);
    }

    /**
     * Adds the specified object at the end of the array.
     *
     * @param object The object to add at the end of the array.
     */
    public void add(T object) {
        if (object != null) {
            synchronized (mLock) {
                mObjects.add(object);
            }
            if (mNotifyOnChange) notifyItemInserted(headers.size() + getCount() + 1);
        }
    }

    /**
     * Adds the specified Collection at the end of the array.
     *
     * @param collection The Collection to add at the end of the array.
     */
    public void addAll(Collection<? extends T> collection) {
        if (collection != null && collection.size() != 0) {
            synchronized (mLock) {
                mObjects.addAll(collection);
            }
            int dataCount = collection.size();
            if (mNotifyOnChange)
                notifyItemRangeInserted(headers.size() + getCount() - dataCount + 1, dataCount);
        }

    }

    /**
     * Adds the specified items at the end of the array.
     *
     * @param items The items to add at the end of the array.
     */
    public void addAll(T[] items) {
        if (items != null && items.length != 0) {
            synchronized (mLock) {
                Collections.addAll(mObjects, items);
            }
            int dataCount = items.length;
            if (mNotifyOnChange)
                notifyItemRangeInserted(headers.size() + getCount() - dataCount + 1, dataCount);
        }
    }

    /**
     * 插入，不会触发任何事情
     *
     * @param object The object to insert into the array.
     * @param index  The index at which the object must be inserted.
     */
    public void insert(T object, int index) {
        synchronized (mLock) {
            mObjects.add(index, object);
        }
        if (mNotifyOnChange) notifyItemInserted(headers.size() + index + 1);
    }

    /**
     * 插入数组，不会触发任何事情
     *
     * @param object The object to insert into the array.
     * @param index  The index at which the object must be inserted.
     */
    public void insertAll(T[] object, int index) {
        synchronized (mLock) {
            mObjects.addAll(index, Arrays.asList(object));
        }
        int dataCount = object.length;
        if (mNotifyOnChange) notifyItemRangeInserted(headers.size() + index + 1, dataCount);
    }

    /**
     * 插入数组，不会触发任何事情
     *
     * @param object The object to insert into the array.
     * @param index  The index at which the object must be inserted.
     */
    public void insertAll(Collection<? extends T> object, int index) {
        synchronized (mLock) {
            mObjects.addAll(index, object);
        }
        int dataCount = object.size();
        if (mNotifyOnChange) notifyItemRangeInserted(headers.size() + index + 1, dataCount);
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
                if (mNotifyOnChange) notifyItemRemoved(headers.size() + position);
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
        if (mNotifyOnChange) notifyItemRemoved(headers.size() + position);
    }


    /**
     * 触发清空
     * 与{@link #clear()}的不同仅在于这个使用notifyItemRangeRemoved.
     * 猜测这个方法与add伪并发执行的时候会造成"Scrapped or attached views may not be recycled"的Crash.
     * 所以建议使用{@link #clear()}
     */
    public void removeAll() {
        int count = mObjects.size();
        synchronized (mLock) {
            mObjects.clear();
        }
        if (mNotifyOnChange) notifyItemRangeRemoved(headers.size(), count);
    }

    /**
     * 触发清空
     */
    public void clear() {
        synchronized (mLock) {
            mObjects.clear();
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    /**
     * Sorts the content of this adapter using the specified comparator.
     *
     * @param comparator The comparator used to sort the objects contained
     *                   in this adapter.
     */
    public void sort(Comparator<? super T> comparator) {
        synchronized (mLock) {
            Collections.sort(mObjects, comparator);
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }


    /**
     * Control whether methods that change the list ({@link #add},
     * {@link #insert}, {@link #remove}, {@link #clear}) automatically call
     * {@link #notifyDataSetChanged}.  If set to false, caller must
     * manually call notifyDataSetChanged() to have the changes
     * reflected in the attached view.
     * <p>
     * The default is true, and calling notifyDataSetChanged()
     * resets the flag to true.
     *
     * @param notifyOnChange if true, modifications to the list will
     *                       automatically call {@link
     *                       #notifyDataSetChanged}
     */
    public void setNotifyOnChange(boolean notifyOnChange) {
        mNotifyOnChange = notifyOnChange;
    }


    /**
     * 这个函数包含了头部和尾部view的个数，不是真正的item个数。
     *
     * @return
     */
    @Override
    public final int getItemCount() {
        return mObjects.size() + headers.size() + footers.size();
    }

    /**
     * 应该使用这个获取item个数
     *
     * @return
     */
    public int getCount() {
        return mObjects.size();
    }

    private View createSpViewByType(ViewGroup parent, int viewType) {
        for (ItemView headerView : headers) {
            if (headerView.hashCode() == viewType) {
                View view = headerView.onCreateView(parent);
                StaggeredGridLayoutManager.LayoutParams layoutParams;
                if (view.getLayoutParams() != null)
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(view.getLayoutParams());
                else
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setFullSpan(true);
                view.setLayoutParams(layoutParams);
                return view;
            }
        }
        for (ItemView footerview : footers) {
            if (footerview.hashCode() == viewType) {
                View view = footerview.onCreateView(parent);
                StaggeredGridLayoutManager.LayoutParams layoutParams;
                if (view.getLayoutParams() != null)
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(view.getLayoutParams());
                else
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setFullSpan(true);
                view.setLayoutParams(layoutParams);
                return view;
            }
        }
        return null;
    }

    @NonNull
    @Override
    public final BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = createSpViewByType(parent, viewType);
        if (view != null) {
            return new StateViewHolder(view);
        }
        final BaseViewHolder viewHolder = onMyCreateViewHolder(parent, viewType);
        //itemView 的点击事件
        if (mItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(v -> mItemClickListener.onItemClick(viewHolder.getAdapterPosition() - headers.size()));
        }
        if (mItemLongClickListener != null) {
            viewHolder.itemView.setOnLongClickListener(v -> mItemLongClickListener.onItemLongClick(viewHolder.getAdapterPosition() - headers.size()));
        }
        return viewHolder;
    }

    public abstract BaseViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType);


    @Override
    public final void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.itemView.setId(position);
        if (headers.size() != 0 && position < headers.size()) {
            headers.get(position).onBindView(holder.itemView);
            return;
        }

        int i = position - headers.size() - mObjects.size();
        if (footers.size() != 0 && i >= 0) {
            footers.get(i).onBindView(holder.itemView);
            return;
        }
        onMyBindViewHolder(holder, position - headers.size());
    }


    public void onMyBindViewHolder(BaseViewHolder holder, final int position) {
        holder.setData(getItem(position));
    }


    @Deprecated
    @Override
    public final int getItemViewType(int position) {
        if (headers.size() != 0) {
            if (position < headers.size()) return headers.get(position).hashCode();
        }
        if (footers.size() != 0) {
            /*
            eg:
            0:header1
            1:header2   2
            2:object1
            3:object2
            4:object3
            5:object4
            6:footer1   6(position) - 2 - 4 = 0
            7:footer2
             */
            int i = position - headers.size() - mObjects.size();
            if (i >= 0) {
                return footers.get(i).hashCode();
            }
        }
        return getViewType(position - headers.size());
    }

    public int getViewType(int position) {
        return 0;
    }


    public List<T> getAllData() {
        return new ArrayList<>(mObjects);
    }

    /**
     * {@inheritDoc}
     */
    public T getItem(int position) {
        return mObjects.get(position);
    }

    /**
     * Returns the position of the specified item in the array.
     *
     * @param item The item to retrieve the position of.
     * @return The position of the specified item.
     */
    public int getPosition(T item) {
        return mObjects.indexOf(item);
    }

    /**
     * {@inheritDoc}
     */
    public long getItemId(int position) {
        return position;
    }

    private class StateViewHolder extends BaseViewHolder {

        public StateViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mItemLongClickListener = listener;
    }
}
