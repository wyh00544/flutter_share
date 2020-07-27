package com.example.share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.NonNull;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** SharePlugin */
public class SharePlugin implements MethodCallHandler {

  public static String CHANNEL = "channel:share_plugin";


  private static Activity activity;

  static MethodChannel channel;


  public SharePlugin() {
  }


  public static void registerWith(Registrar registrar) {
    activity = registrar.activity();
    SharePlugin sharePlugin = new SharePlugin();
    channel = new MethodChannel(registrar.messenger(), CHANNEL);
    channel.setMethodCallHandler(sharePlugin);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("shareText")) {

      String con = call.argument("content");
      shareText(con);
      result.success("success");

    } else if(call.method.equals("shareImage")){
      byte[] image = call.argument("content");
      shareImage(image);
      result.success("success");
    } else {
      result.notImplemented();
    }
  }


  private void shareText(String content){
    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
    shareIntent.setType("text/plain");
    shareIntent.putExtra(Intent.EXTRA_TEXT, content);
//切记需要使用Intent.createChooser，否则会出现别样的应用选择框，您可以试试
    shareIntent = Intent.createChooser(shareIntent, "分享到");
    activity.startActivity(shareIntent);

  }

  private void shareImage(byte[] image){

    Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);

    File file = saveFile(bitmap, String.valueOf(System.currentTimeMillis()));

    //将mipmap中图片转换成Uri
    Uri imgUri = Uri.fromFile(file);

    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
//其中imgUri为图片的标识符
    shareIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
    shareIntent.setType("image/*");
//切记需要使用Intent.createChooser，否则会出现别样的应用选择框，您可以试试
    shareIntent = Intent.createChooser(shareIntent, "分享到");
    activity.startActivity(shareIntent);

  }

  public File saveFile(Bitmap bm, String fileName) {//将Bitmap类型的图片转化成file类型，便于上传到服务器
    String path = Environment.getExternalStorageDirectory() + "/invite_img";
    File dirFile = new File(path);
    if (!dirFile.exists()) {
      dirFile.mkdir();
    }
    File myCaptureFile = new File(path + fileName);
    BufferedOutputStream bos = null;
    try {
      bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
      bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
      bos.flush();
      bos.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return myCaptureFile;

  }

}
