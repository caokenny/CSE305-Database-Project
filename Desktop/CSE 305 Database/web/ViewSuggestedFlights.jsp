<%--
  Created by IntelliJ IDEA.
  User: KennyCao
  Date: 12/5/17
  Time: 8:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>View Suggested Flights</title>
</head>
<body>
  <%
    RequestDispatcher dispatcher = request.getRequestDispatcher("ProduceSuggestedFlights");
    dispatcher.forward(request, response);
  %>
</body>
</html>
