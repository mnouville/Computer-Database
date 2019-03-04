package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;

public class DataSource {

  /**
   * Method that return an HikariDataSource.
   * @return HikariDataSource
   */
  public HikariDataSource dataSource() throws SQLException {
    HikariConfig config = new HikariConfig("/hikari.properties");
    HikariDataSource dataSource = new HikariDataSource(config);

    return dataSource;
  }
}
