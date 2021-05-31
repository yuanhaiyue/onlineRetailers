package com.yuan.online.util

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import java.nio.file.FileSystems
import java.nio.file.Path

/**
 * 描述         生成二维码工具
 */
class QRCodeGenerator {

    companion object{
        @JvmStatic
        fun generateQRCodeImage(text:String,width:Int,height:Int,filePath:String){
            val qrCodeWriter=QRCodeWriter()
            val bitMatrix=qrCodeWriter.encode(text,BarcodeFormat.QR_CODE,width,height)
            val path:Path=FileSystems.getDefault().getPath(filePath)
            MatrixToImageWriter.writeToPath(bitMatrix,"PNG",path)
        }
    }


}
fun main(args: Array<String>){
    QRCodeGenerator.generateQRCodeImage("Hello Word",350,350,"C:\\Users\\14760\\Desktop\\images\\QRTest.png")
}