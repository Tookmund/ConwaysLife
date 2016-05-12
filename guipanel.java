import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class guipanel extends JPanel
{
   private static Conways con;
   private Timer t;
   private static final int DELAY = 100;
   private static boolean go;
   private static Dimension size = new Dimension(10,10); // Size of a cell
   public guipanel()
   {
      int r = 50;
      int c = 50;
      t = new Timer(DELAY, new Listener());
      con = new Conways(r,c);
      con.populate();
      setup(r,c);
      go = false;
      t.start();
   }
   private void setup(int r, int c)
   {
   
   }
   @Override
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      Object[][] l = con.toArray();
      int x = 0;
      int y = 0;
      int n = 0;
      for(int r = 0; r < con.numRows(); r++)
      {
         x = 0;
         for (int c = 0; c < con.numColumns(); c++)
         {
            n = con.getNeighbors(r,c);
            if(l[r][c] != null) 
            {
               if (n > 3) g.setColor(Color.RED);
               else g.setColor(Color.BLACK);
            }
            else 
            {
               if (n == 3) g.setColor(Color.GREEN);
               else g.setColor(Color.WHITE);
            }
            g.fillRect(x,y,size.width,size.height);
            x+=size.width;
         }
         y+=size.height;
      }
   }
   private class Listener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         if (go) con.generation();
         repaint();
      }
   }
   public void processkeys(char c)
   {
         if (c == 'r')
         {
            go = false;
            String s = JOptionPane.showInputDialog("Read from File");
            removeAll();
            revalidate();
            repaint();
            con = null;
            con = concommon.fromFile(s);
            setup(con.numRows(),con.numColumns());
         }
         else if (c == 'w')
         {
            go = false;
            String s = JOptionPane.showInputDialog("Write to File");
            concommon.toFile(con,s);
         }
         else if (c == 'p') con.wrap();
         // Add shift to change Y
         else if (c == '=') size.width++;
         else if (c == '+') size.height++;
         else if (c == '-') size.width--;
         else if (c == '_') size.height--;
         
         else if (c == ' ') go = !go;
   }
   public void processmouse(Point p,int b)
   {
      int mouseR = p.y/size.height;
      int mouseC = p.x/size.width;
      if (b == 1) con.set(mouseR,mouseC,new life());
      else if (b == 3) con.set(mouseR,mouseC,null);
      System.out.println(mouseR+" "+mouseC);
   }
   public void resized()
   {
   }
}