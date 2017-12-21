<%@ page import="com.sun.org.apache.xpath.internal.operations.String" %><%--
  Created by IntelliJ IDEA.
  Person: KennyCao
  Date: 12/2/17
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="/css/homeStyle.css">
    <script src="/js/homeFunction.js"></script>
    <title>Flight Hounds</title>
  </head>
  <body>
    <h1 style="text-align: center">Welcome to Flight Hounds </h1>
    <div style="text-align: center; margin: 0 auto;">
      <ul class="tab" style="text-align: center; margin: 0 auto;">
        <li><a href="javascript:void(0)" class="tablinks">Employee</a></li>
        <li><a href="javascript:callServlet('EmployeeLogout')">Logout</a></li>
      </ul>

      <div id="Employee" class ="loggedInTabContent">
        <h3>Employee Page</h3>
        <li><a href="javascript:void(0)" class="tablinks" onclick="openPage(event, 'RecordReservation')">Record a reservation</a></li><br/>
        <li><a href="javascript:void(0)" class="tablinks" onclick="openPage(event, 'AddCustomer')">Add Customer</a></li><br/>
        <li><a href="javascript:void(0)" class="tablinks" onclick="openPage(event, 'EditCustomer')">Edit Customer</a></li><br/>
        <li><a href="javascript:void(0)" class="tablinks" onclick="openPage(event, 'DelCustomer')">Delete Customer</a></li><br/>
        <li><a href="javascript:void(0)" class="tablinks" onclick="callServlet('ProductMailLists')">Customer Mail List</a></li><br/>
        <li><a href="javascript:void(0)" class="tablinks" onclick="openPage(event, 'SuggestFlight')">Suggestion Flight</a></li><br/>
      </div>

      <div id="RecordReservation" class="tabcontent">
        <h1> Record a reservation </h1>
        <form action="RecordReservation" method="processRequest">
          <label>Employee ID: </label><input type = "number" name = "empid"/><br/><br/>
          <label>Account Number:</label> <input type = "number" name = "AccountNo"/><br/><br/>
          <label>Airline ID:</label><input type="text" name="airlineid"/><br/><br/>
          <label>Flight Number :</label><input type = "number" name="flightnum"/><br/><br/>
          <label>LegNo:</label> <input type ="number" name="legno"/><br/><br/>
          <label>Seat Number:</label> <input type = "text" name = "SeatNo"/><br/><br/>
          <label>Class: </label><input type = "text" name = "Class"/><br/><br/>
          <label>Meal: </label><input type = "text" name = "Meal"/><br/><br/>
          <input type="submit" value="Record Reservation"/>
        </form>
      </div>

      <div id="AddCustomer" class="tabcontent">
        <h1> Enter Customer information </h1>
        <form action="AddCustomer" method="processRequest">
          <label>First Name: </label><input type="text" name="FirstName"/><br/><br/>
          <label>Last Name: </label><input type="text" name="LastName"/><br/><br/>
          <label>Username: </label><input type="text" name="Username"/><br/><br/>
          <label>Password: </label><input type="password" name="Password"/><br/><br/>
          <label>E-mail: </label><input type="text" name="Email"/><br/><br/>
          <label>Address: </label><input type="text" name="Address"/><br/><br/>
          <label>State: </label><input type="text" name="State"/><br/><br/>
          <label>City: </label><input type="text" name="City"/><br/><br/>
          <label>Zip Code: </label><input type="number" name="ZipCode"/><br/><br/>
          <label>Telephone:</label> <input type="number" name="Telephone"/><br/><br/>
          <label>Credit Card Number:</label> <input type="number" name="CreditCardNum"/><br/><br/>
          <input type="submit" value="Create Account"/>
        </form>
      </div>

      <div id="DelCustomer" class="tabcontent">
        <h1>Hello! Please Register below: </h1>
        <form action="DeleteCustomer" method="processRequest">
          <label>ID:</label> <input type = "text" name = "Id"/><br/><br/>
          <input type="submit" value="Delete Account"/>
        </form>
      </div>

      <div id="EditCustomer" class="tabcontent">
        <h1>Please Enter the Customer AccountNo </h1>
        <form action="EditCustomerPage" method="processRequest">
          <label>Customer Account Number:</label> <input type="text" name="accno">
          <input type="submit" value="Edit Account"/>
        </form>
      </div>

      <div id="ListEmail" class="tabcontent">
        <form action="ProductMailLists" method="processRequest">
          <input type="submit" value="List Email"/>
        </form>
      </div>

      <div id="SuggestFlight" class="tabcontent">
        <h1>Hello! Please Register below: </h1>
        <form action="ProductFlightSuggestions" method="processRequest">
          <label>Customer Account Number:</label> <input type = "text" name = "accNo"/><br/><br/>
          <input type="submit" value="List Flight"/>
        </form>
      </div>


    </div>
  </body>
</html>
