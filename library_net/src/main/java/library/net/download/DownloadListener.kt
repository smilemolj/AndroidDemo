package library.net.download

/**
 * 下载进度监听
 * 注意此回调发生在子线程
 * Created by zhaoyuehai 2019/9/5
 */
interface DownloadListener {

    /**
     * 下载进度【发生在子线程】
     * @param currentLength 已经上传大小 单位MB
     * @param totalLength 文件总大小 单位MB
     */
    fun onProgress(currentLength: Long, totalLength: Long)
}
