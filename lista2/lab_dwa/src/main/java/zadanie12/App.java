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

    //Factory for ISQLDriver instances
    DriverFactory driverFactory = new DriverFactory();
    ISQLDriver driver = null;

    try {
      System.out.println("Which type database do you want to use? (MySQL / PostgreSQL): ");
      text = reader.readLine();
      driver = driverFactory.getDriver(text);

    }  catch (IOException e) {
      System.out.println("Error during reading from command line! Message: " + e.getMessage());
    }

    //[GRASP] - Polymorphism
    //Program uses Driver interface instead of creating similar code for each database

    //Creating objects based on defined interfeces (resposible for saving / reading database)
    CustomerPersistent CP = new CustomerPersistent(driver); 
    SellerPersistent SP = new SellerPersistent(driver); 
    ElementPersistent EP = new ElementPersistent(driver); 
    InvoicePersistent IP = new InvoicePersistent(driver, CP, SP); 
    

    try {
      boolean startProgram = true;
      
      while (startProgram) {
        System.out.println("What do you want to do? (Read / Save): ");
        String option = reader.readLine();

        if (option.equals("Save")) {
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

          //Creating invoice (meant to be saved in database) based on Customer and Seller
          Invoice invoiceSave = new Invoice(IP, CP, customer, SP, seller, EP);

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
            invoiceSave.addElement(new Element(elementAmount, productName, productPrice));

            //Asking for another product
            System.out.println("Do you want add a product? (True/False): ");
            text = reader.readLine();
            createNewProduct = Boolean.valueOf(text);
          }

          //Saving invoice in database
          invoiceSave.save();
        } else if (option.equals("Read")) {
          Customer customer = new Customer("", "");
          Seller seller = new Seller("", "");

          System.out.println("Write invoice's ID that you want to read: ");
          text = reader.readLine();
          int invoiceID = Integer.parseInt(text);

          //Creating invoice responsible for collecting data from database
          Invoice invoiceRead = new Invoice(invoiceID, IP, CP, customer, SP, seller, EP);

          //Reading data from database
          invoiceRead.read();

          String customerText = "Customer: [name : %s], [taxID : %s]";
          System.out.println(String.format(customerText, invoiceRead.customer.getName(), 
              invoiceRead.customer.getTaxID()));

          String sellerText = "Seller: [name : %s], [taxID : %s]";
          System.out.println(String.format(sellerText, invoiceRead.seller.getName(), 
              invoiceRead.seller.getTaxID()));

          String elementText = "Element: [productName : %s]," 
              + " [amount : %s], [productPrice : %s]";
          for (int i = 0; i < invoiceRead.elements.size(); i++) {
            Element e = invoiceRead.getElement(i);
            System.out.println(String.format(elementText, e.product.name, 
                String.valueOf(e.amount), String.valueOf(e.product.price)));
          }

          String toPayText = "Need to pay: %s PLN";
          System.out.println(String.format(toPayText, String.valueOf(invoiceRead.countSumToPay())));
        } else {
          System.out.println("You've chosen wrong option!");
        }

        System.out.println("Do you want to start program again? (True/False): ");
        text = reader.readLine();
        startProgram = Boolean.valueOf(text);
      }
    } catch (IOException e) {
      System.out.println("Error during reading from command line! Message: " + e.getMessage());
    }
  }
}
