package springconfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({"dao","service","servlet","mappers","validator","controller"})
@PropertySource(value = { "classpath:hikariconfig.properties" })
@EnableWebMvc
public class SpringConfig implements WebApplicationInitializer {

    @Autowired
    private Environment environement;
  
    @Bean
    public HikariDataSource dataSource() {
      HikariDataSource dataSource = new HikariDataSource();
      dataSource.setJdbcUrl(environement.getRequiredProperty("URL"));
      dataSource.setUsername(environement.getRequiredProperty("USERNAME"));
      dataSource.setPassword(environement.getRequiredProperty("PASSWORD"));
      dataSource.setDriverClassName(environement.getRequiredProperty("DRIVER"));
      return dataSource;
    }

    @Override
    public void onStartup(ServletContext container) throws ServletException {
      // Create the 'root' Spring application context
      AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
      rootContext.register(SpringConfig.class);

      // Manage the life cycle of the root application context
      container.addListener(new ContextLoaderListener(rootContext));
    }
}
