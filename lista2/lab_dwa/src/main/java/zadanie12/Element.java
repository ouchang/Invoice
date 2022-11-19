package zadanie12;

/**
 * Class responsible for creating element.
 */
public class Element {
  int amount;
  Product product;

  Element(int amount, Product product) {
    this.amount = amount;
    this.product = product;
  }

  //[GRASP] - Low coupling
  //Class give the creation of product over to Product class,
  //which reduces depedancy between them.

  //[GRASP] - High cohesion
  //High cohesion = Low coupling

  Element(int amount, String name, double price) {
    this.amount = amount;
    this.product = new Product(name, price);
  }

  //[GRASP] - Expert
  //Class knows the most about its getters & setters

  void setAmount(int amount) {
    this.amount = amount;
  }

  void setProduct(Product p) {
    this.product = p;
  }
  
  //[GRASP] - Creator
  //Element class has the most info about its products
  void setProductV(String name, double price) {
    Product product = new Product(name, price);
    this.product = product;
  }

  int getAmount() {
    return amount;
  }

  Product getProduct() {
    return product;
  }
}

