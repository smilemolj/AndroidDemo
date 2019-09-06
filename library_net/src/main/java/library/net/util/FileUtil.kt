package library.net.util

import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


/**
 * Created by zhaoyuehai 2019/9/5
 */
object FileUtil {

    fun getLocalFilePath(paths: Array<String>): String {
        val pathStr = StringBuilder(Environment.getExternalStorageDirectory().path)
        pathStr.append(File.separator)
        for (path in paths) {
            pathStr.append(path)
            pathStr.append(File.separator)
        }
        return pathStr.toString()
    }

    fun writeFile(inputString: InputStream, filePath: String, fileName: String): File? {
        //建立一个文件
        return try {
            val file = File(filePath)
            if (!file.exists() || !file.isDirectory) {
                val mkdirs = file.mkdirs()//创建文件夹
                if (!mkdirs) {
                    return null
                }
            }
            val resultFile = File(filePath + fileName)
            val fos: FileOutputStream
            fos = FileOutputStream(resultFile)
            val b = ByteArray(1024)
            var len: Int
            do {
                len = inputString.read(b)
                if (len != -1) {
                    fos.write(b, 0, len)
                }
            } while (len != -1)
            inputString.close()
            fos.close()
            resultFile
        } catch (e: IOException) {
            null
        }
    }
}