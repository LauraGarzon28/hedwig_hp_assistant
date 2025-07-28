import 'package:flutter/material.dart';
import 'package:hedwig_hp_assistant/providers/spell_provider.dart';
import 'package:hedwig_hp_assistant/screens/add_spell_screen.dart';
import 'package:hedwig_hp_assistant/screens/home_screen.dart';
import 'package:hedwig_hp_assistant/screens/settings_screen.dart';
import 'package:hedwig_hp_assistant/services/smart_button_service.dart';
import 'package:provider/provider.dart';
import 'package:hedwig_hp_assistant/screens/spell_list_screen.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await SmartButtonService.requestMicPermission();
  SmartButtonService.init();
  runApp(ChangeNotifierProvider(
    create: (context) => SpellProvider(),
    child: MaterialApp(
      initialRoute: "/home",
      routes: {
        "/home": (context) => HomeScreen(),
        "/add_spell": (context) => AddSpellScreen(),
        "/settings": (context) => SettingsScreen(),
        "/spell_list": (context) => SpellListScreen(),
      },
    )
  ));
}



