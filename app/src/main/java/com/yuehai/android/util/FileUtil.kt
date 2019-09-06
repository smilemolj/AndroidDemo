package com.yuehai.android.util

/**
 * Created by zhaoyuehai 2019/9/6
 */
object FileUtil {
    fun getFileNameByUrl(url: String): String? {
        return if (url.contains("/") && !url.endsWith("/") && url.length > 1) {
            val indexOfLast = url.indexOfLast { it == '/' }
            val fileName = url.subSequence(IntRange(indexOfLast + 1, url.length - 1)).toString()
            if (isValidFileName(fileName)) {
                fileName
            } else {
                null
            }
        } else {
            null
        }
    }

    /**
     * 1.首尾不能有空字符(空格、制表符、换页符等空白字符的其中任意一个),文件名尾不能为.号

    2.文件名和扩展名不能同时为空

    3.文件名中不能包含\/:*?"<>|中的任意字符

    4.文件名(包括扩展名)的长度不得大于255个字符

    5.在1.的条件下,文件名中不能出出现除空格符外的任意空字符.
     */
    fun isValidFileName(fileName: String?): Boolean {
        return if (fileName == null || fileName.length > 255) false else fileName.matches("[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$".toRegex())
    }
}