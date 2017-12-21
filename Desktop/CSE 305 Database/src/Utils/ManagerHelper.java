package Utils;

import Database.Database;

import java.sql.ResultSet;

public class ManagerHelper {
    public static boolean validateLogin(String username,String password)
    {
        String q = "Select * from employee where username =\""+username+"\"";
        try {
            ResultSet res = Database.executeQ(q);
            res.next();
            String actualPw = res.getString("password");
            String manager = res.getString("isManager");
            if(actualPw.equals(password) && manager.equals("1") )
                return true;
            else
                return false;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public static String parseDays(String days)
    {
        String res="";
        if(Character.toString(days.charAt(0)).equals("1"))
            res+="Monday,";
        if(Character.toString(days.charAt(1)).equals("1"))
            res+=" Tuesday,";
        if(Character.toString(days.charAt(2)).equals("1"))
            res+=" Wednesday,";
        if(Character.toString(days.charAt(3)).equals("1"))
            res+=" Thursday,";
        if(Character.toString(days.charAt(4)).equals("1"))
            res+=" Friday,";
        if(Character.toString(days.charAt(5)).equals("1"))
            res+=" Saturday,";
        if(Character.toString(days.charAt(6)).equals("1"))
            res+="Sunday";
        if(Character.toString(res.charAt(res.length()-1)).equals(","))
            res = res.substring(0,res.length()-1);
        return res;
    }

}
