class SpellModel {

  final String id;
  String name;
  String description;
  String trigger;
  String action;  
  Map<String, dynamic> parameters;

  SpellModel({
    required this.id,
    required this.name,
    required this.description,
    required this.trigger,
    required this.action,
    required this.parameters,
  });
  
}