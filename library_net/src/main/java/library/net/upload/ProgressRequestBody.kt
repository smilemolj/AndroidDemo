package library.net.upload

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.*
import java.io.File
import java.io.IOException


/**
 * 文件上传请求体
 * @param contentName 如："file"
 *@param contentType 如：MediaType.parse("image/png")
 */
class ProgressRequestBody internal constructor(
    files: List<File>,
    contentName: String,
    contentType: MediaType?,
    private val uploadListener: UploadListener
) : RequestBody() {
    private val requestBody: MultipartBody

    init {
        val builder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
        for (file in files) {
            val requestBody = create(contentType, file)
            builder.addFormDataPart(contentName, file.name, requestBody)
        }
        requestBody = builder.build()
    }

    override fun contentType(): MediaType? {
        return requestBody.contentType()
    }

    override fun contentLength(): Long {
        return requestBody.contentLength()
    }

    override fun writeTo(sink: BufferedSink) {
        val countingSink = CountingSink(sink)
        val bufferedSink = Okio.buffer(countingSink)
        //写入
        requestBody.writeTo(bufferedSink)
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush()
    }

    private inner class CountingSink(delegate: Sink) : ForwardingSink(delegate) {
        private var bytesWritten = 0L
        private val totalLength = contentLength()
        @Throws(IOException::class)
        override fun write(source: Buffer, byteCount: Long) {
            bytesWritten += byteCount
            uploadListener.onProgress(bytesWritten, totalLength)
            super.write(source, byteCount)
        }
    }
}
