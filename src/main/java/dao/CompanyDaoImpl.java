package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.zaxxer.hikari.HikariDataSource;



/**
 * Class that contains every method concerning Company in the Database.
 * 
 * @author mnouville
 * @version 1.0
 */
@Repository
public class CompanyDaoImpl implements CompanyDao {

  private static final Logger LOG = LoggerFactory.getLogger(CompanyDaoImpl.class);
  private final String insert = "INSERT INTO company(id,name) VALUES (?,?);";
  private final String getall = "SELECT id,name FROM company;";
  private final String get = "SELECT id,name FROM company where id =";
  private final String count = "SELECT COUNT(*) from company where id =";
  
  @Autowired
  private HikariDataSource dataSource;

  private JdbcTemplate jdbcTemplate;
  
  private NamedParameterJdbcTemplate njdbcTemplate;
  
  /**
   * Method that return the connection of Hikari
   * @return the connection to the database
   */
  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }
  /**
   * Constructor of CampanyDaoImpl.
   * 
   * @param daoFactory DaoFactory
   */
  CompanyDaoImpl() { }

  /**
   * This method take a Company in parameter and add it into the Database.
   * 
   * @param c Company
   */
  @Override
  public void addCompany(Company c) throws SQLException {
    this.jdbcTemplate.update(insert,c.getId(),c.getName());
  }

  /**
   * This method return a list of every companies in the Database.
   * 
   * @return List of Object Company
   */
  @Override
  public List<Company> getCompanies() throws SQLException {
    List<Company> companies = new ArrayList<Company>();
    ResultSet resultat = null;
    try (Connection connexion = this.getConnection();
        Statement statement = connexion.createStatement()) {
      resultat = statement.executeQuery(getall);

      while (resultat.next()) {
        Integer id = resultat.getInt("id");
        String name = resultat.getString("name");

        Company c = new Company(id, name);
        companies.add(c);
      }
      LOG.info("Request succesfully executed (GET ALL COMPANIES : " + companies.size() + ")! ");
    } catch (SQLException e) {
      LOG.error("ERROR COULD NOT ACCESS TO THE DATABASE");
      e.printStackTrace();
    }
    return companies;
  }

  /**
   * This method delete companies by ID.
   * 
   * @param id int
   */
  @Override
  public void deleteCompany(int id) throws SQLException {    
    
    try (Connection connect = this.getConnection();
        PreparedStatement statementComputers = 
            connect.prepareStatement("DELETE FROM computer WHERE company_id = " + id);
        PreparedStatement statementCompany = 
            connect.prepareStatement("DELETE FROM company WHERE id = " + id)) {
      try {
        connect.setAutoCommit(false);
        statementComputers.execute();
        LOG.info("The statement " + statementComputers + " has been executed.");
        statementCompany.execute();
        LOG.info("The statement " + statementCompany + " has been executed.");
        connect.commit();
      } catch (SQLException e) {
        LOG.error(e.getMessage(), e);
        connect.rollback();
      }
    } catch (SQLException e) {
      LOG.warn(e.getMessage(), e);
    }
  }

  /**
   * This method return the Company with this ID in the Database.
   * 
   * @param i int
   * @return Object Company
   */
  @Override
  public Company getCompany(int i) throws SQLException {
    Company c = new Company();
    ResultSet resultat = null;

    try (Connection connexion = this.getConnection();
        Statement statement = connexion.createStatement()) {
      resultat = statement.executeQuery(get + i + ";");
      while (resultat.next()) {
        Integer id = resultat.getInt("id");
        String name = resultat.getString("name");
        c = new Company(id, name);
      }
      if ( c.getId() == 0 ) {
        c = new Company(0,"");
      }
      //LOG.info("Request succesfully executed (GET COMPANY)! ");
    } catch (SQLException e) {
      LOG.error("ERROR COULD NOT ACCESS TO THE DATABASE");
      e.printStackTrace();
    }

    return c;
  }

  /**
   * This method return a true if a Company ID exist in the Database.
   * 
   * @return boolean
   */
  @Override
  public boolean companyExist(int id) throws SQLException {
    ResultSet resultat = null;

    try (Connection connexion = this.getConnection();
        Statement statement = connexion.createStatement()) {
      resultat = statement.executeQuery(count + id + ";");

      if (resultat.next()) {
        Integer count = resultat.getInt("COUNT(*)");
        if (count > 0) {
          return true;
        } else {
          return false;
        }
      }
      LOG.info("Request succesfully executed (COMPANY EXIST)! ");
    } catch (SQLException e) {
      LOG.error("ERROR COULD NOT ACCESS TO THE DATABASE");
      e.printStackTrace();
    }
    return false;
  }
}
