package testmodel;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Company;
import model.Computer;

import org.junit.Test;

public class ComputerTest {

  @Test
  public void testequals() throws ParseException {
    
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
    Date introduced = formatter.parse("2012-12-12");
    Date discontinued = formatter.parse("2013-13-13");
    
    Computer c1 = new Computer(1,"Computer 1", introduced, discontinued, 
                  new Company(1,"Company 1"));
    Computer c2 = new Computer(2,"Computer 2", introduced, discontinued, 
                  new Company(1,"Company 1"));
    
    assertEquals(false, c1.equals(c2));
    
    c2.setId(1);
    c2.setName("Computer 1");
    
    assertEquals(true, c1.equals(c2));
  }

}
