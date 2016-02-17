import ExcelHandler.Spreadsheet;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("\fWelcome to TextExcel!");
        Spreadsheet sh = new Spreadsheet();
        Scanner s = new Scanner(System.in);
        String input = "";
        while(!input.equals("exit"))
        {
            System.out.print("\nEnter a command: ");
            input = s.nextLine();
            if(input.equals("exit"))
            {
            }
            else
                ExcelHandler.CommandHandler.handle(input, sh);
        }
        System.out.println("\nFarewell!");
    }
}
