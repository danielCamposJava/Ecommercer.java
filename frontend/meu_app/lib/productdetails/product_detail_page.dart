import 'package:flutter/material.dart';
import 'package:meu_app/model/product.dart';

class ProductDetailPage extends StatelessWidget {
  
  final Product product;
  const ProductDetailPage({
    super.key,
    required this.product
  })

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      appBar: AppBar(
        title: Text(product.name)),
        body: Padding(
          padding: EdgeInsets.all(20),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              SizedBox(height: 20),
            
             Text(
               product.name,
               style: TextStyle(
                 fontSize:28,
                 fontWeight: FontWeight.bold
               ),
             ),
             SizedBox(height: 10,)
            ],
          ),
          
          )
    
    );
  }
}
