package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import mappers.MapperDto;
import model.Company;
import model.Computer;
import service.ServiceCompany;
import service.ServiceComputer;
import validator.Validator;

/**
 * Servlet implementation class AddComputerServlet.
 */
@WebServlet(name = "AddComputerServlet", urlPatterns = { "/AddComputerServlet" })
public class AddComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Autowired
  private ServiceComputer serviceComputer;
  
  @Autowired
  private ServiceCompany serviceCompany;
  
  @Autowired
  private Validator validator;
  
  @Autowired
  private MapperDto mapper;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
  }

  /**
   * Method doGet of AddComputerServlet.
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      List<Company> companies;
      companies = this.serviceCompany.getCompanies();
      request.setAttribute("companies", companies);
    } catch (SQLException ex) {
      // Logger.getLogger(UtilisateursServlet.class.getName()).log(Level.SEVERE, null, ex);
    }

    this.getServletContext().getRequestDispatcher("/views/AddComputer.jsp").forward(request,
        response);
  }

  /**
   * Method doPost of AddComputerServlet.
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      Computer c = new Computer();
      
      c.setId(this.serviceComputer.getMaxId());
      
      c.setName(request.getParameter("name"));
      
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
      Date introduced;
      if (this.validator.validIntroduced(request.getParameter("introduced"))) {
        introduced = formatter.parse(request.getParameter("introduced"));
      } else {
        introduced = null;
      }
      c.setIntroduced(introduced);
      
      Date discontinued;
      if (this.validator.validDiscontinued(request.getParameter("discontinued"))) {
        discontinued = formatter.parse(request.getParameter("discontinued"));
      } else {
        discontinued = null;
      }
      c.setDiscontinued(discontinued);

      Company comp;
      int companyid = Integer.parseInt(request.getParameter("companyid"));
      if (companyid != 0 && this.validator.validCompanyId(companyid)) {
        comp = this.serviceCompany.getCompany(
               Integer.parseInt(request.getParameter("companyid")));
      } else {
        comp = new Company(companyid,"");
      }
      
      c.setCompany(comp);

      if (!this.validator.validDates(c)) {
        request.setAttribute("error", "invalidtimestamp");
      } else if (!this.validator.validName(c.getName())) {
        request.setAttribute("error", "invalidname");
      } else {
        this.serviceComputer.addComputer(c);
      }
        
      
      int max = this.serviceComputer.getCount();
      request.setAttribute("maxcomputer", max);
      request.setAttribute("computers", this.mapper.computersToDtos(
                                        this.serviceComputer.getComputers()));
      this.getServletContext().getRequestDispatcher("/views/Dashboard.jsp").forward(request,
          response);
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
