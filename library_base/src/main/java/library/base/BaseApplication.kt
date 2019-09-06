package library.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import java.util.*
import kotlin.system.exitProcess

abstract class BaseApplication : MultiDexApplication(), Application.ActivityLifecycleCallbacks {

    companion object {
        lateinit var instance: BaseApplication private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        registerActivityLifecycleCallbacks(this)
    }

    /**
     * 所有activity管理
     */
    private val activityList = LinkedList<Activity>()
    /**
     * 正在运行的activity
     */
    protected val runningActivity= LinkedList<Activity>()

    val activityCount: Int
        get() = activityList.size

    val runningActivityCount: Int
        get() = runningActivity.size

    val topActivity: Activity?
        get() = if (runningActivity.size > 0) {
            runningActivity[runningActivity.size - 1]
        } else null

    val activity: Activity?
        get() = if (activityList.size > 0) {
            activityList[activityList.size - 1]
        } else null

    override fun onTerminate() {
        super.onTerminate()
        unregisterActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activityList.add(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        runningActivity.add(activity)
    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {
        runningActivity.remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        activityList.remove(activity)
    }

    /**
     * 只回收activity
     */
    fun finishAllActivity() {
        for (activity in activityList) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
    }

    /**
     * 是否还有正在运行的activity
     */
    fun hasAliveActivity(): Boolean {
        return activityList.size != 0
    }

    /**
     * 获取某个activity
     *
     * @param name activity的名称
     */
    fun getActivity(name: String): Activity? {
        var act: Activity? = null
        try {
            for (activity in activityList) {
                if (activity.localClassName.contains(name)) {
                    act = activity
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return act
    }

    fun exitApp() {
        if (hasAliveActivity()) {
            synchronized(this@BaseApplication) {
                finishAllActivity()
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(0)
    }
}
