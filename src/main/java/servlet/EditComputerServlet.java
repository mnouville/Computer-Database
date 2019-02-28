package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mappers.MapperDto;
import model.Company;
import model.Computer;
import service.ServiceCompany;
import service.ServiceCompanyImpl;
import service.ServiceComputer;
import service.ServiceComputerImpl;
import validator.Validator;

/**
 * Servlet implementation class EditComputerServlet.
 */
public class EditComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private ServiceComputer serviceComputer;
  private ServiceCompany serviceCompany;
  private MapperDto mapper;
  private Validator validator;

  @Override
  public void init() throws ServletException {
    this.serviceComputer = ServiceComputerImpl.getInstance();
    this.serviceCompany = ServiceCompanyImpl.getInstance();
    this.mapper = new MapperDto();
    this.validator = new Validator();
  }

  /**
   * Method doGet of EditComputerServlet.
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    int id = Integer.parseInt(request.getParameter("id"));
    request.setAttribute("idcomputer", id);
    try {
      Computer c = this.serviceComputer.getComputer(id);
      request.setAttribute("name", c.getName());
      request.setAttribute("introduced", c.getIntroduced());
      request.setAttribute("discontinued", c.getDiscontinued());
      request.setAttribute("companyid", c.getCompany().getId());
    } catch (SQLException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    try {
      List<Company> companies = this.serviceCompany.getCompanies();
      request.setAttribute("companies", companies);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    this.getServletContext().getRequestDispatcher("/views/EditComputer.jsp").forward(request,
        response);
  }

  /**
   * Method doPost of EditComputerServlet.
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Computer c = new Computer();

    try {
      System.out.println(request.getParameter("id"));
      c.setId(Integer.parseInt(request.getParameter("id")));
      c.setName(request.getParameter("name"));

      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
      if (request.getParameter("introduced").equals("")) {
        c.setIntroduced(null);
      } else {
        Date introduced = formatter.parse(request.getParameter("introduced"));
        c.setIntroduced(introduced);
      }
      
      if (request.getParameter("discontinued").equals("")) {
        c.setDiscontinued(null);
      } else {
        Date discontinued = formatter.parse(request.getParameter("discontinued"));
        c.setDiscontinued(discontinued);
      }

      
      Company comp;
      int companyid = Integer.parseInt(request.getParameter("companyid"));
      if (companyid != 0 && this.validator.validCompanyId(companyid)) {
        System.out.println("if");
        comp = this.serviceCompany.getCompany(
               Integer.parseInt(request.getParameter("companyid")));
      } else {
        System.out.println("else");
        comp = new Company(companyid,"");
      }

      c.setCompany(comp);
      
      if (!this.validator.validDates(c)) {
        request.setAttribute("error", "invaliddates");
      } else if (!this.validator.validName(c.getName())) {
        request.setAttribute("error", "invalidname");
      } else {
        this.serviceComputer.updateComputer(c);
      }

      int max = this.serviceComputer.getCount();
      request.setAttribute("maxcomputer", max);
      request.setAttribute("computers", 
                           this.mapper.computersToDtos(this.serviceComputer.getComputers()));
      this.getServletContext().getRequestDispatcher("/views/Dashboard.jsp").forward(request,
          response);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
