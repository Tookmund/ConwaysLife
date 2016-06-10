// Created to test the difference between SparseMatrix and standard Array

public class ArrayMatrix<anyType> implements Matrixable<anyType>, Cloneable
{
      private Object[][] ar;
      private char bl = '-';
      public ArrayMatrix(int r, int c)
      {
         ar = new Object[r][c];
      }
      private ArrayMatrix(Object[][] b)
      {
         ar = b;
      } 
      public anyType get(int r, int c)				//returns the element at row r, col c
      {
         return (anyType)(ar[r][c]);
      }
      public anyType set(int r, int c, anyType x)	//changes element at (r,c), returns old value
      {
         anyType tmp = (anyType)(ar[r][c]);
         ar[r][c] = x;
         return tmp;
      }
      public void add(int r, int c, anyType x)	   //adds obj at row r, col c
      {
         ar[r][c] = x;
      }
      public anyType remove(int r, int c)
      {
         anyType tmp = (anyType)(ar[r][c]);
         ar[r][c] = null;
         return tmp;
      }
      public int size()			//returns # actual elements stored
      {
         int s = 0;
         for (int r = 0; r < ar.length; r++)
         {
            for (int c = 0; c < ar[0].length; c++)
            {
               if (ar[r][c] != null) s++;
            }
         }
         return s;
      }
      public int numRows()		//returns # rows set in constructor
      {
         return ar.length;
      }
      public int numColumns()	//returns # cols set in constructor
      {
         return ar[0].length;
      }
      public Object[][] toArray()				//returns equivalent structure in 2-D array form
      {
         return ar;
      }
      public boolean contains(anyType x)		//true if x exists in list
      {
         for (int r = 0; r < ar.length; r++)
         {
            for (int c = 0; c < ar.length; c++)
            {
               if ( ((anyType)ar[r][c]).equals(x)) return true;
            }
         }
         return false;
      }
      public int[] getLocation(anyType x)	//returns location [r,c] of where x exists in list, null otherwise
      {
         int[] i = null;
         for (int r = 0; r < ar.length; r++)
         {
            for (int c = 0; c < ar.length; c++)
            {
               if ( ((anyType)ar[r][c]).equals(x))
               {
                  i = new int[2];
                  i[0] = r;
                  i[1] = c;
                  return i;
               }
            }
         }
         return i;
      }
      public boolean isEmpty()					//returns true if there are no actual elements stored
      {
         if (ar.length == 0) return true;
         return false;
      }
      public void clear()							//clears all elements out of the list
      {
         ar = new Object[ar.length][ar[0].length];
      }
		public void setBlank(char blank)		//allows the client to set the character that a blank spot in the array is
															//represented by in String toString()
      {
         bl = blank;
      }
      public Matrixable<anyType> clone()  //clone a Matrixable
      {
         Object [][] obj = new Object[ar.length][];
         for(int i = 0; i < ar.length; i++)
         {
            Object[] m = ar[i];
            int ml = m.length;
            obj[i] = new Object[ml];
            System.arraycopy(m, 0, obj[i], 0, ml);
         }
         return new ArrayMatrix(obj);
      }
      public String toString()
      {
         String s = "";
         for (int r = 0; r < numRows(); r++)
         {
            for (int c = 0; c < numColumns(); c++)
            {
               anyType t = get(r,c);
               if (t != null) s+= t+" ";
               else s+=bl+" ";
            }
            s+="\n";
         }
         return s;
      }
}