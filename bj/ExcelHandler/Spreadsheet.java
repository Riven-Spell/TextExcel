package ExcelHandler;
public class Spreadsheet implements java.io.Serializable
{
    String[][] sh;
    
    public Spreadsheet()
    {
        sh = new String[10][7];
        for(int i = 0;i < 10;i++)
        {
            for(int o = 0;o < 7;o++)
            {
                sh[i][o] = "";
            }
        }
    }
    
    public String[][] getSpreadSheet()
    {
        return sh;
    }
    
    public void setLoc(int x,int y,String toSet)
    {
        sh[y][x] = toSet;
    }
    
    public int[] findObj(String obj)
    {
        int x = -1;
        int y = -1;
        int[] out = {-1,-1};
        for(String[] column:sh)
        {
            for(String row:column)
            {
                if(row.equals(obj))
                {
                    out[0] = x;
                    out[1] = y;
                    return out;
                }
                x++;
            }
            y++;
        }
        return out;
    }
}
