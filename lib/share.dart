import 'dart:async';
import 'dart:typed_data';

import 'package:flutter/services.dart';

class Share {

  static const MethodChannel _channel =
      const MethodChannel('channel:share_plugin');


  static Future<String> shareText(String content) async {
    assert(content != null && content.isNotEmpty);
    var result = await _channel.invokeMethod('shareText',{"content": content});
    return result;
  }


  static Future<String> shareImage(Uint8List content) async {
    assert(content != null && content.isNotEmpty);
    var result = await _channel.invokeMethod('shareImage',{"content": content});
    return result;
  }
}
