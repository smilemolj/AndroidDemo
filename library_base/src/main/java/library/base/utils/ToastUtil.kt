package library.base.utils

import android.content.Context
import android.text.TextUtils
import android.widget.Toast

import library.base.BaseApplication


object ToastUtil {

    private var oldMsg: String? = null
    private var toast: Toast? = null
    private var oneTime: Long = 0
    private var twoTime: Long = 0

    fun showToast(msg: String) {
        if (!TextUtils.isEmpty(msg)) {
            if (toast == null) {
                toast = Toast.makeText(BaseApplication.instance, msg, Toast.LENGTH_SHORT)
                toast!!.show()
                oneTime = System.currentTimeMillis()
            } else {
                twoTime = System.currentTimeMillis()
                if (msg == oldMsg) {
                    if (twoTime - oneTime > 1500) {
                        toast!!.show()
                    }
                } else {
                    oldMsg = msg
                    toast!!.setText(msg)
                    toast!!.show()
                }
            }
            oneTime = twoTime
        }
    }

    fun showToast(resId: Int) {
        showToast(BaseApplication.instance.getString(resId))
    }

    fun showToast(context: Context, msg: String, lengthShort: Int) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(context, msg, lengthShort).show()
        }
    }

}
