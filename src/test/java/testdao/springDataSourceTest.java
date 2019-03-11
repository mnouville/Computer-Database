package testdao;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.zaxxer.hikari.HikariDataSource;

import dao.CompanyDaoImpl;
import springconfig.SpringConfig;

public class springDataSourceTest {

  @Test
  public void test() {
    ApplicationContext vApplicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);

    HikariDataSource source = vApplicationContext.getBean("dataSource", HikariDataSource.class);
    
    assertNotNull(source);
  }

}
