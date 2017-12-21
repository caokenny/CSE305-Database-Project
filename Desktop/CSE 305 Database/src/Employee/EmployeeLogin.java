package Employee;

import Database.Database;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

@SuppressWarnings("Duplicates")
@WebServlet("/EmployeeLogin")
public class EmployeeLogin extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //Get username and password from HTML input form
        String username = request.getParameter("Username");
        String password = request.getParameter("Password");

        //Username is case insensitive
        username = username.toLowerCase();
        try {
            //Search Customer table for the username they entered
            String getUser = "SELECT Username, Password, Id FROM Employee WHERE Username='" + username + "'";
            ResultSet resultSet = Database.executeQ(getUser);

            //If the Result Set didn't return null
            if (resultSet.next()) {
                //Get the username that was found
                String resultUsername = resultSet.getString("Username");
                //Case insensitive username
                resultUsername = resultUsername.toLowerCase();
                //Get the password that was associated with the username
                String resultPassword = resultSet.getString("Password");
                //Get their ID
                String userID = resultSet.getString("Id");
                //If the username returned from the table matches the username they entered AND the password
                //from the table matches the password they entered
                if (resultUsername.equals(username) && resultPassword.equals(password)) {
                    //Set "loggedInUser" attribute to their username so we can access it throughout the lifetime of their login
                    request.getSession().setAttribute("loggedInEmployee", username);
                    //Search Person table to get their First and Last name associated with their ID
                    resultSet = Database.executeQ("SELECT FirstName, LastName FROM Person WHERE Id=" +
                            "'"+userID+"'");
                    resultSet.next();
                    //Set "log" attribute to print Welcome FirstName LastName
                    request.getSession().setAttribute("log", "Welcome " + resultSet.getString("FirstName") + " " + resultSet.getString("LastName"));
                    RequestDispatcher dispatcher = request.getRequestDispatcher("EmployeeIndex.jsp");
                    dispatcher.forward(request, response);
                } else {
                    //If password didn't match tell them wrong username/password
                    request.getSession().setAttribute("log", "Wrong Username/Password");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                    dispatcher.forward(request, response);
                }
            } else {
                //If the Result Set returned empty tell them their username wasn't found
                request.getSession().setAttribute("log", "Username was not found, please register first.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        //Send them back to the home page
        RequestDispatcher dispatcher = request.getRequestDispatcher("EmployeeIndex.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
