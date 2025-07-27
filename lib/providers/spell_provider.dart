import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:hedwig_hp_assistant/models/spell_model.dart';
import 'package:shared_preferences/shared_preferences.dart';

class SpellProvider with ChangeNotifier {
  List<SpellModel> _spells = [];

  SpellProvider() {
    loadSpells();
  }

  List<SpellModel> get getSpells => _spells;

  void createSpell(SpellModel spell) {
    _spells.add(spell);
    saveSpells();
    notifyListeners();
  }

  void loadSpells() async {
    final prefs = await SharedPreferences.getInstance();
    List<String> spellJsonList = prefs.getStringList("spells") ?? [];
    _spells = spellJsonList
        .map((spellString) => SpellModel.fromJson(jsonDecode(spellString)))
        .toList();
    notifyListeners();
  }

  void removeSpell(String id) {
    _spells.removeWhere((spell) => spell.id == id);
    saveSpells();
    notifyListeners();
  }

  void saveSpells() async {
    final prefs = await SharedPreferences.getInstance();
    List<String> spellJsonList = _spells
        .map((spell) => jsonEncode(spell.toJson()))
        .toList();
    await prefs.setStringList("spells", spellJsonList);
  }

  void updateSpell(SpellModel updatedSpell) {
    int index = _spells.indexWhere((spell) => spell.id == updatedSpell.id);
    if (index != -1) {
      _spells[index] = updatedSpell;
      saveSpells();
      notifyListeners(); 
    }
  }
}
