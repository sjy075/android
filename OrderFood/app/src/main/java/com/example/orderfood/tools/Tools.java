package com.example.orderfood.tools;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Tools {

    //将byte[]转换成Bitmap
    public static Bitmap byteArrayToBitmap(byte[] byteArray){
        if(byteArray == null){
            return null;
        }
        return BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
    }

    //保存Bitmap为png图像文件
    public static boolean saveBitmapAsPng(Context context,Bitmap bitmap, String fileName){
        if(bitmap == null || context == null){
            return false;
        }
        //使用ContentValues构建图像的元数据
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DESCRIPTION, "食物图片");
        values.put(MediaStore.Images.Media.DISPLAY_NAME,fileName);
        //获取外部公共图库的URI
        Uri externalContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        //在外部公共图库中创建一个新的图像文件
        Uri imageUri = context.getContentResolver().insert(externalContentUri,values);
        if(imageUri != null){
            try{
                //打开输出流以保存位图数据到图像文件
                OutputStream outputStream = context.getContentResolver().openOutputStream(imageUri);
                if(outputStream != null){
                    //压缩成png格式并写入outputStream中
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
                    outputStream.close();
                    return true;
                }
            }catch (IOException e){
                e.printStackTrace();
                Log.e("图片权限","保存图像时出错" + e.getMessage());
            }
        }else{
            Log.e("图片权限","无法创建图像文件");
        }
        return false;
    }

    //获取图片的路径
    public static String getImagePath(){
        //获取相册绝对路径
        String imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();

        return imagePath;
    }

    //将byte[]转换为PNG图像并保存到指定路径
    public static boolean saveByteArrayAsPng(byte[] byteArray,String filePath,Context context){
        //将路径根据 / 进行分割
        String[] result = filePath.split("/");
        //获取最后一个元素，即文件名
        String fileName = result[result.length - 1];
        //将字节数组转换为Bitmap
        Bitmap bitmap = byteArrayToBitmap(byteArray);

        saveBitmapAsPng(context,bitmap,fileName);
        return true;
    }
    public static void deletePhoto(String photoPath) {
        File photoFile = new File(photoPath);
        if (photoFile.exists() && photoFile.isFile()) {
            photoFile.delete();
        }
    }


}
