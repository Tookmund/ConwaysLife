import java.util.Scanner;
import java.util.Random;
import java.io.*;

public class cli
{
   private static Conways con;
   private static Scanner input = new Scanner(System.in);

   public static void main(String[] args)    {
      System.out.print("File input? [1/0]");
      int i = input.nextInt();
      if (i > 0)
      {
         System.out.print("Filename: ");
         con = concommon.fromFile(input.next());
      }
      else
      {
         System.out.print("How many rows and columns? ");
         int r = input.nextInt();
         int c = input.nextInt();
         con = new Conways(r,c);
         con.populate();
         System.out.print("Output layout to file? [1/0]");
         i = input.nextInt();
         if (i > 0)
         {
             System.out.print("Filename: ");
             String filename = input.next();
             concommon.toFile(con,filename);
         }
      }
      System.out.print("Log to File? [1/0]");
      int f = input.nextInt();
      if (f > 0)
      {
         System.out.print("Filename: ");
         String filename = input.next();
         try
         {
            System.setOut(new PrintStream( new BufferedOutputStream( new FileOutputStream(filename) ) ));
         }
         catch(Exception e)
         {
            System.out.println(e.getMessage());
            System.out.println("Continuing with standard output");
         }
      }
      while(true)
      {
         System.out.println(con);
         con.generation();
      }
   }
}