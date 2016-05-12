import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class guipanel extends JPanel
{
   private static Conways con;
   private static guilife[][] gl;
   private Timer t;
   private static final int DELAY = 100;
   private static boolean go;
   private static int size = 10; // Size of a cell
   private ImageIcon blank = new ImageIcon("blank.png");
   private ImageIcon black = new ImageIcon("black.png");
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
      for(int r = 0; r < con.numRows(); r++)
      {
         x = 0;
         for (int c = 0; c < con.numColumns(); c++)
         {
            if(l[r][c] != null) g.drawImage(black.getImage(),x,y,size,size,null);
            else g.drawImage(blank.getImage(),x,y,size,size,null);
            x+=size;
         }
         y+=size;
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
         else if (c == '+') size ++;
         else if (c == '-') size --;
         else if (c == ' ') go = !go;
   }
   public void processmouse(Point p,int b)
   {
      int mouseR = (int)(p.getY()/size);
      int mouseC = (int)(p.getX()/size);
      if (b == 1) con.set(mouseR,mouseC,new life());
      else if (b == 3) con.set(mouseR,mouseC,null);
      System.out.println(mouseR+" "+mouseC);
   }
   public void resized()
   {
   }
}