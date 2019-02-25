package model;

/**
 * Class for Object Company.
 * 
 * @author mnouville
 * @version 1.0
 */
public class Company {

  private int id;
  private String name;

  /**
   * Constructor of the Class Company.
   * 
   * @param id   int that correspond to the id of the Company
   * @param name String that correspond to the name of the Company
   */
  public Company(int id, String name) {
    this.setId(id);
    this.setName(name);
  }

  /**
   * Empty Constructor of Company.
   */
  public Company() {

  }

  /**
   * Getter of Company, return the id of a Company.
   * 
   * @return int that correspond to the id of a Company
   */
  public int getId() {
    return id;
  }

  /**
   * Setter of Company, set manually the id of a Company.
   * 
   * @param id int that correspond to the id of the Company
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Getter of Company, return the name of a Company.
   * 
   * @return String that correspond to the name of a Company
   */
  public String getName() {
    return name;
  }

  /**
   * Setter of Company, set manually the name of a Company.
   * 
   * @param name String that correspond to the name of the Company
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Override method equals of the class Company.
   * 
   * @param c Object Company
   * @return boolean
   */
  public boolean equals(Company c) {
    if (this.id == c.getId() && this.name.equals(c.getName())) {
      return true;
    } else {
      return false;
    }   
  }

  /**
   * toString method for the class Company Have different utilities for the View part and for
   * debugging.
   * 
   * @return String that correspond to the status of a Company field
   */
  @Override
  public String toString() {
    String s = "";
    s += "ID : " + this.id + "\t" + this.name;
    return s;
  }

}
