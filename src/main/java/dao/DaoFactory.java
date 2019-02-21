package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class instantiante the connection with the database
 * @author mnouville
 * @version 1.0
 */
public class DaoFactory {
    private String url;
    private String username;
    private String password;
    //Singleton
    private static DaoFactory single_instance = null; 
    
    /**
     * Constructor of DaoFactory
     * @param url of the database
     * @param username 
     * @param password
     */
    DaoFactory(String url, String username, String password)
    {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
    /**
     * Return a unique database instance
     * @return an Object DaoFactory
     */
    public static DaoFactory getInstance() 
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
        } 
        catch (ClassNotFoundException e) 
        {
            System.out.println(e.toString());
        }
        //Singleton
        if (single_instance == null) 
            single_instance = new DaoFactory("jdbc:mysql://localhost:3306/computer-database-db?autoReconnect=true&useSSL=false","admincdb","qwerty1234"); 
        return single_instance;
    }
    
    /**
     * This method launch the connection to the database
     * @return Object Connection
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }
    
    /**
     * This method return a CompanyDao
     * @return Object CompanyDao
     */
    public CompanyDao getCompanyDao() {
        return new CompanyDaoImpl(this);
    }
    
    /**
     * This method return a ComputerDao
     * @return Object ComputerDao
     */
    public ComputerDao getComputerDao() {
        return new ComputerDaoImpl(this);
    }
}