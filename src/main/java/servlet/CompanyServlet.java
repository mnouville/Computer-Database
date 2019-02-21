package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoFactory;
import model.Company;
import dao.CompanyDao;

/**
 * Servlet implementation class CompanyServlet
 */
@WebServlet(name = "CompanyServlet", urlPatterns = {"/CompanyServlet"})
public class CompanyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private CompanyDao companyDao;
    
    /**
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.companyDao = daoFactory.getCompanyDao();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try {
            List<Company> companies = companyDao.getCompanies();
            request.setAttribute("companies",companies);
        } catch (SQLException ex) {
            //Logger.getLogger(UtilisateursServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.getServletContext().getRequestDispatcher("/views/Company.jsp").forward(request,response);
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if ( request.getParameter("Add") != null )
        {
            Company c = new Company();
            c.setId(Integer.parseInt(request.getParameter("id")));
            c.setName(request.getParameter("name"));

            try {
                companyDao.addCompany(c);
                request.setAttribute("companies",companyDao.getCompanies());
                this.getServletContext().getRequestDispatcher("/views/Company.jsp").forward(request,response);
            } catch (SQLException ex) {
                //Logger.getLogger(UtilisateursServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if ( request.getParameter("Delete") != null )
        {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                companyDao.deleteCompany(id);
                request.setAttribute("companies",companyDao.getCompanies());
                this.getServletContext().getRequestDispatcher("/views/Company.jsp").forward(request,response);
            } catch (SQLException ex) {
                //Logger.getLogger(UtilisateursServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
