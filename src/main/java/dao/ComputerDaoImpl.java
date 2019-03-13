package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Company;
import model.Computer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.zaxxer.hikari.HikariDataSource;

import mappers.ComputerRowMapper;

/**
 * Class that contains every method concerning Computer in the Database.
 * 
 * @author mnouville
 * @version 1.0
 */
@Repository
public class ComputerDaoImpl implements ComputerDao {

  @Autowired
  private CompanyDaoImpl companyDao;
  private static final Logger LOG = LoggerFactory.getLogger(ComputerDaoImpl.class);
  private final String insert = "INSERT INTO computer(id,name,introduced,discontinued,company_id) "
                               + "VALUES (?,?,?,?,?);";
  private final String getall = "SELECT id,name,introduced,discontinued,company_id FROM computer";
  private final String update = "update computer set name = ?, introduced = ? , discontinued = ?, company_id = ? where id = ?";
  private final String delete = "DELETE FROM computer WHERE id = ?";
  private final String get = "SELECT id,name,introduced,discontinued,company_id "
                           + "FROM computer where id = :id";
  private final String maxid = "SELECT MAX(id) FROM computer;";
  private final String count = "SELECT COUNT(id) FROM computer;";
  private final String sortcompanyname = "SELECT c.id,c.name,c.introduced,c.discontinued,"
                                       + "c.company_id FROM computer c" 
                                       + " LEFT JOIN company comp on c.company_id = comp.id ";

  @Autowired
  private HikariDataSource dataSource;
  
  @Autowired
  private ComputerRowMapper computerRawMapper;
  
  private JdbcTemplate jdbcTemplate;
  
  private NamedParameterJdbcTemplate njdbcTemplate;

  @Autowired
  public void setDatasource(HikariDataSource ds) {
    this.jdbcTemplate = new JdbcTemplate(ds);
    njdbcTemplate = new NamedParameterJdbcTemplate(ds);
  }
  
  /**
   * Method that return the connection of Hikari
   * @return the connection to the database
   */
  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }
  
  /**
   * Constructor of ComputerDaoImpl.
   * 
   * @param daoFactory DaoFactory
   */
  ComputerDaoImpl() { }

  /**
   * This method take a Computer in parameter and add it into the Database.
   * 
   * @param c Computer
   */
  @Override
  public void addComputer(Computer c) throws SQLException {
    this.jdbcTemplate.update(insert,c.getId(),c.getName(),c.getIntroduced(),c.getDiscontinued(),c.getCompany().getId());
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
    try (Connection connexion = this.getConnection();
        Statement statement = connexion.createStatement()) {
      resultat = statement.executeQuery(getall + " LIMIT 50;");
      while (resultat.next()) {
        Integer id = resultat.getInt("id");
        String name = resultat.getString("name");
        Timestamp introduced = resultat.getTimestamp("introduced");
        Timestamp discontinued = resultat.getTimestamp("discontinued");
        int idcompany = resultat.getInt("company_id");

        Computer c = new Computer(id, name, introduced, discontinued, companyDao.getCompany(idcompany));
        computers.add(c);
      }
      LOG.info("Request succesfully executed (GET ALL COMPUTERS)! ");
    } catch (SQLException e) {
      LOG.error("ERROR COULD NOTNamedParameterJdbcTemplate CONNECT TO THE DATABASE");
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

    try (Connection connexion = this.getConnection();
        Statement statement = connexion.createStatement()) {
      resultat = statement.executeQuery(getall + " LIMIT 50 OFFSET " + begin);
      while (resultat.next()) {
        Integer id = resultat.getInt("id");
        String name = resultat.getString("name");
        Timestamp introduced = resultat.getTimestamp("introduced");
        Timestamp discontinued = resultat.getTimestamp("discontinued");
        int idcompany = resultat.getInt("company_id");

        Computer c = new Computer(id, name, introduced, discontinued, companyDao.getCompany(idcompany));
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
    this.jdbcTemplate.update(delete,Long.valueOf(id));
  }

  /**
   * This method return the Computer with this ID in the Database.
   * 
   * @param i int
   * @return an object Computer
   */
  @Override
  public Computer getComputer(int i) throws SQLException {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", i);
    RowMapper<Computer> rowMapper = this.computerRawMapper.getRowMapperComputer();
    return njdbcTemplate.queryForObject(get, params, rowMapper);
  }

  /**
   * This method permit updates on computers in the database.
   * 
   * @param c Computer
   */
  @Override
  public void updateComputer(Computer c) throws SQLException {
    this.jdbcTemplate.update(update,c.getName(), c.getIntroduced(), c.getDiscontinued(), c.getCompany().getId(), c.getId());
  }

  /**
   * Return the Maximum Id in the database.
   * 
   * @return Int that correspond to the Max Id
   */
  @Override
  public int getMaxId() throws SQLException {
    return this.jdbcTemplate.queryForObject(maxid, Integer.class) + 1;
  }

  /**
   * Return the number of row in the database.
   * 
   * @return int
   */
  public int getCount() throws SQLException {
    return this.jdbcTemplate.queryForObject(count, Integer.class);
  }

  /**
   * Method that sort all computers by name.
   */
  public List<Computer> searchName(String search) throws SQLException {
    List<Computer> computers = new ArrayList<Computer>();
    ResultSet resultat = null;
    try (Connection connexion = this.getConnection();
        Statement statement = connexion.createStatement()) {
      resultat = statement.executeQuery(getall + " where name LIKE '%" + search + "%' LIMIT 50;");
      while (resultat.next()) {
        Integer id = resultat.getInt("id");
        String name = resultat.getString("name");
        Timestamp introduced = resultat.getTimestamp("introduced");
        Timestamp discontinued = resultat.getTimestamp("discontinued");
        int idcompany = resultat.getInt("company_id");

        Computer c = new Computer(id, name, introduced, discontinued, companyDao.getCompany(idcompany));
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
  public List<Computer> sortByColumn(String type, int begin, String column) throws SQLException {
    List<Computer> computers = new ArrayList<Computer>();
    ResultSet resultat = null;
    
    try (Connection connexion = this.getConnection();
        Statement statement = connexion.createStatement()) {
      
      if (column.equals("company")) {
        resultat = statement.executeQuery(
            sortcompanyname + "order by ISNULL(comp.name),comp.name " + type + " LIMIT 50 OFFSET " + begin);
        
      } else {
        resultat = statement.executeQuery(
            getall + " order by " + column + " " + type + " LIMIT 50 OFFSET " + begin);
      } 
      
      while (resultat.next()) {
        Integer id = resultat.getInt("id");
        String name = resultat.getString("name");
        Timestamp introduced = resultat.getTimestamp("introduced");
        Timestamp discontinued = resultat.getTimestamp("discontinued");
        int idcompany = resultat.getInt("company_id");

        Computer c = new Computer(id, name, introduced, discontinued, companyDao.getCompany(idcompany));
        computers.add(c);
      }
      
      LOG.info("Request succesfully executed "
             + "(GET ALL COMPUTERS SORTED BY " + column + " " + type + ")!");
      
    } catch (SQLException e) {
      LOG.error("ERROR COULD NOT CONNECT TO THE DATABASE");
      e.printStackTrace();
    }
    
    return computers;
  }
}
