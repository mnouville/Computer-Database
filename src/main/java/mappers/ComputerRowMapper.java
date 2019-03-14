package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import model.Computer;
import service.ServiceCompany;

@Component
public class ComputerRowMapper
{
  @Autowired
  private ServiceCompany serviceCompany;
  
  public RowMapper<Computer> getRowMapperComputer() {
    return new RowMapper<Computer>() {
      public Computer mapRow(ResultSet result, int pRowNum) throws SQLException {
        Computer computer = new Computer();
        computer.setId(result.getInt("id"));
        computer.setName(result.getString("name"));
        if (result.getDate("introduced") != null) {
          computer.setIntroduced(result.getDate("introduced"));
        }
        if (result.getDate("discontinued") != null) {
          computer.setDiscontinued(result.getDate("discontinued"));
        }
        computer.setCompany(serviceCompany.getCompany(result.getInt("company_id")));
        return computer;
      }
    };
  }
}