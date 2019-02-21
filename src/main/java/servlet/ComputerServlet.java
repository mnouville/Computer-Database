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
 * Servlet implementation class ComputerServlet
 */
@WebServlet(name = "ComputerServlet", urlPatterns = {"/ComputerServlet"})
public class ComputerServlet extends HttpServlet {
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
		try {
			int max = computerDao.getCount();
			request.setAttribute("maxcomputer", max);
			List<Computer> computers;
			if ( request.getParameter("page") == null || request.getParameter("page") == "1" ) {
				computers = computerDao.getComputers();	
			}
			else {
				int page = Integer.parseInt(request.getParameter("page"));
				computers = computerDao.getComputers((page-1)*50);
			}
            request.setAttribute("computers",computers);
        } catch (SQLException ex) {
            //Logger.getLogger(UtilisateursServlet.class.getName()).log(Level.SEVERE, null, ex);
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
