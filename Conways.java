import java.util.Random;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.*;

public class Conways 
{
   private Matrixable<life> board;
   private boolean wraparound = true;
   private Integer[] born = {3};
   private Integer[] survive = {2,3};

   public Conways(int r, int c, String rules)
   {
      board = new SparseMatrix<life>(r,c);
      try
      {
         if (rules != null) loadrules(rules);
      }
      catch(Exception e)
      {
         System.err.println("IOException in Conways\nPlease restart");
         System.exit(1);
      }
   }
   public Integer[] getBorn() { return born; }
   public Integer[] getSurvive() { return survive; }
   private void loadrules(String fn) throws IOException
   {
      File rulesfile;
      FileInputStream rules;
      try 
      {
         
         rulesfile = new File(fn);
         rules = new FileInputStream(rulesfile);
      }
      catch (Exception e)
      {
         System.err.println("Rules File failed to load\nUsing Conways instead");
         return;
      }
      int c;
      // arraylists converted back to arrays at the end
      ArrayList<Integer> b = new ArrayList<Integer>();
      ArrayList<Integer> s = new ArrayList<Integer>();
      // skip B/b
      rules.read();
      // Where to add numbers
      boolean br = true;
      for (long i = 0; i < rulesfile.length(); i++)
      {
         c = rules.read();
         if (c == '/')
         {
            // Skip S/s
            rules.read();
            br = false;
         }
         else
         {
            if (br)
            {
               b.add(c);
            }
            else
            {
               s.add(c);
            }
         }
      }
      born = (Integer[])(b.toArray());
      survive = (Integer[])(s.toArray());
   }
   public void generation()
   {
      Matrixable<life> b = board.clone();
      for(int r = 0; r < board.numRows(); r++)
      {
         for(int c = 0; c < board.numColumns(); c++)
         {
            int n = livingNeighbors(b,r,c);
            life l = update(board.get(r,c),n);
            if (l != null) board.set(r,c,l);
            else board.remove(r,c);
         }
      }
   }
   public boolean wrap() 
   { 
      wraparound = !wraparound;
      return wraparound;
   }
   public int getNeighbors(int r, int c)
   {
      return livingNeighbors(board,r,c);
   }
   private int livingNeighbors(Matrixable<life> b,int r,int c)
   {
      // Check all eight neighbors
      int total = 0;
      // The rows to be checked
      int[] rows = {r-1,r,r+1};
      // The columns to be checked
      int[] cols = {c-1,c,c+1};
      // If there is nothing to the left check to the right
      // and vice versa
      if (rows[0] < 0) 
      {
         if (wraparound) rows[0] = b.numRows()-1;
         else rows[0] = -1;
      }
      if (rows[2] >= b.numRows()) 
      {
         if (wraparound) rows[2] = 0;
         else rows[2] = -1;
      }
      if (cols[0] < 0) 
      {
         if (wraparound) cols[0] = b.numColumns()-1;
         else cols[0] = -1;
      }
      if (cols[2] >= b.numRows()) 
      {
         if(wraparound) cols[2] = 0;
         else cols[2] = -1;
      }
      life l;
      for (int i = 0; i < rows.length; i++)
      {
         for (int j = 0; j < cols.length; j++)
         {
            if (rows[i] != -1 && cols[i] != -1) l = b.get(rows[i],cols[j]);
            else continue;
            if (l != null) 
            {
            if (rows[i] == r && cols[j] == c) continue;
               total++;
            }
         }
      }
      return total;
   }
   public life update(life l, int n)
   {
      if (l == null)
      {
         for (int i = 0; i < born.length; i++)
         {
            if (born[i] == n) return new life();
         }
      }
      else
      {
         for (int i = 0; i < survive.length; i++)
         {
            if (survive[i] == n) return l;
         }
         return null;
      }
      return l;
   }
   public void populate()
   {
      Random rand = new Random();
      for (int r = 0; r < numRows(); r++)
      {
         for (int c = 0; c < numColumns(); c++)
         {
            if (rand.nextInt(10) > 5) 
            {
               set(r,c,new life());
            }
         }
      }
   }
   public String toString() { return board.toString(); }
   public int numRows() { return board.numRows(); }
   public int numColumns() { return board.numColumns(); }
   public life set(int r, int c, life l) { return board.set(r,c,l); }
   public life get(int r, int c) { return board.get(r,c); }
   public void clear() { board.clear(); }
}