package zadanie12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//[GRASP] - Creator
//Class App has the most data about invoice,
//hence App creates instances of Invoice class.

//[GRASP] - Controller
//App gets all information from user, delegates tasks
//and returns output to user

/**
 * Class responsible for staring program.
 */
public class App {
  /**
   * Method responsible for starting program. 
   * 
   * @param args arguments from commandline
   */
  public static void main(String[] args) {
    InputStreamReader inRead = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(inRead);

    String text = ""; //Text read from commandline 

    try {
      boolean startProgram = true;
      
      while (startProgram) {
        //Creating CUSTOMER
        System.out.println("Write customer's name: ");
        String customerName = reader.readLine();

        System.out.println("Write customer's taxID: ");
        String customerTaxID = reader.readLine();

        Customer customer = new Customer(customerName, customerTaxID);
        
        //Creating SELLER
        System.out.println("Write seller's name: ");
        String sellerName = reader.readLine();

        System.out.println("Write seller's taxID: ");
        String sellerTaxID = reader.readLine();

        Seller seller = new Seller(sellerName, sellerTaxID);

        //Moving to collecting products section
        System.out.println("Do you want add a product? (True/False): ");
        text = reader.readLine();
        boolean createNewProduct = Boolean.valueOf(text);

        //Creating invoice based on Customer and Seller
        Invoice invoice = new Invoice(customer, seller);

        String productName;
        int elementAmount;
        double productPrice;

        //Adding products
        while (createNewProduct) {
          productName = "";
          elementAmount = 0;
          productPrice = 0.0;

          //Collecting data about the product
          System.out.println("Write name of the product that was bought: ");
          productName = reader.readLine();

          System.out.println("Write product's amount: ");
          text = reader.readLine();
          elementAmount = Integer.parseInt(text);

          System.out.println("Write product's price: ");
          text = reader.readLine();
          productPrice = Double.parseDouble(text);

          //Adding element to invoice's list
          invoice.addElement(new Element(elementAmount, productName, productPrice));

          //Asking for another product
          System.out.println("Do you want add a product? (True/False): ");
          text = reader.readLine();
          createNewProduct = Boolean.valueOf(text);
        }

        String customerText = "Customer: [name : %s], [taxID : %s]";
        System.out.println(String.format(customerText, invoice.customer.getName(), 
            invoice.customer.getTaxID()));

        String sellerText = "Seller: [name : %s], [taxID : %s]";
        System.out.println(String.format(sellerText, invoice.seller.getName(), 
            invoice.seller.getTaxID()));

        String elementText = "Element: [productName : %s]," 
            + " [amount : %s], [productPrice : %s]";
        for (int i = 0; i < invoice.elements.size(); i++) {
          Element e = invoice.getElement(i);
          System.out.println(String.format(elementText, e.product.name, 
              String.valueOf(e.amount), String.valueOf(e.product.price)));
        }

        String toPayText = "Need to pay: %s PLN";
        System.out.println(String.format(toPayText, String.valueOf(invoice.countSumToPay())));
      
        System.out.println("Do you want to start program again? (True/False): ");
        text = reader.readLine();
        startProgram = Boolean.valueOf(text);
      }
    } catch (IOException e) {
      System.out.println("Error during reading from command line! Message: " + e.getMessage());
    }
  }
}
