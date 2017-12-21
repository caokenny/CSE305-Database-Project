package Customer;

import Database.Database;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet("/ViewUpdatePersonalInfo")
public class ViewUpdatePersonalInfo extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getSession().getAttribute("updateLog") == null) request.getSession().setAttribute("updateLog", "");
        if (request.getSession().getAttribute("pwUpdateLog") == null) request.getSession().setAttribute("pwUpdateLog", "");


        PrintWriter out = response.getWriter();

        String username = (String) request.getSession().getAttribute("loggedInUser");

        String query = "SELECT * FROM Customer WHERE Username='" + username + "'";

        ResultSet resultSet = Database.executeQ(query);

        resultSet.next();

        String userID = resultSet.getString("Id");
        String accountNo = resultSet.getString("AccountNo");
        String ccNum = resultSet.getString("CreditCardNo");
        String email = resultSet.getString("Email");

        query = "SELECT * FROM Person WHERE Id='" + userID + "'";

        resultSet = Database.executeQ(query);

        resultSet.next();

        String firstName = resultSet.getString("FirstName");
        String lastName = resultSet.getString("LastName");
        String address = resultSet.getString("Address");
        String city = resultSet.getString("City");
        String state = resultSet.getString("State");
        String zipcode = resultSet.getString("ZipCode");
        String phone = resultSet.getString("Phone");

        out.println(
                "<head> \n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"/css/homeStyle.css\">" +
                "</head>"
        );

        out.println(
                "<div style=\"text-align: center; margin: 0 auto;\">\n" +
                "   <ul class=\"tab\" style=\"text-align: center; margin: 0 auto;\">\n" +
                "       <li><a href=\"index.jsp\" class=\"tablinks\">Home</a></li>\n" +
                "       <li><a href=\"javascript:void(0)\" class=\"tablinks\">User</a></li>\n" +
                "   </ul>"
        );

        String print =
                "<div class=\"loggedInTabContent\"><form action=\"UpdateInfo\" method=\"post\">" +
                        "Account Number: <input type=\"text\" name=\"AccountNo\" value=\"" + accountNo + "\" readonly/><br/><br/>" +
                        "ID Number: <input type=\"text\" name=\"UserID\" value=\"" + userID + "\" readonly/><br/><br/>" +
                        "Username: <input type=\"text\" name=\"Username\" value=\"" + username + "\" readonly/><br/><br/>" +
                        "First Name: <input type=\"text\" name=\"FirstName\" value=\"" + firstName + "\"/><br/><br/>" +
                        "Last Name: <input type=\"text\" name=\"LastName\" value=\"" + lastName + "\"/><br/><br/>" +
                        "Email: <input type=\"text\" name=\"Email\" value=\"" + email + "\"/><br/><br/>" +
                        "Address: <input type=\"text\" name=\"Address\" value=\"" + address + "\"/><br/><br/>" +
                        "City: <input type=\"text\" name=\"City\" value=\"" + city + "\"/><br/><br/>" +
                        "State: <input type=\"text\" name=\"State\" value=\"" + state + "\"/><br/><br/>" +
                        "Zip code: <input type=\"text\" name=\"Zipcode\" min=\"10000\" oninput=\"javascript:if (this.value > 5) this.value = this.value.slice(0, 5);\" value=\"" + zipcode + "\"/><br/><br/>" +
                        "Phone Number: <input type=\"text\" name=\"Phone\" oninput=\"javascript: if (this.value > 10) this.value = this.value.slice(0, 10);\" value=\"" + phone + "\"/><br/><br/>" +
                        "Credit Card Number: <input type=\"text\" name=\"CreditCard\" value=\"" + ccNum + "\"/><br/><br/>" +
                        request.getSession().getAttribute("updateLog") +
                        "<br/><br/>" +
                        "<input type=\"submit\" value=\"Update\"/>" +
                        "</form>" +
                        "<hr>" +
                        "<form action=\"UpdatePassword\" method=\"post\">" +
                        "Old password: <input type=\"password\" name=\"OldPassword\"/><br/><br/>" +
                        "New password: <input type=\"password\" name=\"NewPassword\"/><br/><br/>" +
                        "Confirm password: <input type=\"password\" name=\"ConfirmPassword\"/><br/><br/>" +
                        request.getSession().getAttribute("pwUpdateLog") +
                        "<br/><br/>" +
                        "<input type=\"submit\" value=\"Update Password\"/>" +
                        "</form>" +
                        "</div></div>";

        request.getSession().removeAttribute("updateLog");
        request.getSession().removeAttribute("pwUpdateLog");

        out.println(print);
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
