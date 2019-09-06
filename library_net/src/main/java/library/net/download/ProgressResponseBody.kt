package library.net.download


import library.net.download.DownloadListener
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import java.io.IOException

/**
 * 文件下载请求体
 * Created by zhaoyuehai 2019/9/5
 */
class ProgressResponseBody(
    private val responseBody: ResponseBody?,
    private var downloadListener: DownloadListener
) : ResponseBody() {

    private var bufferedSource: BufferedSource? = null

    override fun contentType(): MediaType? {
        return responseBody?.contentType()
    }

    override fun contentLength(): Long {
        return responseBody?.contentLength() ?: 0L
    }

    override fun source(): BufferedSource? {
        if (bufferedSource == null && responseBody != null) {
            bufferedSource = Okio.buffer(source(responseBody.source()))
        }
        return bufferedSource
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            private var totalBytesRead = 0L
            private var old = 0L
            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                if (bytesRead != -1L) {
                    totalBytesRead += bytesRead
                }
                val l = totalBytesRead / 1024 / 1024
                if (old != l) {
                    old = l
                    downloadListener.onProgress(old, contentLength() / 1024 / 1024)
                }
                return bytesRead
            }
        }
    }
}
