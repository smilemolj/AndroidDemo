package library.net.converter

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonToken
import library.net.ResponseVerify
import library.net.exception.ApiException
import okhttp3.ResponseBody
import org.json.JSONException
import retrofit2.Converter
import java.io.IOException
import java.io.StringReader

/**
 * 请求响应的Gson自动转换器，添加异常处理的操作  code!=10000为异常
 * Created by zhaoyuehai 2018/7/30
 */
class MyGsonResponseBodyConverter<T>(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>,
    private val verify: ResponseVerify?

) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        val response = value.string()
        try {
            try {
                verify?.verify(response)
            } catch (e: JSONException) {
                e.printStackTrace()
                throw ApiException(ApiException.JSON_EXCEPTION, e.message)
            }
            val jsonReader = gson.newJsonReader(StringReader(response))
            val result = adapter.read(jsonReader)
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw JsonIOException("JSON document was not fully consumed.")
            }
            return result
        } finally {
            value.close()
        }
    }
}
