import java.util.Random;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Conways 
{
   private Matrixable<life> board;
   
   public Conways(int r, int c)
   {
      board = new SparseMatrix<life>(r,c);
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
   public int livingNeighbors(Matrixable<life> b,int r,int c)
   {
      // Check all eight neighbors
      int total = 0;
      // The rows to be checked
      int[] rows = {r-1,r,r+1};
      // The columns to be checked
      int[] cols = {c-1,c,c+1};
      // If there is nothing to the left check to the right
      // and vice versa
      if (rows[0] < 0) rows[0] = b.numRows()-1;
      if (rows[2] >= b.numRows()) rows[2] = 0;
      if (cols[0] < 0) cols[0] = b.numColumns()-1;
      if (cols[2] >= b.numRows()) cols[2] = 0;
      life l;
      for (int i = 0; i < rows.length; i++)
      {
         for (int j = 0; j < cols.length; j++)
         {
            l = b.get(rows[i],cols[j]);
            if (l != null) 
            {
            if (rows[i] == r && cols[j] == c) continue;
               total++;
            }
         }
      }
      return total;
   }
   public life update(life l,int n)
   {
       // Rules copied from http://www.conwaylife.com/wiki/Conway's_Game_of_Life
      boolean live = (l != null);
       // Any live cell with fewer than two live neighbours dies (referred to as underpopulation).
      if (live && n < 2) l = null;
       // Any live cell with more than three live neighbours dies (referred to as overpopulation or overcrowding).
      if (live && n > 3) l = null;
       // Any live cell with two or three live neighbours lives, unchanged, to the next generation.
      if (live && (n == 2 || n == 3) ) 
         return l;
       // Any dead cell with exactly three live neighbours will come to life.
      if (!live && n == 3) 
      {
         l = new life();
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
               //System.out.println("Life at "+r+","+c);
               set(r,c,new life());
            }
         }
      }
   }
   public String toString() { return board.toString(); }
   public int numRows() { return board.numRows(); }
   public int numColumns() { return board.numColumns(); }
   public void set(int r, int c, life l) { board.set(r,c,l); }
   public Object[][] toArray() { return board.toArray(); }
}