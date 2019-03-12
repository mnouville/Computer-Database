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
import model.Computer;
import service.ServiceComputer;

/**
 * Servlet implementation class SortByName.
 */
@WebServlet(name = "SortByIntro", urlPatterns = { "/SortByIntro" })
public class SortByIntro extends HttpServlet {
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
      computers = this.serviceComputer.sortByColumn(request.getParameter("type"), offset,"introduced");
      dtos = this.mapper.computersToDtos(computers);
      
      
      request.setAttribute("sort", "intro");
      request.setAttribute("computers", dtos);
    } catch (SQLException ex) {
      // Logger.getLogger(UtilisateursServlet.class.getName()).log(Level.SEVERE, null, ex);
    }

    if (request.getParameter("type").equals("ASC")) {
      request.setAttribute("type", "DESC");
    } else {
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
