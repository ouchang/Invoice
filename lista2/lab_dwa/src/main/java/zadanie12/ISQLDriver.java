package zadanie12;

import java.sql.Connection;

interface ISQLDriver {
  public Connection getConnection();
}
