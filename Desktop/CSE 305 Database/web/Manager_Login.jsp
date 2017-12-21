<%--
  Created by IntelliJ IDEA.
  User: JackieChen
  Date: 12/5/17
  Time: 7:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manager Login</title>
</head>
<body>
<div style="margin: 0 auto;">
    <h3>LOGIN</h3>
    <form action="ManagerLogin" method = "post">
        <br/>
        <label>Username:</label> <input type = "text" name = "username" ><br/><br/>
        <label>Password:</label> <input type ="password" name = "password"><br/><br/>
        <input type ="submit" value="Enter">
    </form>
</div>
</body>
</html>
