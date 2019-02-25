package testmodel;

import static org.junit.Assert.assertEquals;

import model.Company;

import org.junit.Test;

/**
 * Unit Test for Company.java
 * @author mnouville
 * @version 1.0
 */
public class CompanyTest {

  /**
   * Method for testing equals Method of Company.java
   */
  @Test
  public void testequals() {
    Company c1 = new Company(1,"Company1");
    Company c2 = new Company(2,"Company2");
    
    assertEquals(false, c1.equals(c2));
    
    c2.setId(1);
    c2.setName("Company1");
    
    assertEquals(true, c1.equals(c2));
  }

}
