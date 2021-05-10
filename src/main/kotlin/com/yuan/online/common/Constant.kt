package com.yuan.online.common

import lombok.Getter


/**
 * 描述
 */
class Constant {

    companion object{
        @JvmStatic
        val SALT:String="lkj+chh=fcnb666...[,"
        @JvmStatic
        val MALL_USER:String="mall_user"

        @JvmStatic
        val FILE_UPLOAD_DIR:String="C:\\Users\\14760\\Desktop\\images"
        @JvmStatic
        val PRICE_ASC_DESC: Set<String> =hashSetOf("price desc","price asc")


    }


}