package zadanie12;

/**
 * Class responible for creating product.
 */
public class Product {
  String name;
  double price;

  Product(String name, double price) {
    this.name = name;
    this.price = price;
  }

  //[GRASP] - Expert
  //Class knows the most about its getters & setters

  void setName(String name) {
    this.name = name;
  }

  String getName() {
    return name;
  }

  void setPrice(double price) {
    this.price = price;
  }

  Double getPrice() {
    return price;
  }
}

