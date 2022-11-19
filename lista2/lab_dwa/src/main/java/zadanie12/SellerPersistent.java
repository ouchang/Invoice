package zadanie12;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class responsible for implementing seller's interface
 * with methods for saving/reading database.
 */
public class SellerPersistent implements ISeller {
  ISQLDriver driver; //Database's driver

  String insert = "INSERT INTO Sellers(name, taxID) SELECT * FROM (SELECT ? AS name, ? AS taxID)"
        + " AS temp WHERE NOT EXISTS (SELECT taxID FROM Sellers WHERE taxID = ?) LIMIT 1;";
  String select = "SELECT name FROM Sellers WHERE taxID = ?";

  SellerPersistent(ISQLDriver driver) {
    this.driver = driver;
  }

  /**
   * Method responsible for saving seller's info in database.
   */
  public void saveData(Seller seller) {
    
    Connection connection = driver.getConnection();
    
    try {
      PreparedStatement query = connection.prepareStatement(insert);

      query.setString(1, seller.name);
      query.setString(2, seller.taxID);
      query.setString(3, seller.taxID);

      query.executeUpdate();

    } catch (SQLException e) {
      System.out.println("SP saveData SQL Error! Message: " + e.getMessage());
    }
  }


  //GRASP - Dependancy inversion
  //By using this class, main program will depend from interface
  
  /**
   * Method responsible for reading seller's info from database.
   */
  public void readData(Seller seller) {
    Connection connection = driver.getConnection();

    try {
      PreparedStatement query = connection.prepareStatement(select);

      query.setString(1, seller.taxID);

      ResultSet resultset = query.executeQuery();

      while (resultset.next() != false) {
        seller.setName(resultset.getString(1));
      }

    } catch (SQLException e) {
      System.out.println("SP readData SQL Error! Message: " + e.getMessage());
    }
  }
  
}
