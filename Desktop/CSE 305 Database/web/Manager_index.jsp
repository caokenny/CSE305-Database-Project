<%--
  Created by IntelliJ IDEA.
  User: JackieChen
  Date: 12/4/17
  Time: 5:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/homeStyle.css">
    <script src="/js/homeFunction.js"></script>
    <title>Manager Home Page</title>
</head>
<body>
<h1 style="text-align: center">Flight Hounds Manager Page</h1>
<div style="text-align: center; margin: 0 auto;">
    <ul class="tab" style="text-align: center; margin: 0 auto;">
        <li><a href="javascript:void(0)" class="tablinks">Manager</a></li>
        <li><a href="javascript:callServlet('ManagerLoggedOut')">Logout</a></li>
    </ul>
    <div class="loggedInTabContent">
        <div>
        <a style="padding: 10px 10px" href="SearchReservation" class="tablinks">Search Reservations</a>
            <a style="padding: 10px 10px" href ="DetermineMostRevenueRepresentative" class="tablinks">Top Employee</a>
            <a style="padding: 10px 10px" href ="DetermineMostRevenue" class="tablinks">Top Customer</a>
            <a style="padding: 10px 10px" href="ListMostActiveFlights" class ="tablinks">Most Active Flights</a>
        </div>
        <br>
        <div>
        <b><u>Manage Employees</u></b><br/>
        <form action="ShowEmployee" method="get">
            <input type ="submit" value="Show All Employee"><br/><br/>
        </form>
        <form action= "AddEmployeePage" method = "get">
            <input type = "submit" value="Add a new employee"><br/><br/>
        </form>
        <form action="DeleteEmployeePage" method = "get">
            <input type="submit" value="Edit Current Employees"><br/><br/>
        </form>

        </div>

        <div>
            <b><u>Flights</u></b> <br/>
            <form action="ShowFlights" method ="get">
                <input type ="submit"   value="Show All flights"><br/><br/>
            </form>

            <form action ="ListCustomerOnFlightPage" method="get">
                <input type="submit" value ="Show Customers On flight"><br/><br/>
            </form>

            <form action="ListFlightByAirportPage" method="get">
                <input type = "submit"  value="List all flights of an Airport"><br/><br/>
            </form>
            <form action="OnTimeAndDelayFlights" method ="get">
                <input type="submit" value="Flight Schedule"><br/><br/>
            </form>

        </div>

        <div>

            <b><u>SALES</u></b><br/>
            <form action="MonthlySaleReportPage" method="get">
                <input type = "submit" value="Sales summary by month"><br/><br/>
            </form>
            <form action="SearchSalesPage" method="get">
                <input type = "submit" value="Search Sales"><br/>
            </form>

        </div>
    </div>
</div>

</body>
</html>
