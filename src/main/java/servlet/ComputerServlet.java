package servlet;

import dto.Dto;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import mappers.MapperDto;
import service.ServiceComputer;

/**
 * Servlet implementation class ComputerServlet.
 */
@Controller
@RequestMapping("/")
public class ComputerServlet {

  @Autowired
  private ServiceComputer serviceComputer;
  
  @Autowired
  private MapperDto mapper;
  
  
  @GetMapping
  public ModelAndView doGet(@RequestParam(value = "page", defaultValue = "1") String pageNumber,
                            ModelAndView modelView) throws SQLException {
      int totalComputer = serviceComputer.getCount();
      int page = Integer.parseInt(pageNumber);
      List<Dto> computers = this.mapper.computersToDtos(this.serviceComputer.getComputers((page - 1) * 50));
      modelView.addObject("computers", computers);
      modelView.addObject("maxcomputer", totalComputer);
      modelView.setViewName("Dashboard");
      return modelView;
  }
  
  @RequestMapping(value = "/Sort")
  public ModelAndView sortByName(@RequestParam(value = "page", defaultValue = "1") String pageNumber,
                                 @RequestParam(value = "type", defaultValue = "ASC") String type,
                                 @RequestParam(value = "sort", defaultValue = "name") String sort,
                                 ModelAndView modelView) throws SQLException {
      int totalComputer = serviceComputer.getCount();
      int page = Integer.parseInt(pageNumber);
      int offset = (page - 1) * 50;
      List<Dto> computers = this.mapper.computersToDtos(this.serviceComputer.sortByColumn(type, offset,sort));
      modelView.addObject("computers", computers);
      modelView.addObject("maxcomputer", totalComputer);
      if (type.equals("ASC")) {
        modelView.addObject("type", "DESC");
      } else {
        modelView.addObject("type", "ASC");
      }
      modelView.setViewName("Dashboard");
      return modelView;
  }
  
  @RequestMapping(value = "/Search")
  public ModelAndView search(WebRequest request, ModelAndView modelView) throws SQLException {
      int totalComputer = serviceComputer.getCount();
      List<Dto> computers = this.mapper.computersToDtos(this.serviceComputer.searchName(request.getParameter("search")));
      modelView.addObject("computers", computers);
      modelView.addObject("maxcomputer", totalComputer);
      modelView.setViewName("Dashboard");
      return modelView;
  }
  
  @RequestMapping(value = "/Delete")
  public ModelAndView delete(WebRequest request, ModelAndView modelView) throws SQLException {
      int totalComputer = serviceComputer.getCount();
      List<Dto> computers = this.mapper.computersToDtos(this.serviceComputer.searchName(request.getParameter("search")));
      modelView.addObject("computers", computers);
      modelView.addObject("maxcomputer", totalComputer);
      modelView.setViewName("Dashboard");
      return modelView;
  }
}
