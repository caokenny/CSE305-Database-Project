package Customer;

import Database.Database;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateInfo")
public class UpdateInfo extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String firstName = request.getParameter("FirstName");
        String lastName = request.getParameter("LastName");

        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);

        String query = "UPDATE Person " +
                "SET FirstName = '" + firstName + "', " +
                "LastName = '" + lastName + "', " +
                "Address = '" + request.getParameter("Address") + "', " +
                "City = '" + request.getParameter("City") + "', " +
                "State = '" + request.getParameter("State") + "', " +
                "ZipCode = '" + request.getParameter("Zipcode") + "', " +
                "Phone = '" + request.getParameter("Phone") + "' " +
                "WHERE Id = '" + request.getParameter("UserID") + "'";

        Database.executeU(query);

        query = "UPDATE Customer " +
                "SET CreditCardNo = '" + request.getParameter("CreditCard") + "', " +
                "Email = '" + request.getParameter("Email") + "' " +
                "WHERE Id = '" + request.getParameter("UserID") + "'";

        Database.executeU(query);

        request.getSession().setAttribute("updateLog", "Update Success!");

        RequestDispatcher dispatcher = request.getRequestDispatcher("ViewUpdatePersonalInfo");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
