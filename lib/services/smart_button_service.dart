import 'package:flutter/services.dart';

class SmartButtonService {
  static const MethodChannel _channel = MethodChannel("voice_key_channel");

  static void init() {
    _channel.setMethodCallHandler((call) async {
      if (call.method == "smartButtonPressed") {
        // Aquí decides qué hacer, como iniciar STT
        print("Botón inteligente presionado");
        // Puedes llamar aquí a tu lógica de STT o algo más
      }
    });
  }
}
