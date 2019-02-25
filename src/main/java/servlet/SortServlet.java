package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Computer;
import service.ServiceComputer;
import service.ServiceComputerImpl;

/**
 * Servlet implementation class SortServlet.
 */
@WebServlet(name = "SortServlet", urlPatterns = { "/SortServlet" })
public class SortServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private ServiceComputer serviceComputer;

  @Override
  public void init() throws ServletException {
    this.serviceComputer = ServiceComputerImpl.getInstance();
  }

  /**
   * Method doGet of SortServlet.
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

  }

  /**
   * Method doPost of SortServlet.
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      String search = request.getParameter("search");
      List<Computer> computers = this.serviceComputer.sortByName(search);
      request.setAttribute("computers", computers);
      request.setAttribute("maxcomputer", computers.size());
    } catch (SQLException e) {
      e.printStackTrace();
    }

    this.getServletContext().getRequestDispatcher("/views/Dashboard.jsp").forward(request,
        response);
  }

}
