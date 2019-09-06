package library.net.converter

import library.net.exception.ApiException
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken

import org.json.JSONException
import org.json.JSONObject

import java.io.IOException
import java.io.StringReader

import okhttp3.ResponseBody
import retrofit2.Converter

/**
 * 请求响应的Gson自动转换器，添加异常处理的操作  code!=10000为异常
 * Created by zhaoyuehai 2018/7/30
 */
class MyGsonResponseBodyConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) :
    Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        val response = value.string()
        try {
            try {
                verify(response)
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

    @Throws(JSONException::class)
    private fun verify(response: String) {
        val jsonObject = JSONObject(response)
        if (jsonObject.has("code") && !jsonObject.isNull("code")) {
            //{"code":"10000","message":"操作成功","data":{"connection":"1"},"serviceCode":"3.0"}
            //{"code":"00000","message":"请求失败","data":{"message":"Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: \"defect\""},"serviceCode":"3.0"}
            val code = jsonObject.getString("code")
            if (code != "10000") {
                val message = jsonObject.getString("message")
                throw ApiException(code.toInt(), message)
            }
        }
    }
}
