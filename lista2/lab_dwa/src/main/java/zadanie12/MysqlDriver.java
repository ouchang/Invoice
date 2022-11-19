package zadanie12;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//[GRASP] - Dependancy inversion
//By using this class, main program will depend from interface

/**
 * Class responsible for implementing driver's interface
 * with methods for creating a connection with MySQL database.
 */
public class MysqlDriver implements ISQLDriver {

  Connection connection;

  MysqlDriver() {
    try {
      String url = "jdbc:mysql://172.29.128.1:23306/TP_L3"; ///etc/resolv.conf
      String username = "root";
      String password = "Ola123";
      this.connection = DriverManager.getConnection(url, username, password); 
    } catch (SQLException e) {
      System.out.println("Connection failed! Message: " + e.getMessage());
    }
  }

  public Connection getConnection() {
    return connection;
  }
}
