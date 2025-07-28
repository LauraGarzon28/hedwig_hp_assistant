import 'package:flutter/services.dart';
import 'package:permission_handler/permission_handler.dart';

class SmartButtonService {
  static const MethodChannel _channel = MethodChannel("voice_key_channel");

  static void init() {
    _channel.setMethodCallHandler((call) async {
      switch (call.method) {
        case "smartButtonPressed":
          print("Botón inteligente presionado");
          // Puedes iniciar STT en Flutter si no lo hiciste ya en Android
          break;
        case "speechRecognized":
          final text = call.arguments as String;
          print("Texto reconocido desde Android: $text");
          // Aquí envíalo a tu lógica de procesamiento de comandos
          break;
      }
    });
  }

  static Future<void> requestMicPermission() async {
    final status = await Permission.microphone.request();
    if (status.isGranted) {
      // Puedes iniciar el servicio
    } else {
      // Mostrar error al usuario
    }
  }
}
