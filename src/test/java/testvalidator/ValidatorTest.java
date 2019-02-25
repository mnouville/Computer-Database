package testvalidator;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Test;

import validator.Validator;

/**
 * Unit Test class for Validator.java
 * @author mnouville
 * @version 1.0
 */
public class ValidatorTest {

  private Validator validator = new Validator();
  
  /**
   * Method for testing validIntroduced method of validator.java
   */
  @Test
  public void testValidIntroduced() {
    
    String introduced = "10-05-1996";
    
    boolean validIntroduced = validator.validIntroduced(introduced);
    
    assertEquals(true, validIntroduced);
    
    introduced = "1996-05-10";
    
    validIntroduced = validator.validIntroduced(introduced);
    
    assertEquals(true, validIntroduced);
    
    introduced = "UnitTest";
    
    validIntroduced = validator.validIntroduced(introduced);
    
    assertEquals(false, validIntroduced);
  }
  
  /**
   * Method for testing validDiscontinued method of validator.java
   */
  @Test
  public void testValidDiscontinued() {
    
    String discontinued = "10-05-1996";
    
    boolean validDiscontinued = validator.validDiscontinued(discontinued);
    
    assertEquals(true, validDiscontinued);
    
    discontinued = "1996-05-10";
    
    validDiscontinued = validator.validDiscontinued(discontinued);
    
    assertEquals(true, validDiscontinued);
    
    discontinued = "UnitTest";
    
    validDiscontinued = validator.validDiscontinued(discontinued);
    
    assertEquals(false, validDiscontinued);
  }
  
  /**
   * Method for testing validCompanyId method of validator.java
   */
  @Test
  public void testValidCompanyId() throws SQLException {
    
    int companyId = 1;
    
    boolean validCompanyId = validator.validCompanyId(companyId);
    
    assertEquals(true, validCompanyId);
    
    companyId = 10000;
    
    validCompanyId = validator.validCompanyId(companyId);
    
    assertEquals(false,validCompanyId);
    
  }

}
