package ExcelHandler;

public class CommandHandler
{
    public static void handle(String input, Spreadsheet sh)
    {
        String i = input;
        if(i.indexOf(' ') != -1)
        {
            i = i.substring(0,i.indexOf(' '));
        }
        switch(i)
        {
            case "print":
                print(sh);
        }
        Calc.InputLoop.calculate(input);
    }
    
    public static void print(Spreadsheet sh)
    {
        System.out.println("            |     A      |     B      |     C      |     D      |     E      |     F      |     G      |");
        
    }
}
