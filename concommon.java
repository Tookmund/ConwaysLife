import java.util.Scanner;
import java.io.*;
public class concommon
{
   public static Conways fromFile(String fn)
   {
      Conways con;
      Scanner input;
      try
      {
         input = new Scanner(new File(fn));
         con = new Conways(input.nextInt(),input.nextInt());
      }
      catch(Exception e)
      {
         System.err.println("File "+fn+" Not Found\nUsing Random instead.");
         System.out.println("How many rows and columns?");
         Scanner sysout = new Scanner(System.in);
         con = new Conways(sysout.nextInt(),sysout.nextInt());
         con.populate();
         return con;
      }
      for(int r = 0; input.hasNext() && r < con.numRows(); r++)
      {
         for (int c = 0; input.hasNext() && c < con.numColumns(); c++)
         {
            char i = input.next().charAt(0);
            if(i == '*')
            {
               con.set(r,c,new life());
            }
            else if (i == '-') continue;
            else
            {
               // Redo this step of the loop
               c--;
            }
         }
      }
      return con;
   }
   public static void toFile(Conways con, String filename)
   {
      PrintWriter f;
      try
      {
        f = new PrintWriter(filename,"UTF-8");
      }
      catch(Exception e)
      {
         System.out.println(e.getMessage()+"\nFailed to open "+filename);
         return;
      }
      f.println(con.numRows()+" "+con.numColumns());
      f.println(con);
      f.close();
   }
}