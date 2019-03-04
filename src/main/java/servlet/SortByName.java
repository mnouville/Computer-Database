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
import model.Computer;
import service.ServiceComputer;
import service.ServiceComputerImpl;

/**
 * Servlet implementation class SortByName.
 */
public class SortByName extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private ServiceComputer serviceComputer;
  private MapperDto mapper;

  @Override
  public void init() throws ServletException {
    this.serviceComputer = ServiceComputerImpl.getInstance();
    this.mapper = new MapperDto();
  }

  /**
   * Method doGet of SortByName.
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    int page;
    if (request.getParameter("page").equals("")) {
      page = 1;
    } else {
      page = Integer.parseInt(request.getParameter("page"));
    }
    
    try {
      int max = this.serviceComputer.getCount();
      int offset = (page - 1) * 50;
      request.setAttribute("maxcomputer", max);
      List<Computer> computers;
      List<Dto> dtos;
      computers = this.serviceComputer.sortByName(request.getParameter("type"), offset);
      dtos = this.mapper.computersToDtos(computers);
      
      
      request.setAttribute("sort", "name");
      request.setAttribute("computers", dtos);
    } catch (SQLException ex) {
      // Logger.getLogger(UtilisateursServlet.class.getName()).log(Level.SEVERE, null, ex);
    }

    if (request.getParameter("type").equals("ASC")) {
      System.out.println("RETURN DESC");
      request.setAttribute("type", "DESC");
    } else {
      System.out.println("RETURN ASC");
      request.setAttribute("type", "ASC");
    }
    
    this.getServletContext().getRequestDispatcher("/views/Dashboard.jsp").forward(request,
        response);
    
  }

  /**
   * Method doPost of SortByName.
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    System.out.println(request.getParameter("page"));
  }

}
