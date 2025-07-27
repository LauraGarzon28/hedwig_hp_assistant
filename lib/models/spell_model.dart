import 'package:uuid/uuid.dart';

class SpellModel {
  
  final String id;
  String name;
  String description;
  String action;
  List<String> triggers;
  Map<String, dynamic> parameters;

  static const _uuid = Uuid();

  SpellModel({
    String? id,
    required this.name,
    required this.description,
    required this.action,
    List<String>? triggers,
    Map<String, dynamic>? parameters,
  })  : id = id ?? _uuid.v4().substring(0, 8), 
        triggers = triggers ?? [],
        parameters = parameters ?? {};

  Map<String, dynamic> toJson() => {
        "id": id,
        "name": name,
        "description": description,
        "triggers": triggers,
        "action": action,
        "parameters": parameters,
      };

  factory SpellModel.fromJson(Map<String, dynamic> json) {
    return SpellModel(
      id: json["id"],
      name: json["name"],
      description: json["description"],
      action: json["action"],
      triggers: json["triggers"] != null
          ? List<String>.from(json["triggers"])
          : [],
      parameters: json["parameters"] != null
          ? Map<String, dynamic>.from(json["parameters"])
          : {},
    );
  }
}
