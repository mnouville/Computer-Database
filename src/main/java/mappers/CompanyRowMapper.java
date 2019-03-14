package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import model.Company;

@Component
public class CompanyRowMapper
{ 
  public RowMapper<Company> getRowMapperComputer() {
    return new RowMapper<Company>() {
      public Company mapRow(ResultSet result, int pRowNum) throws SQLException {
        Company company = new Company();
        company.setId(result.getInt("id"));
        company.setName(result.getString("name"));
        return company;
      }
    };
  }
}