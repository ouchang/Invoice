package zadanie12;

/**
 * Class responsible for creating invoice's customer.
 */
public class Customer {
  String name;
  String taxID;

  Customer(String name, String taxID) {
    this.name = name;
    this.taxID = taxID;
  }

  //[GRASP] - Expert
  //Class knows the most about its getters & setters

  void setName(String name) {
    this.name = name;
  }

  void setTaxID(String taxID) {
    this.taxID = taxID;
  }

  String getName() {
    return name;
  }

  String getTaxID() {
    return taxID;
  }
}
