package Manager_Transactions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HomePage")
public class HomePage extends HttpServlet {
    public static void HomePage(PrintWriter out)
    {
        out.println("<form action=\"Manager_index.jsp\" method =\"get\">\n" +
                "         <input type =\"submit\"   value=\"Back\">");
    }
    public static void goBackButton(PrintWriter out)
    {
        out.println("<button style=\"text-align: center;\" onclick=\"goBack()\">Back</button>\n" +
                "\n" +
                "<script>\n" +
                "function goBack() {\n" +
                "    window.history.back();\n" +
                "}\n" +
                "</script>");
    }
    public static void goBackButton(PrintWriter out,String name)
    {
        out.println("<button style=\"text-align: center;\" onclick=\"goBack()\">"+name+"</button>\n" +
                "\n" +
                "<script>\n" +
                "function goBack() {\n" +
                "    window.history.back();\n" +
                "}\n" +
                "</script>");
    }
}
