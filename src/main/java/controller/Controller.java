package controller;

import beans.HelloWorldService;

import dao.DaoFactory;
import dnl.utils.text.table.TextTable;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import model.Company;
import model.Computer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import service.ServiceCompany;
import service.ServiceCompanyImpl;
import service.ServiceComputer;
import service.ServiceComputerImpl;
import view.View;

/**
 * Class that implement the console interface for the Application.
 * 
 * @author mnouville
 * @version 1.0
 */
public class Controller {

  private DaoFactory daoFactory;
  private ServiceComputer serviceComputer;
  private ServiceCompany serviceCompany;
  private static final Logger LOG = LoggerFactory.getLogger(Controller.class);
  private View view;
  private Scanner sc;
  private static final String ENUM_LIST = "ADD,DELETE,UPDATE,GET,GETALL,EXIT";

  /**
   * Main of the class Controller.
   * 
   * @param args String[]
   */
  public static void main(String[] args) throws SQLException {
    // loading the definitions from the given XML file
    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
 
    HelloWorldService service = (HelloWorldService) context.getBean("helloWorldService");
    String message = service.sayHello();
    System.out.println(message);
 
    //set a new name
    service.setName("Spring");
    message = service.sayHello();
    System.out.println(message);
    ((ClassPathXmlApplicationContext) context).close();
    
    DaoFactory daoFactory = DaoFactory.getInstance();
    Controller ctrl = new Controller(daoFactory);
    ctrl.launchMenu();
  }

  /**
   * Constructor of the class View.
   * 
   * @param daoFactory DaoFactory
   */
  public Controller(DaoFactory daoFactory) {
    this.daoFactory = daoFactory;
    this.setServiceCompany(new ServiceCompanyImpl(daoFactory));
    this.setServiceComputer(new ServiceComputerImpl(daoFactory));
    this.setView(new View());
  }

  /**
   * Method that display in console the Main Menu of the application.
   * 
   */
  public void launchMenu() throws SQLException {
    this.getView().displayMenu();

    sc = new Scanner(System.in);
    switch (sc.nextLine()) {
      case "1":
        launchMenuCompany();
        break;
      case "2":
        launchMenuComputer();
        break;
      case "3":
        System.exit(0);
        break;
      default:
        this.getView().errorMenu();
        launchMenu();
    }
  }

  /**
   * Method that display in console the Company Menu.
   * 
   */
  public void launchMenuCompany() throws SQLException {
    this.getView().displayMenuCompany();
    sc = new Scanner(System.in);
    String choice = sc.nextLine().toUpperCase();

    if (ENUM_LIST.contains(choice) && !choice.equals("")) {
      switch (Feature.valueOf(choice)) {
        case GETALL:
          List<Company> companies = this.serviceCompany.getCompanies();
          Object[][] data = new Object[companies.size()][5];
          for (int i = 0; i < companies.size(); i++) {
            data[i][0] = companies.get(i).getId();
            data[i][1] = companies.get(i).getName();
          }
          TextTable table = new TextTable(new String[] { "ID", "Name" }, data);
          this.getView().printTableDatabase(table);
          launchMenuCompany();
          break;
        case DELETE:
          deleteCompany();
          break;
        case EXIT:
          launchMenu();
          break;
        default:
          this.getView().errorCommand();
          launchMenuCompany();
      }
    } else {
      this.getView().errorCommand();
      launchMenuCompany();
    }
  }
  
  /**
   * Method that display in console the delete menu for companies.
   */
  public void deleteCompany() throws SQLException {
    System.out.print("Enter Company ID : ");
    Scanner sc = new Scanner(System.in);
    int companyid = Integer.parseInt(sc.nextLine());
    this.serviceCompany.deleteCompany(companyid);
    launchMenuCompany();
    sc.close();
  }

  /**
   * Method that display in console the Computer Menu.
   * 
   */
  public void launchMenuComputer() throws SQLException {
    this.getView().displayMenuComputer();
    sc = new Scanner(System.in);
    String choice = sc.nextLine().toUpperCase();

    if (ENUM_LIST.contains(choice) && !choice.equals("")) {
      switch (Feature.valueOf(choice)) {
        case ADD:
          addComputer();
          break;
        case DELETE:
          deleteComputer();
          break;
        case UPDATE:
          updateComputer();
          break;
        case GET:
          getComputer();
          break;
        case GETALL:
          getComputers();
          break;
        case EXIT:
          launchMenu();
          break;
        default:
          this.getView().errorCommand();
          launchMenuComputer();
      }
    } else {
      this.getView().errorCommand();
      launchMenuComputer();
    }
  }

  /**
   * Method that display in console menu for the addition of a new Computer Menu.
   * 
   */
  public void addComputer() throws SQLException {
    System.out.print("Enter Name : ");
    Scanner sc = new Scanner(System.in);
    String name = sc.nextLine();
    System.out.print("\nIntroduced : ");
    Timestamp ts1 = null;
    SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // Check if the value is a valid timestamp
    try {
      String introduced = sc.nextLine();
      if (!introduced.equals("")) {
        format.parse(introduced);
        ts1 = java.sql.Timestamp.valueOf(introduced);
      }
    } catch (ParseException e) {
      this.getView().errorFormatTimestamp();
      updateComputer();
    }

    System.out.print("\nDiscontinued : ");
    Timestamp ts2 = null;
    // Check if the value is a valid timestamp
    try {
      String discontinued = sc.nextLine();
      if (!discontinued.equals("")) {
        format.parse(discontinued);
        ts2 = java.sql.Timestamp.valueOf(discontinued);
      }
    } catch (ParseException e) {
      this.getView().errorFormatTimestamp();
      updateComputer();
    }

    System.out.print("\nCompany ID : ");

    // Check is the value is an integer
    try {
      int companyid = Integer.parseInt(sc.nextLine());
      // check if the id is a valid company that exist in the table company
      if (this.serviceCompany.companyExist(companyid)) {
        Computer c = new Computer(this.serviceComputer.getMaxId(), name, ts1, ts2,
            this.daoFactory.getCompanyDao().getCompany(companyid));
        // check if all informations are valid
        if (c.validComputer()) {
          LOG.info("Computer Add Request : " + c.toString());
          this.serviceComputer.addComputer(c);
          this.getView().computerAdded();
          launchMenuComputer();
        } else {
          LOG.error("Invalid Datas : " + c.toString());
          this.getView().invalidDatas();
          addComputer();
        }
      } else {
        LOG.error("The following Company Does not exist : " + companyid);
        this.getView().companyDoesNotExist();
        addComputer();
      }
    } catch (NumberFormatException nfe) {
      this.getView().errorDigit();
      addComputer();
    }
    sc.close();
  }

  /**
   * Method that display the suppresion of a Computer Menu.
   * 
   */
  public void deleteComputer() throws SQLException {
    System.out.print("Enter Computer ID : ");
    Scanner sc = new Scanner(System.in);
    try {
      int i = Integer.parseInt(sc.nextLine());
      LOG.info("Request delete Computer : " + i);
      this.serviceComputer.deleteComputer(i);
      this.getView().computerDeleted();
      launchMenuComputer();
    } catch (NumberFormatException nfe) {
      this.getView().errorDigit();
      launchMenuComputer();
    }
    sc.close();
  }

  /**
   * Method that display the update of computer Menu.
   * 
   */
  public void updateComputer() throws SQLException {
    System.out.print("Select a computer ID : ");
    Scanner sc = new Scanner(System.in);
    // check if the value is an integer
    try {
      int i = Integer.parseInt(sc.nextLine());

      System.out.print("\nEnter name : ");
      String name = sc.nextLine();

      System.out.print("\nEnter introduced : ");
      Timestamp ts1 = null;

      SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      // check if the value is a valid timestamp
      try {
        String introduced = sc.nextLine();
        if (!introduced.equals("")) {
          format.parse(introduced);
          ts1 = java.sql.Timestamp.valueOf(introduced);
        }
      } catch (ParseException e) {
        this.getView().errorFormatTimestamp();
        updateComputer();
      }

      System.out.print("\nEnter discontinued : ");
      Timestamp ts2 = null;
      // check if the value is a valid timestamp
      try {
        String discontinued = sc.nextLine();
        if (!discontinued.equals("")) {
          format.parse(discontinued);
          ts2 = java.sql.Timestamp.valueOf(discontinued);
        }
      } catch (ParseException e) {
        this.getView().errorFormatTimestamp();
        updateComputer();
      }

      System.out.print("\nEnter Company_ID : ");
      // check is the value is a valid integer
      try {
        int companyid = Integer.parseInt(sc.nextLine());
        // check is the value is a valid company id that exist in the table company
        if (this.serviceCompany.companyExist(companyid)) {
          Computer c = new Computer(i, name, ts1, ts2,
              this.daoFactory.getCompanyDao().getCompany(companyid));
          // check if all informations are valid
          if (c.validComputer()) {
            LOG.info("Request update Computer : " + c.getId() + " with datas : " + c.toString());
            this.serviceComputer.updateComputer(c);
            this.getView().computerUpdated();
            launchMenuComputer();
          } else {
            LOG.error("Invalid Datas : " + c.toString());
            this.getView().invalidDatas();
            updateComputer();
          }
        } else {
          LOG.error("The following Company Does not exist : " + companyid);
          this.getView().companyDoesNotExist();
          updateComputer();
        }
      } catch (NumberFormatException nfe) {
        this.getView().errorDigit();
        updateComputer();
      }
    } catch (NumberFormatException nfe) {
      this.getView().errorDigit();
      updateComputer();
    }
    sc.close();
  }

  /**
   * Method that display a unique Computer by Id.
   * 
   */
  public void getComputer() throws SQLException {
    System.out.print("Enter computer ID : ");
    Scanner sc = new Scanner(System.in);
    try {
      int id = Integer.parseInt(sc.nextLine());
      LOG.info("Request computer : " + id);
      Computer c = this.serviceComputer.getComputer(id);
      this.getView().displayComputer(c);
      launchMenuComputer();
    } catch (NumberFormatException nfe) {
      this.getView().errorDigit();
      getComputer();
    }
    sc.close();
  }

  /**
   * Method that display every computers in the console.
   * 
   */
  public void getComputers() throws SQLException {
    LOG.info("Request Computers List");
    List<Computer> computers = this.serviceComputer.getComputers();
    Object[][] data = new Object[computers.size()][5];
    for (int i = 0; i < computers.size(); i++) {
      data[i][0] = computers.get(i).getId();
      data[i][1] = computers.get(i).getName();
      data[i][2] = computers.get(i).getIntroduced();
      data[i][3] = computers.get(i).getDiscontinued();
      data[i][4] = computers.get(i).getCompany().getId();
    }
    TextTable table = new TextTable(
        new String[] { "ID", "Name", "Introduced", "Discontinued", "Company_Id" }, data);
    this.getView().printTableDatabase(table);

    launchMenuComputer();
  }

  /**
   * Getter of the class Controller, return the DaoFactory Attribute.
   * 
   * @return Object DaoFactory
   */
  public DaoFactory getDaoFactory() {
    return daoFactory;
  }

  /**
   * Setter of the class Controller, set manually the attribute DaoFactory.
   * 
   * @param daoFactory DaoFactory Object
   */
  public void setDaoFactory(DaoFactory daoFactory) {
    this.daoFactory = daoFactory;
  }

  /**
   * Setter of the class Controller, set manually the attribute DaoFactory.
   * 
   * @return Object View
   */
  public View getView() {
    return view;
  }

  /**
   * Setter of the class Controller, set manually the attribute DaoFactory.
   * 
   * @param view View Object
   */
  public void setView(View view) {
    this.view = view;
  }

  /**
   * Getter of the class Controller, get the ServiceComputer.
   * 
   * @return Object ServiceComputer
   */
  public ServiceComputer getServiceComputer() {
    return serviceComputer;
  }

  /**
   * Setter of the class Controller, set manually the ServiceComputer.
   * 
   * @param serviceComputer Object
   */
  public void setServiceComputer(ServiceComputer serviceComputer) {
    this.serviceComputer = serviceComputer;
  }

  /**
   * Getter of the class Controller, get the ServiceCompany.
   * 
   * @return Object ServiceCompany
   */
  public ServiceCompany getServiceCompany() {
    return serviceCompany;
  }

  /**
   * Setter of the class Controller, set manually the ServiceCompany.
   * 
   * @param serviceCompany Object
   */
  public void setServiceCompany(ServiceCompany serviceCompany) {
    this.serviceCompany = serviceCompany;
  }
}
