public class matrixentry implements Cloneable
{
   private int row = 0;
   private int col = 0;
   private Object obj;
   
   public matrixentry(int r,int c, Object o)
   {
      row = r;
      col = c;
      obj = o;
   }
   public matrixentry clone()
   {
      return new matrixentry(row,col,obj);
   }
   public int getrow() { return row; }
   public int getcol() { return col; }
   public Object getobj() { return obj; }
   public void setobj(Object o) { obj = o; }
}