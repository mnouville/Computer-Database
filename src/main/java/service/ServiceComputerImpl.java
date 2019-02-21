package service;

import java.sql.SQLException;
import java.util.List;

import dao.ComputerDao;
import dao.DaoFactory;
import model.Computer;

/**
 * Class ServiceComputerImpl
 * @author mnouville
 * @version 1.0
 */
public class ServiceComputerImpl implements ServiceComputer {

	private ComputerDao computerDao;
	
	/**
	 * Constructor of the Class ServiceComputerImpl
	 * @param daoFactory Object
	 */
	public ServiceComputerImpl(DaoFactory daoFactory)
	{
		this.computerDao = daoFactory.getComputerDao();
	}
	
	/**
	 * Method for adding a computer in the database
	 * @param Computer Object
	 */
	@Override
	public void addComputer(Computer c) throws SQLException
	{
		this.computerDao.addComputer(c);
	}

	/**
	 * Method for deleting a computer
	 * @param int id of the computer
	 */
	@Override
	public void deleteComputer(int id) throws SQLException
	{
		this.computerDao.deleteComputer(id);
	}
	
	/**
	 * Method for updating a computer
	 * @param Computer Object
	 */
	@Override
	public void updateComputer(Computer c) throws SQLException
	{
		this.computerDao.updateComputer(c);
	}
	
	/**
	 * Method for getting a particular Computer from the database
	 * @param int Id of the computer
	 */
	@Override
	public Computer getComputer(int id) throws SQLException 
	{
		Computer c = this.computerDao.getComputer(id);
		
		return c;
	}
	
	/**
	 * Method for having the list of every Computers in the Database
	 */
	@Override
	public List<Computer> getComputers() throws SQLException 
	{
		List<Computer> list = this.computerDao.getComputers();
		
		return list;
	}

	/**
	 * Method for having the max Id in the database
	 */
	@Override
	public int getMaxId() throws SQLException {
		return this.computerDao.getMaxId();
	}
}
