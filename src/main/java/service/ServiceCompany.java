package service;

import java.sql.SQLException;
import java.util.List;

import model.Company;

/**
 * Class Service Company that implement every methods of ServiceCompanyImpl
 * @author mnouville
 * @version 1.0
 */
public interface ServiceCompany {

	/**
	 * This method take a Company in parameter and add it into the Database
	 * @param c
	 * @throws SQLException
	 */
	void addCompany(Company c) throws SQLException;
	
	/**
	 * This method delete companies by ID
	 * @param id
	 * @throws SQLException
	 */
	void deleteCompany(int id) throws SQLException;
	
	/**
	 * This method return the Company with this ID in the Database
	 * @param id
	 * @return Object Company
	 * @throws SQLException
	 */
	Company getCompany(int id) throws SQLException;
	
	/**
	 * This method return a list of every companies in the Database
	 * @return List of Object Company
	 * @throws SQLException
	 */
	List<Company> getCompanies() throws SQLException;
	
	/**
	 * This method return a true if a Company ID exist in the Database
	 * @return boolean
	 * @throws SQLException
	 */
	boolean companyExist(int id) throws SQLException;
	
}
