import java.util.ArrayList;
public class SparseMatrix<anyType> implements Matrixable<anyType>, Cloneable
{
   private int rows = 0;
   private int cols = 0;
   private ArrayList<matrixentry> al = new ArrayList<matrixentry>();
   private char bl = '-';
   
   public SparseMatrix(int r, int c)
   {
      rows = r;
      cols = c;
   }
   
   private SparseMatrix(ArrayList<matrixentry> mx,int r, int c)
   {
      al = mx;
       rows = r;
      cols = c;

   }
   
   public SparseMatrix<anyType> clone()
   {
      ArrayList<matrixentry> a = new ArrayList<matrixentry>();//(ArrayList<matrixentry>)al.clone();
      for(int i = 0; i < al.size(); i++)
      {
        // a.set(i,a.get(i).clone());
         a.add(al.get(i));
      
      }
      return new SparseMatrix<anyType>(a,rows,cols);
   }
   private int getkey(int r,int c) { 
      return (r*cols)+c; }

   public anyType get(int r, int c)				//returns the element at row r, col c
   {
      int k = getkey(r,c);
      for(int i = 0; i < al.size(); i++)
      {
         matrixentry m = al.get(i);
         if(getkey(m.getrow(),m.getcol()) == k) 
            return (anyType)al.get(i).getobj();
      }
      return null;
   }
   public anyType set(int r, int c, anyType x)	//changes element at (r,c), returns old value
   {
      int k = getkey(r,c);
      for (int i = 0; i < al.size(); i++)
      {
         matrixentry m = al.get(i);
         if(getkey(m.getrow(),m.getcol()) == k) 
         {
            anyType o = (anyType)m.getobj();
            m.setobj(x);
            return o;
         }
      }
      add(r,c,x);
      return null;
   }
   public void add(int r, int c, anyType x)	   //adds obj at row r, col c
   {
      matrixentry m = new matrixentry(r,c,x);
      for (int i = 0; i < al.size(); i++)
      {
         matrixentry old = al.get(i);
         if (getkey(old.getrow(),old.getcol()) > getkey(m.getrow(),m.getcol())) 
         {
            al.add(i,m);
            return;
         }
      }
      al.add(m);
   }
   public anyType remove(int r, int c)
   {
      int k = getkey(r,c);
      anyType o = null;
      for (int i = 0; i < al.size(); i++)
      {
         matrixentry m = al.get(i);
         if (getkey(m.getrow(),m.getcol()) == k)
         {
            o = (anyType)m.getobj();
            al.remove(i);
         }
      }
      return o;
   }
   public int size()			//returns # actual elements stored
   {
      return al.size();
   }
   public int numRows()		//returns # rows set in constructor
   {
      return rows;
   }
   public int numColumns()	//returns # cols set in constructor
   {
      return cols;
   }
   public String toString()
   {
      String s = "";
      for (int r = 0; r < rows; r++)
      {
         //s+=r;
         for (int c = 0; c < cols; c++)
         {
            anyType t = get(r,c);
            if (t != null) s+= t+" ";
            else s+=bl+" ";
         }
         s+="\n";
      }
      return s;
   }
   public Object[][] toArray()				//returns equivalent structure in 2-D array form
   {
      Object[][] o = new Object[numRows()][numColumns()];
      for (int i = 0; i < al.size(); i++)
      {
         matrixentry m = al.get(i);
         o[m.getrow()][m.getcol()] = m.getobj();
      }
      return o;
   }
   public boolean contains(anyType x)		//true if x exists in list
   {
      if (getLocation(x) != null) 
         return true;
      else 
         return false; 
   }
   public int[] getLocation(anyType x)	//returns location [r,c] of where x exists in list, null otherwise
   {
      int[] coord = new int[2];
      for (int i = 0; i < al.size(); i++)
      {
         matrixentry m = al.get(i);
         if (m.getobj().equals(x))
         {
            coord[0] = m.getrow();
            coord[1] = m.getcol();
            return coord;
         }
      }
      return null;
   }
   public boolean isEmpty()					//returns true if there are no actual elements stored
   {
      if (al.size() == 0) 
         return true;
      else 
         return false;
   }
   public void clear()							//clears all elements out of the list
   {
      al.clear();
   }
   public void setBlank(char blank)		//allows the client to set the character that a blank spot in the array is
   													//represented by in String toString()
   {
      bl = blank;
   }
}