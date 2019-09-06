package com.yuehai.android.widget.recyclerhelper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * M为这个itemView对应的model。
 * 使用RecyclerArrayAdapter就一定要用这个ViewHolder。
 * 这个ViewHolder将ItemView与Adapter解耦。
 * 推荐子类继承第二个构造函数。并将子类的构造函数设为一个ViewGroup parent。
 * 然后这个ViewHolder就完全独立。adapter在new的时候只需将parentView传进来。View的生成与管理由ViewHolder执行。
 * 实现setData来实现UI修改。Adapter会在onCreateViewHolder里自动调用。
 *
 *
 * 在一些特殊情况下，只能在setData里设置监听。
 *
 * @param <M>
</M> */
abstract class BaseViewHolder<M> : RecyclerView.ViewHolder {

    val context: Context
        get() = itemView.context

    constructor(itemView: View) : super(itemView)

    constructor(parent: ViewGroup, @LayoutRes res: Int) : super(
        LayoutInflater.from(parent.context).inflate(
            res,
            parent,
            false
        )
    )

    protected fun <T : View> findViewById(@IdRes id: Int): T {
        return itemView.findViewById(id)
    }

    open fun setData(data: M) {}
}