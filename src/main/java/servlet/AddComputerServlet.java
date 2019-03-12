package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import dto.Dto;
import exceptions.ValidationException;
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
    Company comp;
    int companyid = Integer.parseInt(request.getParameter("companyid"));
    try {
      validator.verifyValidCompanyId(companyid);
      comp = this.serviceCompany.getCompany(Integer.parseInt(request.getParameter("companyid")));
      Dto dto = new Dto(this.serviceComputer.getMaxId()+"",
                        request.getParameter("name"), 
                        request.getParameter("introduced"), 
                        request.getParameter("discontinued"), 
                        comp.getId()+"", comp.getName() );
      
      Computer computer = mapper.dtoToComputer(dto);
      this.validator.verifyComputerNotNull(computer);
      this.validator.verifyIdNotNull(computer.getId());
      this.validator.verifyName(computer.getName());
      this.validator.verifyIntroBeforeDisco(computer);
      this.serviceComputer.addComputer(computer);
      int max = this.serviceComputer.getCount();
      request.setAttribute("maxcomputer", max);
      request.setAttribute("computers", this.mapper.computersToDtos(
                                        this.serviceComputer.getComputers()));
      
      this.getServletContext().getRequestDispatcher("/views/Dashboard.jsp").forward(request,
          response);
    } catch (ValidationException e) {
      request.setAttribute("error", e.getMessage());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
