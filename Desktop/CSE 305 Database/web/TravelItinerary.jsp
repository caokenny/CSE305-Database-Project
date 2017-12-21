<%--
  Created by IntelliJ IDEA.
  User: KennyCao
  Date: 12/5/17
  Time: 3:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Travel Itinerary</title>
</head>
<body>
  <%
    RequestDispatcher dispatcher = request.getRequestDispatcher("TravelItinerary");
    dispatcher.forward(request, response);
  %>
</body>
</html>
