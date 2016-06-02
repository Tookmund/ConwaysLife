import java.util.Scanner;
import java.io.*;
public class concommon
{
   // pre: fn is a path to a conways save, row and col and the total number of rows
   // and columns, and rulefile is the path to the rulefile to use
   // post: Return a Conways with the given save loaded or a random Conways
   // if loading the save failed. Both have the rulefile.
   public static Conways fromFile(String fn,int row,int col,String rulefile)
   {
      Conways con;
      Scanner input;
      try
      {
         input = new Scanner(new File(fn));
         con = new Conways(input.nextInt(),input.nextInt(),rulefile);
      }
      catch(Exception e)
      {
         con = new Conways(row,col,rulefile);
         con.populate();
         con.error = "File "+fn+" Not Found or Corrupted";
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
            else continue;
         }
      }
      return con;
   }
   // pre: con is a running conways game, filename is a path to save the game to
   // post: con is written to filename
   public static void toFile(Conways con, String filename)
   {
      PrintWriter f;
      try
      {
        f = new PrintWriter(filename,"UTF-8");
      }
      catch(Exception e)
      {
         con.error = "Failed to open "+filename;
         return;
      }
      f.println(con.numRows()+" "+con.numColumns());
      f.println(con);
      f.close();
   }
}