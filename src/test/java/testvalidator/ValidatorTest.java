package testvalidator;

import java.sql.SQLException;
import java.text.ParseException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import dto.Dto;
import exceptions.ValidationException;
import mappers.MapperDto;
import model.Computer;
import springconfig.SpringConfig;
import validator.Validator;

/**
 * Unit Test class for Validator.java
 * @author mnouville
 * @version 1.0
 */
public class ValidatorTest {

  @Autowired
  private static Validator validator;
  
  @Autowired
  private MapperDto mapper;
  
  private static ApplicationContext applicationContext; 
  
  /**
   * Sets the up before class.
   *
   * @throws Exception the exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
    validator = applicationContext.getBean("validator", Validator.class);
  }
  
  @AfterClass
  public static void setUpAfterClass() throws Exception {
    ((ConfigurableApplicationContext)applicationContext).close();
  }
  
  @Test(expected = ValidationException.class)
  public void verifyIntroBeforeDisco1() throws ValidationException, ParseException, SQLException {
    Dto dto = new Dto("1","Name","2019-01-23", "2019-01-15", "1","");
    
    Computer computer = mapper.dtoToComputer(dto);
    validator.verifyIntroBeforeDisco(computer);
  }
  
  @Test(expected = ValidationException.class)
  public void verifyIntroBeforeDisco2() throws ValidationException, ParseException, SQLException {
    Dto dto = new Dto("1","Name","2019-01-15", "2019-01-15", "1","");
    
    Computer computer = mapper.dtoToComputer(dto);
    validator.verifyIntroBeforeDisco(computer);
  }
  
  @Test(expected = ValidationException.class)
  public void verifyIntroBeforeDisco3() throws ValidationException, ParseException, SQLException {
    Dto dto = new Dto("1","Name","2019-01-16", "2019-01-15", "1","");
    
    Computer computer = mapper.dtoToComputer(dto);
    validator.verifyIntroBeforeDisco(computer);
  }
  
  @Test(expected = ValidationException.class)
  public void verifyIntroBeforeDisco4() throws ValidationException, ParseException, SQLException {
    Dto dto = new Dto("1","Name","", "2019-01-15", "1","");
    
    Computer computer = mapper.dtoToComputer(dto);
    validator.verifyIntroBeforeDisco(computer);
  }
  
  @Test(expected = ValidationException.class)
  public void verifyIntroBeforeDisco5() throws ValidationException, ParseException, SQLException {
    Dto dto = new Dto("1","Name",null, "2019-01-15", "1","");
    
    Computer computer = mapper.dtoToComputer(dto);
    validator.verifyIntroBeforeDisco(computer);
  }

}
