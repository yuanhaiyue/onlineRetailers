package com.yuan.online.util



import com.yuan.online.common.Constant
import org.apache.tomcat.util.codec.binary.Base64
import java.security.MessageDigest


/**
 * 描述： MD5工具
 */

class MD5Utils {

    companion object{
        @JvmStatic
        fun getMD5Str(strValue:String):String{
            val md5:MessageDigest= MessageDigest.getInstance("MD5")
            return Base64.encodeBase64String(md5.digest((strValue+Constant.SALT).toByteArray()))
        }
    }

}
fun main(args: Array<String>){
    val md5="1234"
    println(MD5Utils.getMD5Str(md5))
}


