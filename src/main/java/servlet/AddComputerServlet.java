package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Company;
import model.Computer;
import service.ServiceCompany;
import service.ServiceCompanyImpl;
import service.ServiceComputer;
import service.ServiceComputerImpl;

/**
 * Servlet implementation class AddComputerServlet.
 */
@WebServlet(name = "AddComputerServlet", urlPatterns = { "/AddComputerServlet" })
public class AddComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private ServiceComputer serviceComputer;
  private ServiceCompany serviceCompany;

  @Override
  public void init() throws ServletException {
    this.serviceCompany = ServiceCompanyImpl.getInstance();
    this.serviceComputer = ServiceComputerImpl.getInstance();
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
    Computer c = new Computer();

    try {
      c.setId(this.serviceComputer.getMaxId());
      c.setName(request.getParameter("name"));
      
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
      Date introduced = formatter.parse(request.getParameter("introduced"));
      c.setIntroduced(introduced);
      Date discontinued = formatter.parse(request.getParameter("discontinued"));
      c.setDiscontinued(discontinued);

      Company comp = this.serviceCompany
          .getCompany(Integer.parseInt(request.getParameter("companyid")));

      c.setCompany(comp);

      this.serviceComputer.addComputer(c);
      int max = this.serviceComputer.getCount();
      request.setAttribute("maxcomputer", max);
      request.setAttribute("computers", this.serviceComputer.getComputers());
      this.getServletContext().getRequestDispatcher("/views/Dashboard.jsp").forward(request,
          response);
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }

  }

}
