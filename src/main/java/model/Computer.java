package model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Class for Objects Computer.
 * 
 * @author mnouville
 * @version 1.0
 */
public class Computer {

  private int id;
  private String name;
  private Date introduced;
  private Date discontinued;
  private Company company;

  /**
   * Constructor of the class Computer.
   * 
   * @param id int
   * @param name String
   * @param introduced Timestamp
   * @param discontinued Timestamp
   * @param c Company
   */
  public Computer(int id, String name, Timestamp introduced, Timestamp discontinued, Company c) {
    this.id = id;
    this.name = name;
    if (introduced != null) {
      this.introduced = new Date(introduced.getTime());
    }
      
    if (discontinued != null) {
      this.discontinued = new Date(discontinued.getTime());
    }
      
    this.company = c;
  }

  /**
   * Constructor of the class Computer.
   * 
   * @param id int
   * @param name String
   * @param introduced Date
   * @param discontinued Date
   * @param c Company
   */
  public Computer(int id, String name, Date introduced, Date discontinued, Company c) {
    this.id = id;
    this.name = name;
    this.introduced = introduced;
    this.discontinued = discontinued;
    this.company = c;
  }

  /**
   * Empty Constructor for the class Computer.
   */
  public Computer() {

  }

  /**
   * Method that check if the computer has valid attributes like a true name and consistent Date.
   * 
   * @return Boolean
   */
  public boolean validComputer() {
    boolean valid = false;
    if (this.getIntroduced() != null && this.getDiscontinued() != null) {
      if (this.getIntroduced().before(this.getDiscontinued())) {
        valid = true;
      } else {
        return false;
      }
    }
    if (this.getName().equals("")) {
      return false;
    } else {
      valid = true;
    }
    return valid;
  }

  /**
   * Getter of Computer, return the id of a Computer.
   * 
   * @return int that correspond to the id of the Computer
   */
  public int getId() {
    return id;
  }

  /**
   * Setter of Computer, set manually the id of a Computer.
   * 
   * @param id int that correspond to the id of the Computer
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Getter of Computer, return the name of a Computer.
   * 
   * @return String that correspond to the name of the Computer
   */
  public String getName() {
    return name;
  }

  /**
   * Setter of Computer, set manually the name of a Computer.
   * 
   * @param name String that correspond to the name of the Computer
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter of Computer, return the Date when the computer was introduiced.
   * 
   * @return Date
   */
  public Date getIntroduced() {
    return introduced;
  }

  /**
   * Setter of Computer, set manually the introduced Date of a Computer.
   * 
   * @param introduced Date that correspond to the introduced Date of a Computer
   */
  public void setIntroduced(Date introduced) {
    this.introduced = introduced;
  }

  /**
   * Getter of Computer, return the Date when the computer was Discontinued.
   * 
   * @return Date
   */
  public Date getDiscontinued() {
    return discontinued;
  }

  /**
   * Setter of Computer, set manually the discontinued Date of a Computer.
   * 
   * @param discontinued Date that correspond to the discontinued Date of a Computer
   */
  public void setDiscontinued(Date discontinued) {
    this.discontinued = discontinued;
  }

  /**
   * Getter of Computer, return the Company Id of the Computer.
   * 
   * @return Int that correspond to the Company Id
   */
  public Company getCompany() {
    return company;
  }

  /**
   * Setter of Computer, set manually the Company Id of a Computer.
   * 
   * @param c Company
   */
  public void setCompany(Company c) {
    this.company = c;
  }

  /**
   * Override Method Equals from Computer.
   * 
   * @param c Computer
   * @return boolean
   */
  public boolean equals(Computer c) {
    if (this.id == c.getId() && this.name.equals(c.getName())
        && this.introduced.equals(c.getIntroduced())
        && this.discontinued.equals(c.getDiscontinued()) && this.company.equals(c.getCompany())) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * toString method for the class Computer Have different utilities for the View part and for
   * debugging.
   * 
   * @return String that correspond to the status of a Computer Object
   */
  @Override
  public String toString() {
    String s = "";
    s += "ID : " + this.id + "\tName : " + this.name + "\tIntroduced : " + this.introduced
        + "\tDiscontinued : " + this.discontinued + "\tCompany_id : " + this.company.toString();
    return s;
  }
}
