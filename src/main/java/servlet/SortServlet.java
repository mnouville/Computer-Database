package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ComputerDao;
import dao.DaoFactory;
import model.Computer;

/**
 * Servlet implementation class SortServlet
 */
@WebServlet(name = "SortServlet", urlPatterns = {"/SortServlet"})
public class SortServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ComputerDao computerDao;

	@Override
	public void init() throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.computerDao = daoFactory.getComputerDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			String search = request.getParameter("search");
			computerDao = DaoFactory.getInstance().getComputerDao();
			List<Computer> computers = computerDao.sortByName(search);
			
			request.setAttribute("computers",computers);
			request.setAttribute("maxcomputer", computers.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.getServletContext().getRequestDispatcher("/views/Dashboard.jsp").forward(request,response);
	}

}
