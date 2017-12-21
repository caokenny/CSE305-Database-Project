package Manager_Transactions;

import Utils.ManagerHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/ManagerLogin")
public class ManagerLogin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if(ManagerHelper.validateLogin(username,password))
            {
                request.getSession().setAttribute("managerLoggedIn", username);
                response.sendRedirect("Manager_index.jsp");
            }
            else{
                request.getSession().setAttribute("log", "Incorrect Username/Password");
                RequestDispatcher rq = request.getRequestDispatcher("index.jsp");
                rq.include(request,response);

            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
