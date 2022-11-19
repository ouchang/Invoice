package zadanie12;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//[GRASP] - Dependancy inversion
// By using this class, main program will depend from interface

/**
 * Class responsible for saving/reading invoice's info.
 */
public class InvoicePersistent implements IInvoice {
  ISQLDriver driver; //Databases' driver

  ICustomer customerPersistent; //Responsible for saving/reading data about invoice's customer
  ISeller sellerPersistent; //Responsible for saving/reading data about invoice's seller

  String insert = "INSERT INTO Invoices(customerID, sellerID) VALUES (?, ?)";
  String selectCustomer = "SELECT customerID FROM Invoices WHERE ID = ?";
  String selectSeller = "SELECT sellerID FROM Invoices WHERE ID = ?";

  InvoicePersistent(ISQLDriver driver, CustomerPersistent CP, SellerPersistent SP) {
    this.driver = driver;
    this.customerPersistent = CP;
    this.sellerPersistent = SP;
  }

  /**
   * Method responsible for saving invoice's data in database.
   */
  public void saveData(Invoice invoice) {
    
    Connection connection = driver.getConnection();
    
    try {
      PreparedStatement query = connection.prepareStatement(insert);

      query.setString(1, invoice.customer.taxID);
      query.setString(2, invoice.seller.taxID);

      query.executeUpdate();

    } catch (SQLException e) {
      System.out.println("IP saveData SQL Error! Message: " + e.getMessage());
    }
  }

  /**
   * Method responsible for reading data about invoice's customer (taxID).
   */
  public void readDataCustomer(Invoice invoice) {
    Connection connection = driver.getConnection();

    try {
      PreparedStatement query = connection.prepareStatement(selectCustomer);

      query.setInt(1, invoice.ID);

      ResultSet resultset = query.executeQuery();

      Customer customer = new Customer("", "");

      while (resultset.next() != false) {
        customer.setTaxID(resultset.getString(1));
      }

      customerPersistent.readData(customer);
      invoice.setCustomer(customer);
    } catch (SQLException e) {
      System.out.println("IP readDataCustomer SQL Error! Message: " + e.getMessage());
    }
  }
  
  /**
   * Method responsible for reading data about invoice's seller (taxID).
   */
  public void readDataSeller(Invoice invoice) {
    Connection connection = driver.getConnection();

    try {
      PreparedStatement query = connection.prepareStatement(selectSeller);

      query.setInt(1, invoice.ID);

      ResultSet resultset = query.executeQuery();

      Seller seller = new Seller("", "");

      while (resultset.next() != false) {
        seller.setTaxID(resultset.getString(1));
      }

      sellerPersistent.readData(seller);
      invoice.setSeller(seller);
    } catch (SQLException e) {
      System.out.println("IP readDataSeller SQL Error! Message: " + e.getMessage());
    }
  }

  /**
   * Method that find the highest saved ID number in database
   * and, by adding one to it, method creates ID for new invoice.
   */
  public int getInvoiceID() {
    Connection connection = driver.getConnection();
    int newInvoiceID = 0;
    try {
      PreparedStatement query = connection.prepareStatement("SELECT ID FROM Invoices" 
          + " ORDER BY ID DESC LIMIT 1");

      ResultSet resultset = query.executeQuery();

      if (!resultset.next()) {
        newInvoiceID = 1;
      } else {
        newInvoiceID = resultset.getInt(1) + 1;
      }
    } catch (SQLException e) {
      System.out.println("IP getInvoiceID SQL Error! Message: " + e.getMessage());
    }

    return newInvoiceID;
  }
}
