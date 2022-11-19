package zadanie12;

/**
 * Class responsible for creating driver for given database type.
 */
public class DriverFactory {

  /**
   * Method creates database's driver based on given type.
   * @param type type of database
   * @return
   */
  public ISQLDriver getDriver(String type) {
    if (type.equals("MySQL")) {
      return new MysqlDriver();
    } else if (type.equals("PostgreSQL")) {
      //return new PostgresqlDriver;
    } else {
      System.out.println("Wrong type of database!");
    }
    return null;
  }
}
