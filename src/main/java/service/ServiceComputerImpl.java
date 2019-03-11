package service;

import dao.ComputerDao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Computer;

/**
 * Class ServiceComputerImpl.
 * 
 * @author mnouville
 * @version 1.0
 */
@Service
public class ServiceComputerImpl implements ServiceComputer {

  @Autowired
  private ComputerDao computerDao;
  
  /**
   * Method for adding a computer in the database.
   * 
   * @param c Computer
   */
  @Override
  public void addComputer(Computer c) throws SQLException {
    this.computerDao.addComputer(c);
  }

  /**
   * Method for deleting a computer.
   * 
   * @param id int
   */
  @Override
  public void deleteComputer(int id) throws SQLException {
    this.computerDao.deleteComputer(id);
  }

  /**
   * Method for updating a computer.
   * 
   * @param c Computer
   */
  @Override
  public void updateComputer(Computer c) throws SQLException {
    this.computerDao.updateComputer(c);
  }

  /**
   * Method for getting a particular Computer from the database.
   * 
   * @param id int
   */
  @Override
  public Computer getComputer(int id) throws SQLException {
    Computer c = this.computerDao.getComputer(id);

    return c;
  }

  /**
   * Method for having the list of every Computers in the Database.
   */
  @Override
  public List<Computer> getComputers() throws SQLException {
    List<Computer> list = this.computerDao.getComputers();

    return list;
  }

  /**
   * Return a list a computer base on a beginning ID.
   * 
   * @param begin int
   */
  @Override
  public List<Computer> getComputers(int begin) throws SQLException {
    return this.computerDao.getComputers(begin);
  }

  /**
   * Method for having the max Id in the database.
   */
  @Override
  public int getMaxId() throws SQLException {
    return this.computerDao.getMaxId();
  }

  /**
   * Method that return the number of computers.
   */
  @Override
  public int getCount() throws SQLException {
    return this.computerDao.getCount();
  }

  /**
   * Method that list some computer sorted by name.
   * @param search String
   */
  @Override
  public List<Computer> searchName(String search) throws SQLException {
    return this.computerDao.searchName(search);
  }
  
  /**
   * Method that list computers ordered by name.
   * @param type String
   * @param begin int
   * @return List of computers
   */
  public List<Computer> sortByName(String type, int begin) throws SQLException {
    return this.computerDao.sortByName(type, begin);
  }
  
  /**
   * Method that list computers ordered by Introduced.
   * @param type String
   * @param begin int
   * @return List of computers
   */
  public List<Computer> sortByIntro(String type, int begin) throws SQLException {
    return this.computerDao.sortByIntro(type, begin);
  }
  
  /**
   * Method that list computers ordered by Introduced.
   * @param type String
   * @param begin int
   * @return List of computers
   */
  public List<Computer> sortByDisc(String type, int begin) throws SQLException {
    return this.computerDao.sortByDisc(type, begin);
  }
  
  /**
   * Method that list computers ordered by Company Name.
   * @param type String
   * @param begin int
   * @return List of computers
   */
  public List<Computer> sortByCompanyName(String type, int begin) throws SQLException {
    return this.computerDao.sortByCompanyName(type, begin);
  }
}
