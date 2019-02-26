package dto;

import dao.DaoFactory;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import model.Company;
import service.ServiceCompany;
import service.ServiceCompanyImpl;

/**
 * DTO for objects Company and Computers.
 * @author mnouville
 * @version 1.0
 */
public class CdbDto {

  private int id;
  private String name;
  private Date introduced;
  private Date discontinued;
  private Company company;
  private List<Company> listCompany;
  private ServiceCompany serviceCompany;
  
  /**
   * Constructor of CdbDto class.
   * @param id int
   * @param name String
   * @param introduced Date
   * @param discontinued Date
   * @param c Company
   */
  public CdbDto(int id, String name, Date introduced, Date discontinued, Company c) 
      throws SQLException {
    this.id = id;
    this.name = name;
    this.introduced = introduced;
    this.discontinued = discontinued;
    this.company = c;
    this.serviceCompany = new ServiceCompanyImpl(DaoFactory.getInstance());
    this.listCompany = this.serviceCompany.getCompanies();
  }

  /**
   * Getter of the class CdbDto.java
   * @return int
   */
  public int getId() {
    return id;
  }

  /**
   * Setter of the class CdbDto.java
   * @param id int
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Getter of the class CdbDto.java
   * @return String
   */
  public String getName() {
    return name;
  }

  /**
   * Setter of the class CdbDto.java
   * @param name String
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter of the class CdbDto.java
   * @return Date
   */
  public Date getIntroduced() {
    return introduced;
  }

  /**
   * Setter of the class CdbDto.java
   * @param introduced Date
   */
  public void setIntroduced(Date introduced) {
    this.introduced = introduced;
  }
  
  /**
   * Getter of the class CdbDto.java
   * @return Date
   */
  public Date getDiscontinued() {
    return discontinued;
  }

  /**
   * Setter of the class CdbDto.java
   * @param discontinued Date
   */
  public void setDiscontinued(Date discontinued) {
    this.discontinued = discontinued;
  }

  /**
   * Getter of the class CdbDto.java
   * @return Company
   */
  public Company getCompany() {
    return company;
  }

  /**
   * Setter of the class CdbDto.java
   * @param company Company
   */
  public void setCompany(Company company) {
    this.company = company;
  }

  /**
   * Getter of the class CdbDto.java
   * @return List of Company
   */
  public List<Company> getListCompany() {
    return listCompany;
  }

  /**
   * Setter of the class CdbDto.java
   * @param listCompany List
   */
  public void setListCompany(List<Company> listCompany) {
    this.listCompany = listCompany;
  }

  /**
   * Getter of the class CdbDto.java
   * @return ServiceCompany
   */
  public ServiceCompany getServiceCompany() {
    return serviceCompany;
  }

  /**
   * Setter of the class CdbDto.java
   * @param serviceCompany ServiceCompany
   */
  public void setServiceCompany(ServiceCompany serviceCompany) {
    this.serviceCompany = serviceCompany;
  }
  
}
