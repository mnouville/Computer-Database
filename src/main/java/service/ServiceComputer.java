package service;

import java.sql.SQLException;
import java.util.List;

import model.Computer;

/**
 * Class ServiceComputer that implement every methods of ServiceComputerImpl
 * @author mnouville
 * @version 1.0
 */
public interface ServiceComputer {
	/**
	 * This method take a Computer in parameter and add it into the Database
	 * @param c
	 * @throws SQLException
	 */
	void addComputer(Computer c) throws SQLException;
	
	/**
	 * This method delete computers by ID
	 * @param id
	 * @throws SQLException
	 */
	void deleteComputer(int id) throws SQLException;
	
	/**
	 * This method return the Computer with this ID in the Database
	 * @param id
	 * @return an object Computer
	 * @throws SQLException
	 */
	Computer getComputer(int id) throws SQLException;
	
	/**
	 * This method return a list of every computers in the Database
	 * @return List of Objects Computer
	 * @throws SQLException
	 */
	List<Computer> getComputers() throws SQLException;
	
	/**
	 * Return the Maximum Id in the database
	 * @return Int that correspond to the Max Id
	 * @throws SQLException
	 */
	int getMaxId() throws SQLException;
	
	/**
	 * This method permit updates on computers in the database
	 * @param c
	 * @throws SQLException
	 */
	void updateComputer(Computer c) throws SQLException;
}
