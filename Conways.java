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
   // If the rulefile passed to the constructor is valid
   public boolean rulesValid = true;
   // If there was an error when using the conways object
   public String error;

   // pre: r is the number of rows, c is the number of columns and c is a rulefile name
   // post; Creates a Conways object, setting rulesValid to false if the rulefile is invalid
   public Conways(int r, int c, String rules)
   {
      board = new SparseMatrix<life>(r,c);
      if (rules != null) 
      {
         byte b = loadrules(rules);
         if (b > 0) rulesValid = false;
      }
   }
   // post: Creates a new board of type SparseMatrix
   public void SparseMatrix()
   {
      board = new SparseMatrix<life>(numRows(),numColumns());
   }
   // post: Creates a new board of type ArrayMatrix
   public void ArrayMatrix()
   {
      board = new ArrayMatrix<life>(numRows(),numColumns());
   }
   // pre: fn is a path to a file in born/survive format
   // post: fills the born and survive arrays
   private byte loadrules(String fn)
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
         return 1;
      }
      int c = -1;
      // arraylists converted back to arrays at the end
      ArrayList<Integer> b = new ArrayList<Integer>();
      ArrayList<Integer> s = new ArrayList<Integer>();
      // Where to add numbers
      boolean br = true;
      // Born/Survive
      for (long i = 0; i < rulesfile.length(); i++)
      {
         try
         {
            c = rules.read();
         }
         catch(Exception e) 
         {
            return 1;
         }
         if (c == '/') br = false;
         else
         {
            // Subtract 48 because these are raw bytes
            // Zero is 48 in ASCII
            c = c-48;
            if (br)
            {
               if (c >= 0) b.add(c);
            }
            else
            {
               if (c >= 0) s.add(c);
            }
         }
      }
      // Have to fill arrays because arraylist toArray doesn't work properly
      born = new Integer[b.size()];
      for(int i = 0; i < b.size(); i++)
      {
         born[i] = (Integer)(b.get(i));
      }
       survive = new Integer[s.size()];
      for(int i = 0; i < s.size(); i++)
      {
         survive[i] = (Integer)(s.get(i));
      }
      return 0;
   }
   // post: Advances all cells one generation
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
   // post: Toggles wraparound
   public boolean wrap() 
   { 
      wraparound = !wraparound;
      return wraparound;
   }
   public boolean getwrap() { return wraparound; }
   // calls other livingNeighbors with current board
   // see other livingNeighbors
   public int livingNeighbors(int r, int c)
   {
      return livingNeighbors(board,r,c);
   }
   // pre: b is a board to check, r and c are a valid location of a cell on the board
   // post: returns the number of neighbors a cell has
   private int livingNeighbors(Matrixable<life> b,int r,int c)
   {
      // Check all eight neighbors
      int total = 0;
      for (int row = r-1; row <=r+1; row++)
      {
         for (int col = c-1; col <= c+1; col++)
         {
            if (row == r && col == c) continue;
            if (wraparound)
            {
               int oldrow = row;
               int oldcol = col;
               if (row == 0) row = (numColumns()-1);
               else if (row == (numRows()-1)) row = 0;
               if (col == 0) col = (numColumns()-1);
               else if (col == (numColumns()-1)) col = 0;
               if (b.get(row,col) != null) total++;
               row = oldrow;
               col = oldcol;
            }
            else
            {
               if (row >= 0 && row < numRows() && col >= 0 && col < numColumns())
               {
                  if (b.get(row,col) != null) total++;
               }
            }
         }
      }
      return total;
   }
   // pre: n is the number of neighbors a cell has
   // post; returns whether a cell can be born this generation
   public boolean isBorn(int n)
   {
      for (int i = 0; i < born.length; i++)
      {
         if (born[i] == n) return true;
      }
      return false;
   }
   // pre: n is the number of neighbors a cell has
   // post: returns whether a cell can survive this generation
   public boolean isSurvive(int n)
   {
      for (int i = 0; i < survive.length; i++)
      {
         if (survive[i] == n) return true;
      }
      return false;
   }
   // pre: l is a cell at a location, n is the number of neighbors l has
   // post: returns an updated version of l based on the current ruleset
   public life update(life l, int n)
   {
      if (l == null)
      {
         if (isBorn(n)) return new life();
         return null;
      }
      else
      {
         if (isSurvive(n)) return l;
         return null;
      }
   }
   // post: the board is randomly populated with cells
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
   // Methods exposed from the board
   // See Matrixable
   public String toString() { return board.toString(); }
   public int numRows() { return board.numRows(); }
   public int numColumns() { return board.numColumns(); }
   public life set(int r, int c, life l) { return board.set(r,c,l); }
   public life get(int r, int c) { return board.get(r,c); }
   public void clear() { board.clear(); }
}