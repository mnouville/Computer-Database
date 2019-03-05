package dao;

import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class instantiate the connection with the database.
 * 
 * @author mnouville
 * @version 1.0
 */
public class DaoFactory {
  private static HikariDataSource dataSource = new HikariDataSource();
  
  private static DaoFactory instance = new DaoFactory();
  
  private Logger logger = LoggerFactory.getLogger(DaoFactory.class);

  private DaoFactory() {
    Properties connectionProps = new Properties();
    try {
      connectionProps.load(new FileInputStream(
              Thread.currentThread().getContextClassLoader().getResource("").getPath()
              + "hikariconfig.properties"));
      dataSource.setJdbcUrl(connectionProps.getProperty("URL"));
      dataSource.setUsername(connectionProps.getProperty("USERNAME"));
      dataSource.setPassword(connectionProps.getProperty("PASSWORD"));
      dataSource.setDriverClassName(connectionProps.getProperty("DRIVER"));
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }
  
  /**
   * Method get Instance.
   * @return the DAOFactory
   */
  public static DaoFactory getInstance() {
    return instance;
  }
  
  /**
   * Method get Connection.
   * @return the connection to the database
   * @throws SQLException thrown if a problem occur during the communication
   */
  public static Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }
  

  /**
   * This method return a CompanyDao.
   * 
   * @return Object CompanyDao
   */
  public CompanyDao getCompanyDao() {
    return CompanyDaoImpl.getInstance();
  }

  /**
   * This method return a ComputerDao.
   * 
   * @return Object ComputerDao
   */
  public ComputerDao getComputerDao() {
    return ComputerDaoImpl.getInstance();
  }
}