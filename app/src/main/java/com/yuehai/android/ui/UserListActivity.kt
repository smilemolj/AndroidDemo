package com.yuehai.android.ui

import android.app.Activity
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuehai.android.R
import com.yuehai.android.contract.UserListContract
import com.yuehai.android.net.response.UserForListBean
import com.yuehai.android.presenter.UserListPresenter
import com.yuehai.android.ui.adapter.UserListViewHolder
import com.yuehai.android.widget.TipDialogFragment
import com.yuehai.android.widget.recyclerhelper.BaseViewHolder
import com.yuehai.android.widget.recyclerhelper.CommonRecycleAdapter
import com.yuehai.android.widget.recyclerhelper.MyDividerItemDecoration
import kotlinx.android.synthetic.main.activity_user_list.*
import library.base.BaseMvpActivity

/**
 * 用户列表 V
 * Created by zhaoyuehai 2019/3/22
 */
class UserListActivity : BaseMvpActivity<UserListContract.Presenter>(), UserListContract.View,
    UserListViewHolder.OnEditClickListener {

    private lateinit var adapter: CommonRecycleAdapter<UserForListBean>

    override val innerViewId: Int
        get() = R.layout.activity_user_list

    override val toolbarTitle: Int
        get() = R.string.user_list

    override fun createPresenter(): UserListContract.Presenter {
        return UserListPresenter(this)
    }

    override fun init() {
        super.init()
        smart_rl.setOnRefreshLoadMoreListener(presenter)
        adapter = object : CommonRecycleAdapter<UserForListBean>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): BaseViewHolder<UserForListBean> {
                return UserListViewHolder(parent, this@UserListActivity)
            }
        }
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(
            MyDividerItemDecoration(
                this,
                MyDividerItemDecoration.VERTICAL
            )
        )
        recycler_view.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_user_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {
                startActivityForResult(Intent(this, RegisterActivity::class.java), 3000)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 3000 && resultCode == Activity.RESULT_OK) {
            presenter.onRefresh(smart_rl)
        }
    }

    override fun showData(data: List<UserForListBean>?, isRefresh: Boolean) {
        if (data.isNullOrEmpty()) {
            if (isRefresh) {
                adapter.clear()
                smart_rl.finishRefresh()
            } else {
                smart_rl.finishLoadMoreWithNoMoreData()
            }
        } else {
            if (isRefresh) {
                adapter.clear()
                adapter.addAll(data)
                smart_rl.finishRefresh()
            } else {
                adapter.addAll(data)
                smart_rl.finishLoadMore(true)
            }
            smart_rl.setNoMoreData(false)//恢复有数据状态
        }
    }

    override fun alterConfirmDialog(
        msg: String,
        listener: TipDialogFragment.OnClickListener
    ) {
        TipDialogFragment(msg, listener).show(supportFragmentManager, "confirmDialog")
    }

    override fun onDeleteSuccess(userBean: UserForListBean) {
        adapter.remove(userBean)
    }

    override fun onDeleteClick(position: Int) {
        if (adapter.itemCount > position) {
            presenter.onDeleteClick(adapter.getItem(position))
        }
    }

    override fun onModifyClick(position: Int) {
        if (adapter.itemCount > position) {
            val item = adapter.getItem(position)
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra("isModify", true)
            intent.putExtra("id", item.id)
            intent.putExtra("userName", item.userName)
            intent.putExtra("phone", item.phone)
            intent.putExtra("email", item.email)
            intent.putExtra("nickName", item.nickName)
            startActivityForResult(intent, 3000)
        }
    }
}
