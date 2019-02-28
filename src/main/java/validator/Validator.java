package validator;

import dao.DaoFactory;

import java.sql.SQLException;

import model.Computer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.ServiceCompany;
import service.ServiceCompanyImpl;

/**
 * Class Validator.java
 * @author mnouville
 * @version 1.0
 */
public class Validator {

  private static final Logger LOG = LoggerFactory.getLogger(Validator.class);
  private DaoFactory daoFactory;
  private ServiceCompany serviceCompany;

  public Validator() {
    this.daoFactory = DaoFactory.getInstance();
    this.serviceCompany = new ServiceCompanyImpl(daoFactory);
  }
  
  /**
   * Method for the validation of Computer Name.
   * @param name String
   * @return
   */
  public boolean validName(String name) {
    if (name.equals("") || name == null) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Method for the validation of introduced Parameter.
   * @param introduced String
   * @return
   */
  public boolean validIntroduced(String introduced) {
    if (!introduced.equals("") && introduced != null) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Method for the validation of discontinued Parameter.
   * @param discontinued String
   * @return
   */
  public boolean validDiscontinued(String discontinued) {
    if (!discontinued.equals("") && discontinued != null) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Method for the validation of company id Parameter.
   * @param id int
   * @return
   */
  public boolean validCompanyId(int id) throws SQLException {
    try {
      // check if the id is a valid company that exist in the table company
      if (this.serviceCompany.companyExist(id)) {
        return true;
      } else {
        LOG.error("The following Company Does not exist : " + id);
        return false;
      }
    } catch (NumberFormatException nfe) {
      return false;
    }
  }
  
  /**
   * Method for the validation of every elements of a computer.
   * @param c Computer
   * @return boolean
   */
  public boolean validDates(Computer c) {
    if (c.getIntroduced() != null && c.getDiscontinued() != null) {
      if (c.getIntroduced().before(c.getDiscontinued())) {
        return true;
      } else {
        return false;
      }
    } else {
      return true;
    }
  }
}
