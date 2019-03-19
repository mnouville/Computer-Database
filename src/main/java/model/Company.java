package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class for Object Company.
 * 
 * @author mnouville
 * @version 1.0
 */
@Entity
@Table(name = "company")
public class Company implements java.io.Serializable{

  private static final long serialVersionUID = 1L;
  private int id;
  private String name;
  private Set<Computer> computers = new HashSet<Computer>(0);

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

  public Company(int id, String name, Set<Computer> computers) {
    this.id = id;
    this.name = name;
    this.computers = computers;
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
  @Id 
  @Column(name="id", unique=true, nullable=false)
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
  @Column(name="name")
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
 
  
  public void setComputers(Set<Computer> computers) {
      this.computers = computers;
  }
  
  /**
   * HashCode Method for Company Object.
   */
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  /**
   * Equals method for Company Object.
   */
  public boolean equals(Object obj) {
    if (this == obj) {
      return true; 
    }
    if (obj == null) {
      return false; 
    }
    if (getClass() != obj.getClass()) {
      return false; 
    }
    Company other = (Company) obj;
    if (id != other.id) {
      return false; 
    }
    if (name == null) {
      if (other.name != null) {
        return false; 
      }
    } else if (!name.equals(other.name)) {
      return false; 
    }
    return true;
  }


}
