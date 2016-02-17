package ExcelHandler;

public class CommandHandler
{
    static String lets = "abcdef";
    static String s = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890-=!@#%$^&*()_+[]\\{}|;':\"<>?,./";
    public static void handle(String input, Spreadsheet sh)
    {
        boolean scream = true;
        for(char c:input.toCharArray())
        {
             if(s.indexOf(c) == -1)
             {
                scream = false;
             }
        }
        if(scream)
        {
            System.out.println("WOAH, LOOKS LIKE YOU LEFT YOUR CAPS LOCK ON!!!!");
        }
        input = input.toLowerCase();
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
                help(input);
                break;
            case "cls":
                System.out.print("\f");
                break;
            default:
                if(input.indexOf('=') != -1)
                {
                    try
                    {
                        char c = input.toCharArray()[0];
                        int columns = lets.indexOf(c);
                        String s = input.substring(1,input.indexOf(' '));
                        sh.setLoc(columns, Integer.parseInt(s) - 1, input.substring(input.indexOf('=')+1));
                    }
                    catch(Exception ex)
                    {
                        System.err.println("dickInvalid command \"" + i + "\" input. Type \"help\" to have a list of commands");
                    }
                }
                else
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
            String sx = "" + x;
            System.out.println("------------+------------+------------+------------+------------+------------+------------+------------+");
            System.out.print("       ".substring(0,Util.Math.Clamp(0,7,6-(sx.length()-1))) + x + "     |");
            for(String row:column)
            {
                String sec = "            ";
                System.out.print(row.substring(0,Util.Math.Clamp(0,12,row.length())) + sec.substring(0,Util.Math.Clamp(0,12,12 - (row.length()-1))));
                System.out.print('|');
            }
            System.out.println();
        }
    }
    
    public static void help(String i)
    {
        if(i.indexOf(' ') == -1)
        {
            System.out.println("Available commands: \nprint - prints the table out\nexit - exits the program\nhelp [command] - shows this text\ncls - Clear screen");
        }
        else
        {
            switch(i.substring(i.indexOf(' ') + 1))
            {
                case "print":
                    System.out.println("Prints the table. No parameters.");
                    break;
                case "exit":
                    System.out.println("Exits the program. No parameters.");
                    break;
                case "help":
                    System.out.println("Shows help for all or a specific command. Parameters: help [command]");
                    break;
                case "cls":
                    System.out.println("Clears the screen. No parameters");
                    break;
                default:
                    System.err.println("That's not a command I know, or you added too many parameters.");
                    break;
            }
        }
    }
}
