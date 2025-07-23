import 'package:flutter/material.dart';
import 'package:hedwig_hp_assistant/providers/spell_provider.dart';
import 'package:hedwig_hp_assistant/screens/add_spell_screen.dart';
import 'package:hedwig_hp_assistant/screens/home_screen.dart';
import 'package:hedwig_hp_assistant/screens/settings_screen.dart';
import 'package:hedwig_hp_assistant/screens/spell_list_screen.dart';

void main() {
  runApp(ChangeNotifierProvider(
    create: (context) => SpellProvider(),
    child: MaterialApp(
      initialRoute: "/home",
      routes: {
        "/home": (context) => HomeScreen(),
        "/add_spell": (context) => AddSpellScreen(),
        "/settings": (context) => SettingsScreen(),
        "/spell_list": (context) => SpellListScreen()
      },
    )
  ));
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Hedwig Assistant',
      theme: ThemeData(
        
      ),
    );
  }
}


