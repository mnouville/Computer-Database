package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ComputerDao;
import dao.DaoFactory;
import model.Computer;

/**
 * Servlet implementation class DeleteComputerServlet
 */
public class DeleteComputerServlet extends HttpServlet {
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
		System.out.println(request.getParameter("selection"));
		String[] parts = request.getParameter("selection").split(",");
		
		try {
			computerDao = DaoFactory.getInstance().getComputerDao();
			
			for ( int i = 0; i < parts.length; i++ ) {
				computerDao.deleteComputer(Integer.parseInt(parts[i]));
			}
			List<Computer> computers = computerDao.getComputers();
			int max = computerDao.getCount();
			request.setAttribute("maxcomputer", max);
			request.setAttribute("computers",computers);
			
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher("/views/Dashboard.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
