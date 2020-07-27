import 'dart:typed_data';
import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:share/share.dart';

import 'package:image_gallery_saver/image_gallery_saver.dart';
import 'package:fluttertoast/fluttertoast.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {


  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';


  GlobalKey rootWidgetKey = GlobalKey();


  @override
  void initState() {
    super.initState();
  }


  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: RepaintBoundary(
        key: rootWidgetKey,
        child: Scaffold(
          appBar: AppBar(
            title: const Text('Plugin example app'),
          ),
          body: GestureDetector(
            child: Center(
              child: Text('Running on: $_platformVersion\n'),
            ),

            onTap: () {
              _saveImage();

            },
          ),
        ),
      ),

    );
  }



  void _saveImage() async {
    try {
      RenderRepaintBoundary boundary =
      rootWidgetKey.currentContext.findRenderObject();
      var image = await boundary.toImage(pixelRatio: 3.0);
      ByteData byteData = await image.toByteData(format: ImageByteFormat.png);
      Uint8List pngBytes = byteData.buffer.asUint8List();
      final result = await ImageGallerySaver.saveImage(pngBytes); //这个是核心的保存图片的插件
//      await Share.shareImage(pngBytes);
//      await Share.shareText("1234");

      Fluttertoast.showToast(
        msg: "保存成功",
        gravity: ToastGravity.BOTTOM,
      );

    } catch (e) {
      Fluttertoast.showToast(
        msg: "保存失败",
        gravity: ToastGravity.BOTTOM,
      );
    }
  }


}