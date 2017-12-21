package Manager_Transactions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/MonthlySaleReportPage")
public class MonthlySaleReportPage extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PrintWriter out = response.getWriter();

            out.println(
                    "<head> \n" +
                    "    <link rel=\"stylesheet\" type=\"text/css\" href=\"/css/homeStyle.css\">" +
                    "</head>"
            );

            out.println("<div style=\"text-align: center; margin: 0 auto;\">\n" +
                    "   <ul class=\"tab\" style=\"text-align: center; margin: 0 auto;\">\n" +
                    "       <li><a href=\"javascript:void(0)\" class=\"tablinks\">Manager</a></li>\n" +
                    "   </ul>" +
                    "<div class=\"loggedInTabContent\"");

            response.setContentType("text/html");
            out.println("<h3> Select Month</h3>");
            out.println("<form action=GetMonthlyReport method = get><br>");
            out.println("<select name= month>");
            out.println("<option value = 1>January</option><br>");
            out.println("<option value = 2>February</option><br>");
            out.println("<option value = 3>March</option><br>");
            out.println("<option value = 4>April</option><br>");
            out.println("<option value = 5>May</option><br>");
            out.println("<option value = 6>June</option><br>");
            out.println("<option value = 7>July</option><br>");
            out.println("<option value = 8>August</option><br>");
            out.println("<option value = 9>September</option><br>");
            out.println("<option value = 10>October</option><br>");
            out.println("<option value = 11>November</option><br>");
            out.println("<option value = 12>December</option><br>");
            out.println("</select>");
            out.println("<input type = submit value =Get Report>");
            out.println("</form><br>");
            HomePage.goBackButton(out);
            out.println("</div></div>");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
