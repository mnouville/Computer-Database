package validator;

import dao.DaoFactory;

import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.ServiceCompany;
import service.ServiceCompanyImpl;

public class Validator {

  private static final Logger LOG = LoggerFactory.getLogger(Validator.class);
  private DaoFactory daoFactory;
  private ServiceCompany serviceCompany;

  public Validator() {
    this.daoFactory = DaoFactory.getInstance();
    this.serviceCompany = new ServiceCompanyImpl(daoFactory);
  }

  /**
   * Method for the validation of introduced Parameter.
   * @param introduced String
   * @return
   */
  public boolean validIntroduced(String introduced) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
    // Check if the value is a valid timestamp
    try {
      if (!introduced.equals("")) {
        formatter.parse(introduced);
      }
      return true;
    } catch (ParseException e) {
      return false;
    }
  }
  
  /**
   * Method for the validation of discontinued Parameter.
   * @param discontinued String
   * @return
   */
  public boolean validDiscontinued(String discontinued) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
    // Check if the value is a valid timestamp
    try {
      if (!discontinued.equals("")) {
        formatter.parse(discontinued);
      }
      return true;
    } catch (ParseException e) {
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
}
