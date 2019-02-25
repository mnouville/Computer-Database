package testdao;

import static org.junit.Assert.assertEquals;

import dao.CompanyDao;
import dao.DaoFactory;

import java.sql.SQLException;
import java.util.List;

import model.Company;

import org.junit.Test;

/**
 * Class JUnit for testing different methods in CompanyDaoImpl Class.
 * 
 * @author mnouville
 * @version 1.0
 */
public class CompanyDaoImplTest {

  private DaoFactory daoFactory = DaoFactory.getInstance();

  /**
   * JUnit test for adding company.
   * 
   */
  @Test
  public void testAddCompany() throws SQLException {
    Company c = new Company(44, "UnitTestCompany");
    CompanyDao cd = daoFactory.getCompanyDao();
    cd.addCompany(c);

    List<Company> companies = cd.getCompanies();

    boolean b = false;
    for (int i = 0; i < companies.size(); i++) {
      if (companies.get(i).equals(c)) {
        b = true;
      } 
    }
    assertEquals(true, b);
  }

  /**
   * JUnit test for deleting company.
   * 
   */
  @Test
  public void testDeleteCompany() throws SQLException {
    CompanyDao cd = daoFactory.getCompanyDao();
    Company c = cd.getCompany(44);
    cd.deleteCompany(c.getId());
    List<Company> companies = cd.getCompanies();

    boolean b = false;
    for (int i = 0; i < companies.size(); i++) {
      if (companies.get(i).equals(c)) {
        b = true;
      }
    }

    assertEquals(false, b);
  }

  /**
   * JUnit test for getting Company.
   * 
   */
  @Test
  public void testGetCompany() throws SQLException {
    CompanyDao cd = daoFactory.getCompanyDao();
    Company c = cd.getCompany(1);

    boolean b = false;
    if (c != null) {
      b = true;
    }
      
    assertEquals(true, b);
  }

  /**
   * JUnit test for getting all companies.
   * 
   */
  @Test
  public void testGetCompanies() throws SQLException {
    CompanyDao cd = daoFactory.getCompanyDao();
    List<Company> companies = cd.getCompanies();

    boolean b = false;
    if (companies.size() > 0) {
      b = true;
    }

    assertEquals(true, b);
  }

}
