package servlet;

import dao.DaoFactory;

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

import model.Company;
import model.Computer;
import service.ServiceCompany;
import service.ServiceCompanyImpl;
import service.ServiceComputer;
import service.ServiceComputerImpl;

/**
 * Servlet implementation class EditComputerServlet.
 */
public class EditComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private ServiceComputer serviceComputer;
  private ServiceCompany serviceCompany;

  @Override
  public void init() throws ServletException {
    DaoFactory daoFactory = DaoFactory.getInstance();
    this.serviceComputer = new ServiceComputerImpl(daoFactory);
    this.serviceCompany = new ServiceCompanyImpl(daoFactory);
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
      Date introduced = formatter.parse(request.getParameter("introduced"));
      c.setIntroduced(introduced);
      Date discontinued = formatter.parse(request.getParameter("discontinued"));
      c.setDiscontinued(discontinued);

      Company comp = this.serviceCompany
          .getCompany(Integer.parseInt(request.getParameter("companyid")));

      c.setCompany(comp);

      this.serviceComputer.updateComputer(c);
      int max = this.serviceComputer.getCount();
      request.setAttribute("maxcomputer", max);
      request.setAttribute("computers", this.serviceComputer.getComputers());
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
