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
                break;
            case "help":
                help(i);
                break;
            default:
                System.err.println("Invalid command \"" + i + "\" input. Type \"help\" to have a list of commands");
        }
    }
    
    public static void print(Spreadsheet sh)
    {
        System.out.println("            |     A      |     B      |     C      |     D      |     E      |     F      |     G      |");
        String[][] sha = sh.getSpreadSheet();
        int x = 0;
        for(String[] column:sha)
        {
            x++;
            System.out.println("------------+------------+------------+------------+------------+------------+------------+------------+");
            System.out.print("     " + x + "     |");
            for(String row:column)
            {
                String sec = "            ";
                System.out.print(row.substring(0,Util.Math.Clamp(0,11,row.length())) + sec.substring(0,Util.Math.Clamp(0,12,12 - (row.length()-1))));
                System.out.print('|');
            }
            System.out.println();
        }
    }
    
    public static void help(String i)
    {
        if(i.indexOf(' ') == -1)
        {
            System.out.println("Available commands: \nprint - prints the table out\nexit - exits the program\nhelp [command] - shows this text");
        }
        else
        {
            switch(i.substring(i.indexOf(' ') + 1))
            {
                case "print":
                    System.out.println("Prints the table. No parameters.");
                case "exit":
                    System.out.println("Exits the program. No parameters.");
                case "help":
                    System.out.println("Shows help for all or a specific command. Parameters: help [command]");
                default:
                    System.err.println("That's not a command I know, or you added too many parameters.");
            }
        }
    }
}
