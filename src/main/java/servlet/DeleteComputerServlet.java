package servlet;

import dto.Dto;

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

import mappers.MapperDto;
import service.ServiceComputer;

/**
 * Servlet implementation class DeleteComputerServlet.
 */
@WebServlet(name = "DeleteComputerServlet", urlPatterns = { "/DeleteComputerServlet" })
public class DeleteComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Autowired
  private ServiceComputer serviceComputer;
  
  @Autowired
  private MapperDto mapper;
  
  @Override
  public void init(ServletConfig config) throws ServletException{
    super.init(config);
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
  }

  /**
   * Method doGet of DeleteComputerServlet.
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
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
