package dao;

import controller.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Computer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that contains every method concerning Computer in the Database.
 * 
 * @author mnouville
 * @version 1.0
 */
public class ComputerDaoImpl implements ComputerDao {

  private DaoFactory daoFactory;
  private static ComputerDaoImpl single_instance = null;
  private static final Logger LOG = LoggerFactory.getLogger(Controller.class);
  private final String insert = "INSERT INTO computer(id,name,introduced,discontinued,company_id) "
                               + "VALUES (?,?,?,?,?);";
  private final String getall = "SELECT id,name,introduced,discontinued,company_id FROM computer";
  private final String delete = "DELETE FROM computer WHERE id = ";
  private final String get = "SELECT id,name,introduced,discontinued,company_id "
                           + "FROM computer where id =";
  private final String maxid = "SELECT MAX(id) FROM computer;";
  private final String count = "SELECT COUNT(id) FROM computer;";

  /**
   * Constructor of ComputerDaoImpl.
   * 
   * @param daoFactory DaoFactory
   */
  ComputerDaoImpl(DaoFactory daoFactory) {
    this.daoFactory = daoFactory;
  }
  
  /**
   * Return unique instance of ComputerDaoImpl.
   * @return ComputerDaoImpl
   */
  public static ComputerDaoImpl getInstance() {
    // Singleton
    if (single_instance == null) {
      single_instance = new ComputerDaoImpl(DaoFactory.getInstance());
    }
      
    return single_instance;
  }

  /**
   * This method take a Computer in parameter and add it into the Database.
   * 
   * @param c Computer
   */
  @Override
  public void addComputer(Computer c) throws SQLException {
    try (Connection connexion = daoFactory.getConnection();
        PreparedStatement preparedStatement = connexion.prepareStatement(insert)) {
      preparedStatement.setInt(1, c.getId());
      preparedStatement.setString(2, c.getName());

      if (c.getIntroduced() != null) {
        preparedStatement.setDate(3, new java.sql.Date(c.getIntroduced().getTime()));
      } else {
        preparedStatement.setDate(3, null);
      }

      if (c.getDiscontinued() != null) {
        preparedStatement.setDate(4, new java.sql.Date(c.getDiscontinued().getTime()));
      } else {
        preparedStatement.setDate(4, null);
      }

      if (c.getCompany().getId() == 0) {
        preparedStatement.setNull(5, java.sql.Types.INTEGER);
      } else {
        preparedStatement.setInt(5, c.getCompany().getId());
      }
      

      preparedStatement.executeUpdate();
      LOG.info("Request succesfully executed (ADD COMPUTER)! ");
    } catch (SQLException e) {
      e.printStackTrace();
      LOG.error("ERROR COULD NOT CONNECT TO THE DATABASE");
    }
  }

  /**
   * This method return a list of every computers in the Database.
   * 
   * @return List of Objects Computer
   */
  @Override
  public List<Computer> getComputers() throws SQLException {
    List<Computer> computers = new ArrayList<Computer>();
    ResultSet resultat = null;
    try (Connection connexion = daoFactory.getConnection();
        Statement statement = connexion.createStatement()) {
      resultat = statement.executeQuery(getall + " LIMIT 50;");
      CompanyDao cd = daoFactory.getCompanyDao();
      while (resultat.next()) {
        Integer id = resultat.getInt("id");
        String name = resultat.getString("name");
        Timestamp introduced = resultat.getTimestamp("introduced");
        Timestamp discontinued = resultat.getTimestamp("discontinued");
        int idcompany = resultat.getInt("company_id");

        Computer c = new Computer(id, name, introduced, discontinued, cd.getCompany(idcompany));
        computers.add(c);
      }
      LOG.info("Request succesfully executed (GET ALL COMPUTERS)! ");
    } catch (SQLException e) {
      LOG.error("ERROR COULD NOT CONNECT TO THE DATABASE");
      e.printStackTrace();
    }
    return computers;
  }

  /**
   * This method return a list of every computers in the Database.
   * 
   * @return List of Objects Computer
   */
  @Override
  public List<Computer> getComputers(int begin) throws SQLException {
    List<Computer> computers = new ArrayList<Computer>();
    ResultSet resultat = null;

    try (Connection connexion = daoFactory.getConnection();
        Statement statement = connexion.createStatement()) {
      resultat = statement.executeQuery(getall + " LIMIT 50 OFFSET " + begin);
      CompanyDao cd = daoFactory.getCompanyDao();
      while (resultat.next()) {
        Integer id = resultat.getInt("id");
        String name = resultat.getString("name");
        Timestamp introduced = resultat.getTimestamp("introduced");
        Timestamp discontinued = resultat.getTimestamp("discontinued");
        int idcompany = resultat.getInt("company_id");

        Computer c = new Computer(id, name, introduced, discontinued, cd.getCompany(idcompany));
        computers.add(c);
      }
      LOG.info("Request succesfully executed (GET ALL COMPUTERS)! ");
    } catch (SQLException e) {
      LOG.error("ERROR COULD NOT CONNECT TO THE DATABASE");
      e.printStackTrace();
    }
    return computers;
  }

  /**
   * This method delete computers by ID.
   * 
   * @param id int
   */
  @Override
  public void deleteComputer(int id) throws SQLException {
    try (Connection connexion = daoFactory.getConnection();
        PreparedStatement preparedStatement = connexion.prepareStatement(delete + id)) {
      preparedStatement.executeUpdate();
      LOG.info("Request succesfully executed (DELETE COMPUTERS)! ");
    } catch (SQLException e) {
      LOG.error("ERROR COULD NOT CONNECT TO THE DATABASE");
      e.printStackTrace();
    }
  }

  /**
   * This method return the Computer with this ID in the Database.
   * 
   * @param i int
   * @return an object Computer
   */
  @Override
  public Computer getComputer(int i) throws SQLException {
    Computer c = new Computer();
    ResultSet resultat = null;

    try (Connection connexion = daoFactory.getConnection();
        Statement statement = connexion.createStatement()) {

      resultat = statement.executeQuery(get + i + ";");

      if (resultat.next()) {
        Integer id = resultat.getInt("id");
        String name = resultat.getString("name");
        Date introduced = resultat.getDate("introduced");
        Date discontinued = resultat.getDate("discontinued");
        int companyid = resultat.getInt("company_id");

        CompanyDao cd = daoFactory.getCompanyDao();

        c = new Computer(id, name, introduced, discontinued, cd.getCompany(companyid));
      }
      LOG.info("Request succesfully executed (GET COMPUTER)! ");
    } catch (SQLException e) {
      LOG.error("ERROR COULD NOT ACCESS TO THE DATABASE");
      e.printStackTrace();
    }

    return c;
  }

  /**
   * This method permit updates on computers in the database.
   * 
   * @param c Computer
   */
  @Override
  public void updateComputer(Computer c) throws SQLException {
    String intro;
    String disc;

    if (c.getIntroduced() == null) {
      intro = "NULL";
    } else {
      intro = "TIMESTAMP('" + new java.sql.Timestamp(c.getIntroduced().getTime()).toString() + "')";
    }

    if (c.getDiscontinued() == null) {
      disc = "NULL";
    } else {
      disc = "TIMESTAMP('" + new java.sql.Timestamp(c.getDiscontinued().getTime()).toString()
          + "')";
    }

    if (c.getCompany().getId() == 0) {
      try (Connection connexion = daoFactory.getConnection();
          PreparedStatement preparedStatement = connexion
              .prepareStatement("update computer set name = '" + c.getName() + "', introduced = "
                  + intro + " ,discontinued = " + disc + ", company_id = NULL" + " where id = "
                  + c.getId() + ";");) {
        preparedStatement.executeUpdate();
        LOG.info("Request succesfully executed (UPDATE COMPUTER)! ");
      } catch (SQLException e) {
        LOG.error("ERROR COULD NOT ACCESS TO THE DATABASE");
        e.printStackTrace();
      }
    } else {
      try (Connection connexion = daoFactory.getConnection();
          PreparedStatement preparedStatement = connexion
              .prepareStatement("update computer set name = '" + c.getName() + "', introduced = "
                  + intro + " ,discontinued = " + disc + ", company_id = " + c.getCompany().getId()
                  + " where id = " + c.getId() + ";");) {
        preparedStatement.executeUpdate();
        LOG.info("Request succesfully executed (UPDATE COMPUTER)! ");
      } catch (SQLException e) {
        LOG.error("ERROR COULD NOT ACCESS TO THE DATABASE");
        e.printStackTrace();
      }
      ;
    }
    
    
  }

  /**
   * Return the Maximum Id in the database.
   * 
   * @return Int that correspond to the Max Id
   */
  @Override
  public int getMaxId() throws SQLException {
    ResultSet resultat = null;
    try (Connection connexion = daoFactory.getConnection();
        Statement statement = connexion.createStatement()) {
      resultat = statement.executeQuery(maxid);
      if (resultat.next()) {
        return resultat.getInt("MAX(id)") + 1;
      }
      LOG.info("Request succesfully executed (GET MAX ID)! ");
    } catch (SQLException e) {
      LOG.error("ERROR COULD NOT ACCESS TO THE DATABASE");
      e.printStackTrace();
    }
    return 0;
  }

  /**
   * Return the number of row in the database.
   * 
   * @return int
   */
  public int getCount() throws SQLException {
    ResultSet resultat = null;
    try (Connection connexion = daoFactory.getConnection();
        Statement statement = connexion.createStatement()) {
      resultat = statement.executeQuery(count);
      if (resultat.next()) {
        return resultat.getInt("COUNT(ID)");
      }
      LOG.info("Request succesfully executed (GET COUNT ID)! ");
    } catch (SQLException e) {
      LOG.error("ERROR COULD NOT ACCESS TO THE DATABASE");
      e.printStackTrace();
    }
    return 0;
  }

  /**
   * Method that sort all computers by name.
   */
  public List<Computer> searchName(String search) throws SQLException {
    List<Computer> computers = new ArrayList<Computer>();
    ResultSet resultat = null;
    try (Connection connexion = daoFactory.getConnection();
        Statement statement = connexion.createStatement()) {
      resultat = statement.executeQuery(getall + " where name LIKE '%" + search + "%' LIMIT 50;");
      CompanyDao cd = daoFactory.getCompanyDao();
      while (resultat.next()) {
        Integer id = resultat.getInt("id");
        String name = resultat.getString("name");
        Timestamp introduced = resultat.getTimestamp("introduced");
        Timestamp discontinued = resultat.getTimestamp("discontinued");
        int idcompany = resultat.getInt("company_id");

        Computer c = new Computer(id, name, introduced, discontinued, cd.getCompany(idcompany));
        computers.add(c);
      }
      LOG.info("Request succesfully executed (GET ALL COMPUTERS SORTED BY NAME)! ");
    } catch (SQLException e) {
      LOG.error("ERROR COULD NOT CONNECT TO THE DATABASE");
      e.printStackTrace();
    }
    return computers;
  }
  
  /**
   * Method that sort all computers by introduced.
   */
  public List<Computer> sortByName(String type, int begin) throws SQLException {
    List<Computer> computers = new ArrayList<Computer>();
    ResultSet resultat = null;
    try (Connection connexion = daoFactory.getConnection();
        Statement statement = connexion.createStatement()) {
      resultat = statement.executeQuery(
          getall + " order by name " + type + " LIMIT 50 OFFSET " + begin);
      CompanyDao cd = daoFactory.getCompanyDao();
      while (resultat.next()) {
        Integer id = resultat.getInt("id");
        String name = resultat.getString("name");
        Timestamp introduced = resultat.getTimestamp("introduced");
        Timestamp discontinued = resultat.getTimestamp("discontinued");
        int idcompany = resultat.getInt("company_id");

        Computer c = new Computer(id, name, introduced, discontinued, cd.getCompany(idcompany));
        computers.add(c);
      }
      LOG.info("Request succesfully executed (GET ALL COMPUTERS SORTED BY NAME)! ");
    } catch (SQLException e) {
      LOG.error("ERROR COULD NOT CONNECT TO THE DATABASE");
      e.printStackTrace();
    }
    return computers;
  }
  
  /**
   * Method that sort all computers by introduced.
   */
  public List<Computer> sortByIntro(String type, int begin) throws SQLException {
    List<Computer> computers = new ArrayList<Computer>();
    ResultSet resultat = null;
    try (Connection connexion = daoFactory.getConnection();
        Statement statement = connexion.createStatement()) {
      resultat = statement.executeQuery(
          getall + " order by introduced " + type + " LIMIT 50 OFFSET " + begin);
      CompanyDao cd = daoFactory.getCompanyDao();
      while (resultat.next()) {
        Integer id = resultat.getInt("id");
        String name = resultat.getString("name");
        Timestamp introduced = resultat.getTimestamp("introduced");
        Timestamp discontinued = resultat.getTimestamp("discontinued");
        int idcompany = resultat.getInt("company_id");

        Computer c = new Computer(id, name, introduced, discontinued, cd.getCompany(idcompany));
        computers.add(c);
      }
      LOG.info("Request succesfully executed (GET ALL COMPUTERS SORTED BY NAME)! ");
    } catch (SQLException e) {
      LOG.error("ERROR COULD NOT CONNECT TO THE DATABASE");
      e.printStackTrace();
    }
    return computers;
  }
  
  /**
   * Method that sort all computers by introduced.
   */
  public List<Computer> sortByDisc(String type, int begin) throws SQLException {
    List<Computer> computers = new ArrayList<Computer>();
    ResultSet resultat = null;
    try (Connection connexion = daoFactory.getConnection();
        Statement statement = connexion.createStatement()) {
      resultat = statement.executeQuery(
          getall + " order by discontinued " + type + " LIMIT 50 OFFSET " + begin);
      CompanyDao cd = daoFactory.getCompanyDao();
      while (resultat.next()) {
        Integer id = resultat.getInt("id");
        String name = resultat.getString("name");
        Timestamp introduced = resultat.getTimestamp("introduced");
        Timestamp discontinued = resultat.getTimestamp("discontinued");
        int idcompany = resultat.getInt("company_id");

        Computer c = new Computer(id, name, introduced, discontinued, cd.getCompany(idcompany));
        computers.add(c);
      }
      LOG.info("Request succesfully executed (GET ALL COMPUTERS SORTED BY NAME)! ");
    } catch (SQLException e) {
      LOG.error("ERROR COULD NOT CONNECT TO THE DATABASE");
      e.printStackTrace();
    }
    return computers;
  }
}
