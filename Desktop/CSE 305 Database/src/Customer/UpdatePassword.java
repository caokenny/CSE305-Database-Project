package Customer;

import Database.Database;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

@WebServlet("/UpdatePassword")
public class UpdatePassword extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String newPassword = request.getParameter("NewPassword");
        String confirmPassword = request.getParameter("ConfirmPassword");

        String query = "SELECT Password FROM Customer WHERE Username='" + request.getSession().getAttribute("loggedInUser") + "' AND " +
                "Password='" + request.getParameter("OldPassword") + "'";

        ResultSet resultSet = Database.executeQ(query);

        if (resultSet.next()) {
            resultSet.beforeFirst();
            resultSet.next();
            if (!newPassword.equals(confirmPassword)) {
                request.getSession().setAttribute("pwUpdateLog", "Passwords do not match");
            } else {
                query = "UPDATE Customer " +
                        "SET Password = '" + newPassword + "' " +
                        "WHERE Username='" + request.getSession().getAttribute("loggedInUser") + "' " +
                        "AND Password='" + request.getParameter("OldPassword") + "'";
                Database.executeU(query);
                request.getSession().setAttribute("pwUpdateLog", "Password Update Success!");
            }

        } else {
            request.getSession().setAttribute("pwUpdateLog", "Old password incorrect");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("ViewUpdatePersonalInfo");
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
