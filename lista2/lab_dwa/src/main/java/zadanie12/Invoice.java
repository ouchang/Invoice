package zadanie12;

import java.util.ArrayList;
import java.util.List;

/**
 * Class resposinble for creating invoice.
 */
public class Invoice {
  Customer customer;
  Seller seller;
  List<Element> elements;

  Invoice(Customer customer, Seller seller) {

    this.customer = customer;
    this.seller = seller;
    this.elements = new ArrayList<Element>();
  }

  //[GRASP] - Expert
  //Invoice class has the most info about its getters & setters

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setSeller(Seller seller) {
    this.seller = seller;
  }

  public Seller getSeller() {
    return seller;
  }

  /**
   * Method adds element to invoice's list of elements.
   * @param e new element
   */
  public void addElement(Element e) {
    elements.add(e);
  }

  /**
   * Method gets element from invoice's list of element based on given index.
   * @param idx element's index
   * @return element based on given index
   */
  public Element getElement(int idx) {
    return elements.get(idx);
  }

  //[GRASP] - Expert
  //Class Invoice know the most about its elements, 
  //hence it counts the sum that is needed to pay.

  /**
   * Method responsible for counting sum that is needed to be paid.
   * @return
   */
  public double countSumToPay() {
    double needToPay = 0.0;
    for (int i = 0; i < elements.size(); i++) {
      Element e = elements.get(i);
      needToPay += e.getAmount() * e.getProduct().getPrice();
    }

    double scale = 10 * 10;
    return Math.round(needToPay * scale) / scale;
  }

  //[GRASP] - Expert
  //Class Invoice know the most about its elements, 
  //hence it counts the sum that is needed to pay.

  /**
   * Method responsible for counting sum that is needed to be paid.
   * @param elems list of bought elements 
   * @return
   */
  public static double countSumToPayA(ArrayList<Element> elems) {
    double needToPay = 0.0;
    for (int i = 0; i < elems.size(); i++) {
      Element e = elems.get(i);
      needToPay += e.getAmount() * e.getProduct().getPrice();
    }

    double scale = 10 * 10;
    return Math.round(needToPay * scale) / scale;
  }
}
