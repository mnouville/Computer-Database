package servlet;

import dto.Dto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mappers.MapperDto;
import model.Computer;
import service.ServiceComputer;
import service.ServiceComputerImpl;

/**
 * Servlet implementation class ComputerServlet.
 */
@WebServlet(name = "ComputerServlet", urlPatterns = { "/ComputerServlet" })
public class ComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private ServiceComputer serviceComputer;
  private MapperDto mapper;

  @Override
  public void init() throws ServletException {
    this.serviceComputer = ServiceComputerImpl.getInstance();
    this.mapper = new MapperDto();
  }

  /**
   * Method doGet of ComputerServlet.
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      int max = this.serviceComputer.getCount();
      request.setAttribute("maxcomputer", max);
      List<Computer> computers;
      List<Dto> dtos;
      if (request.getParameter("page") == null || request.getParameter("page") == "1") {
        computers = this.serviceComputer.getComputers();
        dtos = this.mapper.computersToDtos(computers);
      } else {
        int page = Integer.parseInt(request.getParameter("page"));
        computers = this.serviceComputer.getComputers((page - 1) * 50);
        dtos = this.mapper.computersToDtos(computers);
      }
      request.setAttribute("computers", dtos);
    } catch (SQLException ex) {
      // Logger.getLogger(UtilisateursServlet.class.getName()).log(Level.SEVERE, null, ex);
    }

    this.getServletContext().getRequestDispatcher("/views/Dashboard.jsp").forward(request,
        response);
  }

  /**
   * Method doPost of ComputerServlet.
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    doGet(request, response);
  }

}
