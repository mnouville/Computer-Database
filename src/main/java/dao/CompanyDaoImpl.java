package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.Company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.zaxxer.hikari.HikariDataSource;

import mappers.CompanyRowMapper;

/**
 * Class that contains every method concerning Company in the Database.
 * 
 * @author mnouville
 * @version 1.0
 */
@Repository
public class CompanyDaoImpl implements CompanyDao {

  private static final Logger LOG = LoggerFactory.getLogger(CompanyDaoImpl.class);
  private final String insert = "INSERT INTO company(id,name) VALUES (?,?);";
  private final String getall = "SELECT id,name FROM company;";
  private final String get = "SELECT id,name FROM company where id = :id";
  private final String count = "SELECT COUNT(*) from company where id = ";
  private final String delete = "DELETE FROM company WHERE id = :id";
  private static final String deletecomputer = "DELETE FROM computer WHERE company_id = :id";
  
  private HikariDataSource dataSource;
  private CompanyRowMapper companyRawMapper;
  private JdbcTemplate jdbcTemplate;
  private NamedParameterJdbcTemplate njdbcTemplate;
  
  /**
   * Method that return the connection of Hikari
   * @return the connection to the database
   */
  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }
  /**
   * Constructor of CampanyDaoImpl.
   * 
   * @param daoFactory DaoFactory
   */
  CompanyDaoImpl(HikariDataSource dataSource, CompanyRowMapper companyRawMapper ) { 
    this.dataSource = dataSource;
    this.companyRawMapper = companyRawMapper;
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.njdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
  }

  /**
   * This method take a Company in parameter and add it into the Database.
   * 
   * @param c Company
   */
  @Override
  public void addCompany(Company c) throws SQLException {
    this.jdbcTemplate.update(insert,c.getId(),c.getName());
  }

  /**
   * This method return a list of every companies in the Database.
   * 
   * @return List of Object Company
   */
  @Override
  public List<Company> getCompanies() throws SQLException {
    NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    MapSqlParameterSource params = new MapSqlParameterSource();
    RowMapper<Company> rowMapper = this.companyRawMapper.getRowMapperCompany();
    List<Company> list = jdbcTemplate.query(getall, params, rowMapper);
    LOG.info("Request succesfully executed (get companies) size : " + list.size());
    return list;
  }

  /**
   * This method delete companies by ID.
   * 
   * @param id int
   */
  @Override
  public void deleteCompany(int id) throws SQLException {    
    MapSqlParameterSource parameters = new MapSqlParameterSource();
    NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    parameters.addValue("id", id);
    
    if(jdbcTemplate.update(deletecomputer, parameters) == 0) {
      LOG.info("Impossible to delete computer with the following company ID : " + id);
    }
    
    if(jdbcTemplate.update(delete, parameters) == 0) {
      LOG.info("Impossible to delete company with the following ID : " + id);
    }
    
    LOG.info("COMPANY DELETED");
  }

  /**
   * This method return the Company with this ID in the Database.
   * 
   * @param i int
   * @return Object Company
   */
  @Override
  public Company getCompany(int i) throws SQLException {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", i);
    RowMapper<Company> rowMapper = this.companyRawMapper.getRowMapperCompany();
    try {
      return njdbcTemplate.queryForObject(get, params, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      return new Company(0,"");
    } 
  }

  /**
   * This method return a true if a Company ID exist in the Database.
   * 
   * @return boolean
   */
  @Override
  public boolean companyExist(int id) throws SQLException {
    return ( jdbcTemplate.queryForObject(count + id, Integer.class) > 0);
  }
}
