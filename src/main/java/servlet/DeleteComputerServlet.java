package servlet;

import dto.Dto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mappers.MapperDto;
import service.ServiceComputer;
import service.ServiceComputerImpl;

/**
 * Servlet implementation class DeleteComputerServlet.
 */
public class DeleteComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private ServiceComputer serviceComputer;
  private MapperDto mapper;
  
  @Override
  public void init() throws ServletException {
    this.serviceComputer = ServiceComputerImpl.getInstance();
    this.mapper = new MapperDto();
  }

  /**
   * Method doGet of DeleteComputerServlet.
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    System.out.println(request.getParameter("selection"));
    String[] parts = request.getParameter("selection").split(",");

    try {
      for (int i = 0; i < parts.length; i++) {
        this.serviceComputer.deleteComputer(Integer.parseInt(parts[i]));
      }
      List<Dto> dtos = this.mapper.computersToDtos(this.serviceComputer.getComputers());
      int max = this.serviceComputer.getCount();
      request.setAttribute("maxcomputer", max);
      request.setAttribute("computers", dtos);
    } catch (NumberFormatException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    this.getServletContext().getRequestDispatcher("/views/Dashboard.jsp").forward(request,
        response);
  }

  /**
   * Method doPost of DeleteComputerServlet.
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    doGet(request, response);
  }

}
