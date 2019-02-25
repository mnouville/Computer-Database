package test;

import static org.junit.Assert.assertEquals;

import dao.ComputerDao;
import dao.DaoFactory;

import java.sql.SQLException;
import java.util.List;

import model.Company;
import model.Computer;

import org.junit.Test;

public class ComputerDaoImplTest {

  private DaoFactory daoFactory = DaoFactory.getInstance();

  /**
   * JUnit test for adding computer.
   * 
   */
  @Test
  public void testAddComputer() throws SQLException {
    Computer c = new Computer(575, "UnitTestComputer", null, null, new Company(1, "Apple Inc."));
    ComputerDao cd = daoFactory.getComputerDao();
    cd.addComputer(c);

    List<Computer> computers = cd.getComputers();

    Computer cc = cd.getComputer(574);
    System.out.println(c.equals(cc));

    boolean b = false;
    for (int i = 0; i < computers.size(); i++) {
      if (computers.get(i).equals(c)) {
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
    ComputerDao cd = daoFactory.getComputerDao();
    Computer c = cd.getComputer(575);
    cd.deleteComputer(c.getId());
    List<Computer> computers = cd.getComputers();

    boolean b = false;
    for (int i = 0; i < computers.size(); i++) {
      if (computers.get(i).equals(c)) {
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
    ComputerDao cd = daoFactory.getComputerDao();
    Computer c = cd.getComputer(1);

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
    ComputerDao cd = daoFactory.getComputerDao();
    List<Computer> computers = cd.getComputers();

    boolean b = false;
    if (computers.size() > 0) {
      b = true;
    }
      
    assertEquals(true, b);
  }

}
