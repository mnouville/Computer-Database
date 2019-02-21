package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CompanyDao;
import dao.ComputerDao;
import dao.DaoFactory;
import model.Company;
import model.Computer;

/**
 * Servlet implementation class EditComputerServlet
 */
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ComputerDao computerDao;
	private CompanyDao companyDao;

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
		int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("idcomputer",id);

		try {
			companyDao = DaoFactory.getInstance().getCompanyDao();
			List<Company> companies = companyDao.getCompanies();
			
			request.setAttribute("companies", companies);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        this.getServletContext().getRequestDispatcher("/views/EditComputer.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		computerDao = DaoFactory.getInstance().getComputerDao();
		companyDao = DaoFactory.getInstance().getCompanyDao();
		Computer c = new Computer();
		
        try {
        	System.out.println(request.getParameter("id"));
			c.setId(Integer.parseInt(request.getParameter("id")));
			c.setName(request.getParameter("name"));
	        
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
	        Date introduced = formatter.parse(request.getParameter("introduced"));
	        c.setIntroduced(introduced);
	        Date discontinued = formatter.parse(request.getParameter("discontinued"));
	        c.setDiscontinued(discontinued);
	        
	        Company comp = companyDao.getCompany(Integer.parseInt(request.getParameter("companyid")));
	        
	        c.setCompany(comp);
	        
	        computerDao.updateComputer(c);
	        int max = computerDao.getCount();
			request.setAttribute("maxcomputer", max);
            request.setAttribute("computers",computerDao.getComputers());
            this.getServletContext().getRequestDispatcher("/views/Dashboard.jsp").forward(request,response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
