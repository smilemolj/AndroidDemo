package com.yuehai.android

import com.yuehai.android.util.FileUtil
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val url =
        "https://www.xxx.com/a.apk"

    @Test
    fun testGetFileName() {
        assertEquals("a.apk", FileUtil.getFileNameByUrl(url))
    }

    @Test
    fun testFileName() {
        assertEquals(true, FileUtil.isValidFileName("a.zip"))
    }
}
