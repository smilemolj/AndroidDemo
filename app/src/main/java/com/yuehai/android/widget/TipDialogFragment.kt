package com.yuehai.android.widget

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.yuehai.android.R
import kotlinx.android.synthetic.main.tip_layout.*

/**
 * 提示信息DialogFragment
 * Created by zhaoyuehai 2019/3/29
 */
class TipDialogFragment : DialogFragment(), View.OnClickListener {

    private var mOnClickListener: OnClickListener? = null

    private var msg: String? = null

    interface OnClickListener {
        /**
         * 之后会dismiss
         */
        fun onConfirm()

        /**
         * 之后会dismiss
         */
        fun onCancel()
    }

    fun setOnConfirmListener(onClickListener: OnClickListener) {
        this.mOnClickListener = onClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.tip_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val msg = arguments!!.getString("msg")
            if (msg != null)
                tip_tv!!.text = msg
        }
        confirm_btn.setOnClickListener(this)
        cancel_btn.setOnClickListener(this)
    }

    fun setMessage(msg: String) {
        this.msg = msg
    }

    override fun onResume() {
        super.onResume()
        if (msg != null) tip_tv!!.text = msg
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null && dialog.window != null) {
            val win = dialog.window
            // 一定要设置Background，如果不设置，window属性设置无效
            win!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val dm = DisplayMetrics()
            if (activity != null && activity!!.windowManager != null)
                activity!!.windowManager.defaultDisplay.getMetrics(dm)
            val params = win.attributes
            params.gravity = Gravity.CENTER
            // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            win.attributes = params
        }
    }

    override fun onClick(v: View) {
        if (mOnClickListener == null) return
        when (v.id) {
            R.id.confirm_btn -> {
                mOnClickListener!!.onConfirm()
                dismiss()
            }
            R.id.cancel_btn -> {
                mOnClickListener!!.onCancel()
                dismiss()
            }
        }
    }
}
