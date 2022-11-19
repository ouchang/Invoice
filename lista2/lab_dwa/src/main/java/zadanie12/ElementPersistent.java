package zadanie12;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


//[GRASP] - Dependancy inversion
//By using this class, main program will depend from interface

/**
 * Class responsible for implementing element's interface
 * with methods for saving/reading database. 
 */
public class ElementPersistent implements IElement {
  ISQLDriver driver; //Database's driver


  //[GRASP] - Dependancy inversion
  //Program depends on product's interface
  IProduct productPersistent; //Responsible for saving/reading element's product

  String insert = "INSERT INTO Elements(invoiceID, productName, amount) VALUES (?, ?, ?)"; 
  String select = "SELECT productName, amount FROM Elements WHERE invoiceID = ?"; 

  ElementPersistent(ISQLDriver driver) {
    this.driver = driver;
    this.productPersistent = new ProductPersistent(driver);
  }

  /**
   * Method responsible for saving element's data in database.
   */
  public void saveData(Element element, int invoiceID) {
    
    Connection connection = driver.getConnection();
    
    try {
      PreparedStatement query = connection.prepareStatement(insert);

      //Save data about element' product in database;
      productPersistent.saveData(element.product);

      query.setInt(1, invoiceID); 
      query.setString(2, element.product.name);
      query.setInt(3, element.amount);

      query.executeUpdate();

    } catch (SQLException e) {
      System.out.println("EP saveData SQL Error! Message: " + e.getMessage());
    }
  }

  /**
   * Method responsible for reading element's data from database.
   */
  public ArrayList<Element> readData(int invoiceID) {
    Connection connection = driver.getConnection();
    ArrayList<Element> elements = new ArrayList<Element>();

    try {
      PreparedStatement query = connection.prepareStatement(select);

      query.setInt(1, invoiceID);

      ResultSet resultset = query.executeQuery();

      String productName = "";
      int elementAmount;

      while (resultset.next() != false) {
        productName = resultset.getString(1);
        elementAmount = resultset.getInt(2);

        //Reading data about element's product
        Product product = productPersistent.readData(productName);

        //Creating element based on read information and add it to the list
        Element element = new Element(elementAmount, product);
        elements.add(element);
      }

    } catch (SQLException e) {
      System.out.println("EP readData SQL Error! Message: " + e.getMessage());
    }

    return elements;
  }
  
}
