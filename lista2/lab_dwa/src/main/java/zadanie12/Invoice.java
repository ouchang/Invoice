package zadanie12;

import java.util.ArrayList;
import java.util.List;

/**
 * Class resposinble for creating invoice.
 */
public class Invoice {
  int ID;

  //[GRASP] - Pure fabrication
  //Program communicates with invoice's class that implements interface
  IInvoice invoicePersistent;

  //[GRASP] - Pure fabrication
  //Program communicates with customer's class that implements interface
  Customer customer;
  ICustomer customerPersistent; 

  //[GRASP] - Pure fabrication
  //Program communicates with seller's class that implements interface
  Seller seller;
  ISeller sellerPersistent;

  //[GRASP] - Pure fabrication
  //Program communicates with element's class that implements interface
  List<Element> elements;
  IElement elementPersistent;

  //SAVE constructor version
  Invoice(IInvoice invoicePersistent, ICustomer customerPersistent,
       Customer customer, ISeller sellerPersistent, Seller seller, IElement elementPersistent) {
    this.invoicePersistent = invoicePersistent;
    this.ID = invoicePersistent.getInvoiceID(); //gives highest ID from database + 1

    this.customer = customer;
    this.customerPersistent = customerPersistent;

    this.seller = seller;
    this.sellerPersistent = sellerPersistent;

    this.elements = new ArrayList<Element>();
    this.elementPersistent = elementPersistent;
  }

  //READ constructor version
  Invoice(int ID, IInvoice invoicePersistent, ICustomer customerPersistent,
       Customer customer, ISeller sellerPersistent, Seller seller, IElement elementPersistent) {
    this.invoicePersistent = invoicePersistent;
    this.ID = ID;

    this.customer = customer;
    this.customerPersistent = customerPersistent;

    this.seller = seller;
    this.sellerPersistent = sellerPersistent;

    this.elements = new ArrayList<Element>();
    this.elementPersistent = elementPersistent;
  }

  //[GRASP] - Expert
  //Invoice class has the most info about its getters & setters

  public int getID() {
    return ID;
  }

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

  /**
   * Method responsible for saving customer's data in database.
   */
  public void saveCustomer() {
    //[GRASP] - Dependancy inversion
    //Invoice class depends on customer's inteface
    customerPersistent.saveData(customer);
  }

  /**
   * Method responsible for reading customer's data from database.
   */
  public void readCustomer() {
    //[GRASP] - Dependancy inversion
    //Invoice class depends on invoice's inteface
    invoicePersistent.readDataCustomer(this);
  }

  /**
   * Method responsible for saving seller's data in database.
   */
  public void saveSeller() {
    //[GRASP] - Dependancy inversion
    //Invoice class depends on seller's inteface
    sellerPersistent.saveData(seller);
  }

  /**
   * Method responsible for reading seller's data from database.
   */
  public void readSeller() {
    //[GRASP] - Dependancy inversion
    //Invoice class depends on invoice's inteface
    invoicePersistent.readDataSeller(this);
  }

  /**
   * Method saves elements from invoice's list in database.
   */
  public void saveElements() {
    for (int i = 0; i < elements.size(); i++) {
      elementPersistent.saveData(elements.get(i), ID);
    }
  }

  /**
   * Method reads elements from database.
   */
  public void readElements() {
    //[GRASP] - Dependancy inversion
    //Invoice class depends on element's inteface
    this.elements = elementPersistent.readData(ID);
  }

  /**
   * General method for saving invoice's data.
   * Uses: method for saving customer's data in database
   *       method for saving seller's data in database
   *       method for saving elements' data in database
   */
  public void save() {
    saveCustomer();
    saveSeller();
    saveElements();
    invoicePersistent.saveData(this);
  }

  /**
   * General method for reading invoice's data.
   * Uses: method for reading customer's data in database
   *       method for reading seller's data in database
   *       method for reading elements' data in database
   */
  public void read() {
    readCustomer();
    readSeller();
    readElements();
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
