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
import kotlinx.android.synthetic.main.input_layout.*

/**
 * 单个输入框的DialogFragment
 * Created by zhaoyuehai 2019/3/29
 */
class InputDialogFragment : DialogFragment(), View.OnClickListener {

    companion object {
        fun newInstance(title: String, content: String?): InputDialogFragment {
            val fragment = InputDialogFragment()
            val bundle = Bundle()
            bundle.putString("title", title)
            if (content != null)
                bundle.putString("content", content)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var mOnClickListener: OnClickListener? = null

    interface OnClickListener {
        /**
         * 会提前dismiss
         *
         * @param content 输入内容
         */
        fun onConfirm(content: String)

        /**
         * 会提前dismiss
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
        return inflater.inflate(R.layout.input_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val title = arguments?.getString("title")
            if (title != null)
                input_tv.text = title
            val content = arguments?.getString("content")
            if (content != null) {
                input_et.setText(content)
                input_et.setSelection(content.length)
            }
        }
        confirm_btn.setOnClickListener(this)
        cancel_btn.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        // 一定要设置Background，如果不设置，window属性设置无效
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dm = DisplayMetrics()
        if (activity != null && activity!!.windowManager != null)
            activity!!.windowManager.defaultDisplay.getMetrics(dm)
        val params = dialog.window?.attributes
        params?.gravity = Gravity.CENTER
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params?.width = ViewGroup.LayoutParams.MATCH_PARENT
        params?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = params
    }

    override fun onClick(v: View) {
        if (mOnClickListener == null) return
        when (v.id) {
            R.id.confirm_btn -> {
                val text = input_et.text
                if (text != null && text.toString() != "") {
                    dismiss()
                    mOnClickListener?.onConfirm(text.toString())
                }
            }
            R.id.cancel_btn -> {
                dismiss()
                mOnClickListener?.onCancel()
            }
        }
    }
}
