package Manager_Transactions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/AddEmployeePage")
public class AddEmployeePage extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try{
            PrintWriter out = response.getWriter();

            out.println(
                    "<head> \n" +
                    "    <link rel=\"stylesheet\" type=\"text/css\" href=\"/css/homeStyle.css\">" +
                    "</head>"
            );

            response.setContentType("text/html");
            out.println(
                    "<div style=\"text-align: center; margin: 0 auto;\">\n" +
                    "   <ul class=\"tab\" style=\"text-align: center; margin: 0 auto;\">\n" +
                    "       <li><a href=\"Manager_index.jsp\">Home</a></li>" +
                    "       <li><a href=\"javascript:void(0)\" class=\"tablinks\">Manager</a></li>\n" +
                    "   </ul>" +
                    "<div id=\"AddEmployee\" class=\"loggedInTabContent\">\n" +
                    "    <form action=\"AddEmployee\" method=\"post\">\n" +
                    "        <h3>Employee information</h3>\n" +
                    "        First Name: <input type=\"text\" name=\"FirstName\"/><br/><br/>\n" +
                    "        Last Name: <input type=\"text\" name=\"LastName\"/><br/><br/>\n" +
                    "        SSN: <input type=\"text\" name=\"SSN\" oninput=\"javascript:if (this.value > 9) this.value = this.value.slice(0, 9);\"/><br/><br/>\n" +
                    "        Username: <input type=\"text\" name=\"Username\"/><br/><br/>\n" +
                    "        Password: <input type=\"password\" name=\"Password\"/><br/><br/>\n" +
                    "        E-mail: <input type=\"text\" name=\"Email\"/><br/><br/>\n" +
                    "        Address: <input type=\"text\" name=\"Address\"/><br/><br/>\n" +
                    "        State: <input type=\"text\" name=\"State\"/><br/><br/>\n" +
                    "        City:<input type=\"text\" name=\"City\"/><br/><br/>\n" +
                    "        Zip Code: <input type=\"number\" name=\"ZipCode\" oninput=\"javascript:if (this.value > 5) this.value = this.value.slice(0, 5);\"/><br/><br/>\n" +
                    "        Telephone: <input type=\"number\" name=\"Telephone\" oninput=\"javascript:if (this.value > 10) this.value = this.value.slice(0, 10);\"/><br/><br/>\n" +
                    "        Hourly Rate: <input type=\"number\" name=\"rate\"/><br/><br/>\n" +
                    "        <input type =\"submit\" value = \"Add Employee\">\n" +
                    "    </form>\n" +
                    "</div>" +
                    "</div>");
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
