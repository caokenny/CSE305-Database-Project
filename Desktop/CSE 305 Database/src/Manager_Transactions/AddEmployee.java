package Manager_Transactions;

import Database.Database;
import Header.Header;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet(urlPatterns = "/AddEmployee")
public class AddEmployee extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{

        try {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println(Header.getManagerHeader());
            String firstName = request.getParameter("FirstName");
            String lastName = request.getParameter("LastName");
            String userName = request.getParameter("Username");
            String password = request.getParameter("Password");
            String email = request.getParameter("Email");
            String address = request.getParameter("Address");
            String state = request.getParameter("State");
            String city = request.getParameter("City");
            int SSN = Integer.parseInt(request.getParameter("SSN"));
            int zipCode = Integer.parseInt(request.getParameter("ZipCode"));
            double rate = Double.parseDouble(request.getParameter("rate"));
            String telephone = request.getParameter("Telephone");

            String q = "INSERT INTO Person(FirstName, LastName, Address, City, State, ZipCode, Phone) VALUES" +
                    "('"+firstName+"', '"+lastName+"', '"+address+"', '"+city+"', '"+state+"', '"+zipCode+"', '"+telephone+"')";

            Database.executeU(q);

            q = "SELECT Id FROM Person WHERE FirstName='" + firstName + "' AND LastName='" + lastName +
                    "' AND Address='" + address + "' AND City='" + city + "' AND State='" + state + "' AND ZipCode='" +
                    zipCode + "' AND Phone='" + telephone + "'";

            ResultSet resultSet = Database.executeQ(q);

            resultSet.next();

            String id = resultSet.getString("Id");

            String employeeAdd ="INSERT INTO Employee() VALUES(" +id+","+SSN+","+"0,"+
                    "CURDATE(),"+ rate+",\""+userName+"\",\""+password+"\")";
            Database.executeU(employeeAdd);
            out.println("<h3>Employee Added!</h3>");
            HomePage.HomePage(out);
            out.println(Header.getFooter());

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
}
