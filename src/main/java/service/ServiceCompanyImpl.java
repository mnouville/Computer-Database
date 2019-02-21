package service;

import java.sql.SQLException;
import java.util.List;

import dao.CompanyDao;
import dao.DaoFactory;
import model.Company;

/**
 * Class ServiceCompanyImpl
 * @author mnouville
 * @version 1.0
 */
public class ServiceCompanyImpl implements ServiceCompany {

	private CompanyDao companyDao;
	
	/**
	 * Constructor of the class ServiceCompanyImpl
	 * @param daoFactory Object
	 */
	public ServiceCompanyImpl(DaoFactory daoFactory)
	{
		this.companyDao = daoFactory.getCompanyDao();
	}
	
	/**
	 * Method for adding new Company in the Database
	 * @param Company Object
	 */
	@Override
	public void addCompany(Company c) throws SQLException {
		this.companyDao.addCompany(c);
	}

	/**
	 * Method for deleting Company
	 * @param int id of the Company
	 */
	@Override
	public void deleteCompany(int id) throws SQLException {
		this.companyDao.deleteCompany(id);
	}

	/**
	 * Method for getting a particular Company
	 * @param int id of the Company
	 */
	@Override
	public Company getCompany(int id) throws SQLException {
		return this.companyDao.getCompany(id);
	}

	/**
	 * Method for getting every Companies in the database
	 */
	@Override
	public List<Company> getCompanies() throws SQLException {
		return this.companyDao.getCompanies();
	}

	/**
	 * Method for checking if a Company exist
	 * @param int id of the company
	 */
	@Override
	public boolean companyExist(int id) throws SQLException {
		return this.companyDao.companyExist(id);
	}

}
