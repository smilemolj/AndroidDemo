package library.net.converter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import library.net.ResponseVerify
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Created by zhaoyuehai 2019/9/6
 */
class MyGsonConverterFactory(private val gson: Gson, private val verify: ResponseVerify?) :
    Converter.Factory() {
    constructor() : this(Gson(), null)

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return MyGsonResponseBodyConverter(gson, adapter, verify)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return MyGsonRequestBodyConverter(gson, adapter)
    }
}