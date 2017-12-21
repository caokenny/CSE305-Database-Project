package Person;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Database.Database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Collection;

@WebServlet("/AddPerson")
public class AddPerson extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("FirstName");
        String lastName = request.getParameter("LastName");
        String userName = request.getParameter("Username");
        String password = request.getParameter("Password");
        String address = request.getParameter("Address");
        String email = request.getParameter("Email");
        String state = request.getParameter("State");
        String city = request.getParameter("City");
        String zipcode = request.getParameter("ZipCode");
        String phone = request.getParameter("Telephone");
        String cc = request.getParameter("CreditCardNum");

        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);

        try {
            String addPerson = "INSERT INTO Person(FirstName, LastName, Address, City, State, ZipCode, Phone) VALUES" +
                    "('"+firstName+"', '"+lastName+"', '"+address+"', '"+city+"', '"+state+"', '"+zipcode+"', '"+phone+"')";
            try {
                Database.executeU(addPerson);

                addPerson = "SELECT Id FROM Person WHERE FirstName='" + firstName + "' AND LastName='" + lastName +
                        "' AND Address='" + address + "' AND City='" + city + "' AND State='" + state + "' AND ZipCode='" +
                        zipcode + "' AND Phone='" + phone + "'";

                ResultSet resultSet = Database.executeQ(addPerson);

                resultSet.next();

                String id = resultSet.getString("Id");

                addPerson = "INSERT INTO Customer(Id, CreditCardNo, Email, CreationDate, Rating, Username, Password) VALUES" +
                        "('"+id+"', '"+cc+"', '"+email+"', NOW(), 5, '"+userName+"', '"+password+"');";

                Database.executeU(addPerson);

                ResultSet getAccountNo = Database.executeQ("SELECT AccountNo FROM Customer WHERE" +
                        " Id='" + id + "'");

                getAccountNo.next();

                String accountNo = getAccountNo.getString(1);

                String addPassenger = "INSERT INTO Passenger VALUES('" + id + "', '" + accountNo + "')";

                Database.executeU(addPassenger);

                request.getSession().setAttribute("loggedInUser", userName);
                request.getSession().setAttribute("log", "Welcome " + firstName + " " + lastName);
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
