import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class guipanel extends JPanel
{
   private static Conways con;
   private Timer t;
   private static final int DELAY = 100;
   private static boolean go;
   private static byte size;           // size of a cell
   private static int playerR;			// start row for the player
   private static int playerC;			// start col for the player
   private boolean wrap;
   private boolean border;
   public guipanel()
   {
      int r = 50;
      int c = 50;
      size = 10;
      t = new Timer(DELAY, new Listener());
      con = new Conways(r,c);
      con.populate();
      go = false;
      addMouseListener(new mouselisten());
      wrap = true;
      border = false;
      t.start();
   }
   @Override
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      int x = 5;
      int y = 5;
      int n = 0;
      for(int r = 0; r < con.numRows(); r++)
      {
         x = 5;
         for (int c = 0; c < con.numColumns(); c++)
         {
            n = con.getNeighbors(r,c);
            if(con.get(r,c) != null)
            {
               if (n > 3) g.setColor(Color.RED);
               else g.setColor(Color.BLACK);
            }
            else 
            {
               if (n == 3) g.setColor(Color.GREEN);
               else g.setColor(Color.WHITE);
            }
            g.fillRect(x,y,size,size);
            if (border)
            {
               if (g.getColor() == Color.WHITE) g.setColor(Color.BLACK);
               else g.setColor(Color.WHITE);
               g.drawRect(x,y,size,size);
               g.setColor(Color.WHITE);
            }
            x+=size;
         }
         y+=size;
      }
      if (!wrap)
      {
         Graphics2D g2 = (Graphics2D)g;
         g2.setColor(Color.BLUE);
         g2.setStroke(new BasicStroke(5));
         g2.drawRect(5,5,size*con.numColumns()+5,size*con.numRows()+5);
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
         }
         else if (c == 'w')
         {
            go = false;
            String s = JOptionPane.showInputDialog("Write to File");
            concommon.toFile(con,s);
         }
         else if (c == 'p') wrap = con.wrap();
         else if (c == 'c') con.clear();
         else if (c == 'n')
         {
            go = false;
            con.populate();
            go = true;
         }
         else if (c == 's')
         {
            go = false;
            String s = JOptionPane.showInputDialog("New Dimensions");
            String[] z = s.split(" ");
            con = new Conways(Integer.parseInt(z[0]),Integer.parseInt(z[1]));
            con.populate();
         }
         else if (c == 'b') border = !border;
         // Shift doesn't matter
         else if (c == '=' || c == '+') size++;
         else if (c == '-' || c == '_') size--;
         else if (c == ' ') go = !go;
   }
   public static void processmouse(MouseEvent e)
   {
      int mouseR = (e.getY()/size);
      int mouseC = (e.getX()/size);
      
      if(mouseR >=0 && mouseC >= 0 && mouseR < con.numRows() && mouseC < con.numColumns())
      {
         if (e.getButton() == 1) con.set(mouseR,mouseC,new life());
         else if (e.getButton() == 3) con.set(mouseR,mouseC,null);
      }
   }
   public void mouseMoved(MouseEvent e)
   {
      int mouseR = (e.getY()/size);
      int mouseC = (e.getX()/size);
      if(mouseR >=0 && mouseC >= 0 && mouseR < con.numRows() && mouseC < con.numColumns())
      {
         playerR = mouseR;
         playerC = mouseC;
      }
   }
   public static class mouselisten implements MouseListener
   {
      public void mouseClicked(MouseEvent e) { }
      public void	mouseEntered(MouseEvent e) { }
      public void	mouseExited(MouseEvent e)  { }
      public void	mousePressed(MouseEvent e) { }
      public void	mouseReleased(MouseEvent e)
      {
         processmouse(e);
      }
   }

}