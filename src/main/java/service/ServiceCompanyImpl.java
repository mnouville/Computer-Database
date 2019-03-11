package service;

import dao.CompanyDao;
import dao.CompanyDaoImpl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Company;

/**
 * Class ServiceCompanyImpl.
 * 
 * @author mnouville
 * @version 1.0
 */
@Service
public class ServiceCompanyImpl implements ServiceCompany {

  @Autowired
  private CompanyDao companyDao;
  private static ServiceCompanyImpl single_instance = null;

  /**
   * Constructor of the class ServiceCompanyImpl.
   * 
   * @param daoFactory Object
   */
  public ServiceCompanyImpl() {
    this.companyDao = CompanyDaoImpl.getInstance();
  }
  
  /**
   * Return unique instance of ServiceCompanyImpl.
   * @return ServiceCompanyImpl
   */
  public static ServiceCompanyImpl getInstance() {
    // Singleton
    if (single_instance == null) {
      single_instance = new ServiceCompanyImpl();
    }
      
    return single_instance;
  }

  /**
   * Method for adding new Company in the Database.
   * 
   * @param c Company
   */
  @Override
  public void addCompany(Company c) throws SQLException {
    this.companyDao.addCompany(c);
  }

  /**
   * Method for deleting Company.
   * 
   * @param id int
   */
  @Override
  public void deleteCompany(int id) throws SQLException {
    this.companyDao.deleteCompany(id);
  }

  /**
   * Method for getting a particular Company.
   * 
   * @param id int 
   */
  @Override
  public Company getCompany(int id) throws SQLException {
    return this.companyDao.getCompany(id);
  }

  /**
   * Method for getting every Companies in the database.
   */
  @Override
  public List<Company> getCompanies() throws SQLException {
    return this.companyDao.getCompanies();
  }

  /**
   * Method for checking if a Company exist.
   * 
   * @param id int
   */
  @Override
  public boolean companyExist(int id) throws SQLException {
    return this.companyDao.companyExist(id);
  }

}
