package zadanie12;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//[GRASP] - Dependancy inversion
//* By using this class, main program will depend from interface

/**
 * Class responsible for implementing customer's interface
 * with methods for saving/reading database.
 */
public class CustomerPersistent implements ICustomer {
  ISQLDriver driver; //Database's driver

  String insert = "INSERT INTO Customers(name, taxID) SELECT * FROM (SELECT ? AS name, ? AS taxID)"
        + " AS temp WHERE NOT EXISTS (SELECT taxID FROM Customers WHERE taxID = ?) LIMIT 1;";
  String select = "SELECT name FROM Customers WHERE taxID = ?";

  /**
   * Constructor.
   * @param driver driver of given database
   */
  CustomerPersistent(ISQLDriver driver) {
    this.driver = driver;
  }

  /**
   * Method responsible for saving customer's data in database.
   */
  public void saveData(Customer customer) {
    
    Connection connection = driver.getConnection();
    
    try {
      PreparedStatement query = connection.prepareStatement(insert);

      query.setString(1, customer.name);
      query.setString(2, customer.taxID);
      query.setString(3, customer.taxID);

      query.executeUpdate();

    } catch (SQLException e) {
      System.out.println("CP saveData SQL Error! Message: " + e.getMessage());
    }
  }

  /**
   * Method responsible for reading customer's data from database.
   */
  public void readData(Customer customer) {
    Connection connection = driver.getConnection();

    try {
      PreparedStatement query = connection.prepareStatement(select);

      query.setString(1, customer.taxID);

      ResultSet resultset = query.executeQuery();

      while (resultset.next() != false) {
        //Update customer's name based on info from database
        customer.setName(resultset.getString(1));
      }

    } catch (SQLException e) {
      System.out.println("CP readData SQL Error! Message: " + e.getMessage());
    }
  }
  
}
