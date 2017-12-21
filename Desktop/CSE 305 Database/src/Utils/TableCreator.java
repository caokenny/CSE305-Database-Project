package Utils;

public class TableCreator {
    String initTable= "<style>\n" +
            "table {\n" +
            "    font-family: arial, sans-serif;\n" +
            "    border-collapse: collapse;\n" +
            "    width: 100%;\n" +
            "}\n" +
            "\n" +
            "td, th {\n" +
            "    border: 1px solid #dddddd;\n" +
            "    text-align: left;\n" +
            "    padding: 8px;\n" +
            "}\n" +
            "\n" +
            "tr:nth-child(even) {\n" +
            "    background-color: #dddddd;\n" +
            "}\n" +
            "</style>" +
            "<table styele =\"width:100%\">\n" +
            "<tr>\n";

    public TableCreator(String [] columnNames)
    {
        for(int x= 0 ; x< columnNames.length;x++)
        {
            initTable+="<th>" + columnNames[x] + "</th>\n";
        }
        initTable += "</tr>\n";
    }

    public void addRow(String[] cells)
    {
        initTable +="<tr>\n";
        for(int x= 0;x <cells.length;x++)
        {
            initTable+="<td>"+cells[x]+"</td>";
        }
        initTable += "</tr>\n";
    }
    public void addFormRow(String [] cells , String servletName)
    {
        initTable += "<tr><form action =\""+servletName+"\" method = \"get\">\n";
        for(int x= 0;x <cells.length;x++)
        {
            initTable+="<td>"+cells[x]+"</td>";
        }
        initTable +="</form> </tr>\n";
    }
    public String returnTable()
    {
        return initTable +"</table>";
    }

}
