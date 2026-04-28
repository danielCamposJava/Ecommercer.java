import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:meu_app/model/product.dart';
import '../model/product.dart';

class ProductService {
  Future<List<Product>> fetchProducts() async {
    final response = await http.get(Uri.parse("http://10.0.2.2:8080/products"));

    if (response.statusCode == 200) {
      List data = jsonDecode(response.body);

      return data.map((item) => Product.fromjson(item)).toList();
    }

    throw Exception("Erro ao buscar produtos");
  }
}
