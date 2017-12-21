package Customer;

import Database.Database;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;

@WebServlet("/TravelItinerary")
public class TravelItinerary extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String resId = request.getParameter("resId");
            String itinerary = "SELECT A.City AS \"City To Be Stopped At In Order\"\n" +
                    ", A.Country\n" +
                    "FROM Includes I, Leg L ,Airport A\n" +
                    "WHERE I.ResrNo = '"+resId+"'\n" +
                    "AND I.airlineID= L.AirlineID\n" +
                    "AND I.FlightNo = L.FlightNo \n" +
                    "AND I.LegNo = L.LegNo\n" +
                    "AND A.Id = L.DepAirportID\n" +
                    "UNION\n" +
                    "SELECT A.City, A.Country\n" +
                    "FROM Includes I, Leg L ,Airport A\n" +
                    "WHERE I.ResrNo ='"+resId+"'\n" +
                    "AND I.airlineID= L.AirlineID\n" +
                    "AND I.FlightNo = L.FlightNo \n" +
                    "AND I.LegNo = L.LegNo\n" +
                    "AND A.Id = L.ArrAirportID;\n";
            String finalRes = "";
            ResultSet result = Database.executeQ(itinerary);
            result.beforeFirst();
            while(result.next()){
                finalRes += "City To Be Stopped At In Order: " + result.getString("City") + ", Country: " + result.getString("Country");
            }
            System.out.println(finalRes);

            request.getSession().setAttribute("log", "");
        }catch(Exception e){
            request.getSession().setAttribute("log", "Cannot list best seller list: " + e.toString());
        }
        request.getSession().setAttribute("result", "");
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
