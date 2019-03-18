//package testdao;
//
//import static org.junit.Assert.assertEquals;
//
//import java.sql.SQLException;
//import java.util.List;
//
//import model.Company;
//import model.Computer;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import service.ServiceComputer;
//
//@Component
//public class ComputerDaoImplTest {
//
//  @Autowired
//  private ServiceComputer serviceComputer;
//  
//  /**
//   * JUnit test for adding computer.
//   */
//  @Test
//  public void testAddComputer() throws SQLException {
//    Computer c = new Computer(1000, "UnitTestComputer", null, null, new Company(1, "Apple Inc."));
//    int c1 = serviceComputer.getCount();
//    serviceComputer.addComputer(c);
//    int c2 = serviceComputer.getCount();
//    System.out.println(c1 + " , " + c2);
//    boolean b = false;
//    
//    if (c1 < c2) {
//      b = true;
//    }
//    
//    assertEquals(true, b);
//  }
//
//  /**
//   * JUnit test for deleting company.
//   */
//  @Test
//  public void testDeleteComputer() throws SQLException {
//    Computer c = serviceComputer.getComputer(1000);
//    serviceComputer.deleteComputer(c.getId());
//    List<Computer> computers = serviceComputer.getComputers();
//
//    boolean b = false;
//    if (computers.contains(c)) {
//      b = true;
//    }
//
//    assertEquals(false, b);
//  }
//
//  /**
//   * JUnit test for getting Company.
//   * 
//   */
//  @Test
//  public void testGetCompany() throws SQLException {
//    Computer c = serviceComputer.getComputer(1);
//
//    boolean b = false;
//    if (c != null) {
//      b = true;
//    }
//
//    assertEquals(true, b);
//  }
//
//  /**
//   * JUnit test for getting all companies.
//   * 
//   */
//  @Test
//  public void testGetCompanies() throws SQLException {
//    List<Computer> computers = serviceComputer.getComputers();
//
//    boolean b = false;
//    if (computers.size() > 0) {
//      b = true;
//    }
//      
//    assertEquals(true, b);
//  }
//
//}
