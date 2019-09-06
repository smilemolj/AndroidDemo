package library.base.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import library.base.R


/**
 * 菊花Dialog
 * Created by zhaoyuehai 2019/3/6
 */
class MyProgressDialog internal constructor(context: Context) :
    Dialog(context, R.style.loading_dialog) {

    private lateinit var tvTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.loading_dialog)
        tvTxt = findViewById(R.id.tv_txt)
    }

    fun setText(msg: String?) {
        if (msg.isNullOrEmpty()) {
            tvTxt.text = ""
            tvTxt.visibility = View.GONE
        } else {
            tvTxt.text = msg
            tvTxt.visibility = View.VISIBLE
        }
    }
}
