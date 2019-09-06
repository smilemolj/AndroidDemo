package com.yuehai.android.ui.adapter

import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import com.yuehai.android.R
import com.yuehai.android.net.response.UserForListBean
import com.yuehai.android.widget.recyclerhelper.BaseViewHolder

/**
 * Created by zhaoyuehai 2019/3/28
 */
class UserListViewHolder(parent: ViewGroup, private val mOnEditClickListener: OnEditClickListener) :
    BaseViewHolder<UserForListBean>(parent, R.layout.user_list_item) {
    private val nameTV = findViewById<TextView>(R.id.name_tv)
    private val nickTV = findViewById<TextView>(R.id.nick_tv)
    private val emailTV = findViewById<TextView>(R.id.email_tv)
    private val phoneTV = findViewById<TextView>(R.id.phone_tv)
    private val deleteBTN = findViewById<Button>(R.id.delete_btn)
    private val modifyBTN = findViewById<Button>(R.id.modify_btn)

    override fun setData(data: UserForListBean) {
        super.setData(data)
        nameTV.text = String.format("用户名:%s", data.userName)
        nickTV.text = String.format("昵称:%s", data.nickName)
        phoneTV.text = String.format("手机:%s", data.phone)
        emailTV.text = String.format("邮箱:%s", data.email)
        deleteBTN.setOnClickListener { mOnEditClickListener.onDeleteClick(adapterPosition) }
        modifyBTN.setOnClickListener { mOnEditClickListener.onModifyClick(adapterPosition) }
    }

    interface OnEditClickListener {
        fun onDeleteClick(position: Int)

        fun onModifyClick(position: Int)
    }
}
