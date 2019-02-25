package dao;

import controller.Controller;

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



/**
 * Class that contains every method concerning Company in the Database.
 * 
 * @author mnouville
 * @version 1.0
 */
public class CompanyDaoImpl implements CompanyDao {

  private DaoFactory daoFactory;
  private static final Logger LOG = LoggerFactory.getLogger(Controller.class);
  private final String insert = "INSERT INTO company(id,name) VALUES (?,?);";
  private final String getall = "SELECT id,name FROM company;";
  private final String delete = "DELETE FROM company WHERE id = ";
  private final String get = "SELECT id,name FROM company where id =";
  private final String count = "SELECT COUNT(*) from company where id =";

  /**
   * Constructor of CampanyDaoImpl.
   * 
   * @param daoFactory DaoFactory
   */
  CompanyDaoImpl(DaoFactory daoFactory) {
    this.daoFactory = daoFactory;
  }

  /**
   * This method take a Company in parameter and add it into the Database.
   * 
   * @param c Company
   */
  @Override
  public void addCompany(Company c) throws SQLException {
    try (Connection connexion = daoFactory.getConnection();
        PreparedStatement preparedStatement = connexion.prepareStatement(insert)) {
      preparedStatement.setInt(1, c.getId());
      preparedStatement.setString(2, c.getName());
      preparedStatement.executeUpdate();
      LOG.info("Request succesfully executed (ADD COMPANY)! ");
    } catch (SQLException e) {
      LOG.error("ERROR COULD NOT ACCESS TO THE DATABASE");
      e.printStackTrace();
    }
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
    try (Connection connexion = daoFactory.getConnection();
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
    try (Connection connexion = daoFactory.getConnection();
        PreparedStatement preparedStatement = connexion.prepareStatement(delete + id)) {
      preparedStatement.executeUpdate();
      LOG.info("Request succesfully executed (DELETE COMPANY)! ");
    } catch (SQLException e) {
      LOG.error("ERROR COULD NOT ACCESS TO THE DATABASE");
      e.printStackTrace();
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

    try (Connection connexion = daoFactory.getConnection();
        Statement statement = connexion.createStatement()) {
      resultat = statement.executeQuery(get + i + ";");
      while (resultat.next()) {
        Integer id = resultat.getInt("id");
        String name = resultat.getString("name");
        c = new Company(id, name);
      }
      LOG.info("Request succesfully executed (GET COMPANY)! ");
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

    try (Connection connexion = daoFactory.getConnection();
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
