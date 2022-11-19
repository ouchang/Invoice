package zadanie12;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class responsible for implementing product's interface
 * with methods for saving/reading database.
 */
public class ProductPersistent implements IProduct {
  ISQLDriver driver; //Database's driver

  String insert = "INSERT INTO Products(name, price) SELECT * FROM (SELECT ? AS name, ? AS price)"
        + " AS temp WHERE NOT EXISTS (SELECT name FROM Products WHERE name = ?) LIMIT 1;";
  String select = "SELECT price FROM Products WHERE name = ?";

  ProductPersistent(ISQLDriver driver) {
    this.driver = driver;
  }

  /**
   * Method responsible for saving product's data in database.
   */
  public void saveData(Product product) {
    
    Connection connection = driver.getConnection();
    
    try {
      PreparedStatement query = connection.prepareStatement(insert);

      query.setString(1, product.name);
      query.setDouble(2, product.price);
      query.setString(3, product.name);

      query.executeUpdate();

    } catch (SQLException e) {
      System.out.println("PP saveData SQL Error! Message: " + e.getMessage());
    }
  }

  /**
   * Method responsible for reading product's data from database.
   */
  public Product readData(String productName) {
    Connection connection = driver.getConnection();
    Product product = new Product(productName, 0.0);

    try {
      PreparedStatement query = connection.prepareStatement(select);

      query.setString(1, productName);

      ResultSet resultset = query.executeQuery();

      while (resultset.next() != false) {
        product.setPrice(resultset.getDouble(1));
      } 

    } catch (SQLException e) {
      System.out.println("PP readData SQL Error! Message: " + e.getMessage());
    }

    return product;
  }
}
