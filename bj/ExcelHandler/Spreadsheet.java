package ExcelHandler;
public class Spreadsheet implements java.io.Serializable
{
    String[][] sh;
    
    public Spreadsheet()
    {
        sh = new String[10][7];
    }
}
