package Header;

public class Header {
    public static String getUserHeader() {
        String returnThis =
                "<head> \n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"/css/homeStyle.css\">" +
                "</head>" +
                "<div style=\"text-align: center; margin: 0 auto;\">\n" +
                "   <ul class=\"tab\" style=\"text-align: center; margin: 0 auto;\">\n" +
                "       <li><a href=\"index.jsp\">Home</a></li>" +
                "       <li><a href=\"javascript:void(0)\" class=\"tablinks\">User</a></li>\n" +
                "   </ul>" +
                "<div class=\"loggedInTabContent\">";
        return returnThis;
    }

    public static String getFooter() {
        String returnThis = "</div></div>";
        return returnThis;
    }

    public static String getManagerHeader() {
        String returnThis =
                "<head> \n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"/css/homeStyle.css\">" +
                "</head>" +
                "<div style=\"text-align: center; margin: 0 auto;\">\n" +
                "   <ul class=\"tab\" style=\"text-align: center; margin: 0 auto;\">\n" +
                "       <li><a href=\"Manager_index.jsp\">Home</a></li>" +
                "       <li><a href=\"javascript:void(0)\" class=\"tablinks\">Manager</a></li>\n" +
                "   </ul>" +
                "<div class=\"loggedInTabContent\">";
        return returnThis;
    }

    public static String getEmployeeHeader() {
        String returnThis =
                "<head> \n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"/css/homeStyle.css\">" +
                "</head>" +
                "<div style=\"text-align: center; margin: 0 auto;\">\n" +
                "   <ul class=\"tab\" style=\"text-align: center; margin: 0 auto;\">\n" +
                "       <li><a href=\"EmployeeIndex.jsp\">Home</a></li>" +
                "       <li><a href=\"javascript:void(0)\" class=\"tablinks\">Employee</a></li>\n" +
                "   </ul>" +
                "<div class=\"loggedInTabContent\">";
        return returnThis;
    }
}
