import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class guipanel extends JPanel
{
   private static Conways con;
   private Timer t;
   private static final int DELAY = 100;  // How long (in milliseconds) to wait 
                                                // before redrawing the screen
   private static boolean go;             // Whether simulation should go
   private static byte size;              // Size of a cell
   private boolean wrap;                  // Whether wraparound is enabled
   private boolean border;                // Whether cells should have a border
   private boolean one;                   // Whether the simulation should go one step at a time
   private String rulefile;               // Name of rulefile
   
   public guipanel()
   {
      int r = 50;
      int c = 50;
      size = 10;
      t = new Timer(DELAY, new Listener());
      con = new Conways(r,c,rulefile);
      con.populate();
      go = false;
      addMouseListener(new mouselisten());
      wrap = true;
      border = false;
      one = false;
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
            n = con.livingNeighbors(r,c);
            if(con.get(r,c) == null)
            {
               if(con.isBorn(n))
               {
                  g.setColor(Color.GREEN);
               }
               else g.setColor(Color.WHITE);
            }
            else 
            {
               if(con.isSurvive(n))
               {
                  g.setColor(Color.BLACK);
               }
               else g.setColor(Color.RED);
            }
            g.fillRect(x,y,size,size);
            if (border)
            {
               if (g.getColor() == Color.WHITE) g.setColor(Color.BLACK);
               else g.setColor(Color.WHITE);
               g.drawRect(x,y,size,size);
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
   // pre: c is the char of a key that has been pressed
   // post: Acts upon the keypress
   // See README for what it should do
   public void processkeys(char c)
   {
         if (c == 'r')
         {
            go = false;
            String s = JOptionPane.showInputDialog("Read from File");
            wrap = true;
            con = concommon.fromFile(s,con.numRows(),con.numColumns(),rulefile);
            repaint();
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
            wrap = true;
            String s = JOptionPane.showInputDialog("New Dimensions");
            String[] z = s.split(" ");
            con = new Conways(Integer.parseInt(z[0]),Integer.parseInt(z[1]),rulefile);
            con.populate();
         }
         else if (c == 'b') border = !border;
         // Shift doesn't matter
         else if (c == '=' || c == '+') size++;
         else if (c == '-' || c == '_') size--;
         else if (c == '.' || c == '>')
         {
            go = false;
            one = true;
         }
         else if (c == ',' || c == '<') 
         {
            one = false;
            go = true;
         }
         else if (c == ' ')
         {
            if (!one) go = !go;
            else con.generation();
         }
         else if (c == 'e')
         {
            rulefile = JOptionPane.showInputDialog("Ruleset File");
            con = new Conways(con.numRows(),con.numColumns(),rulefile);
            con.populate();
         }
   }
   // pre: e is the MouseEvent passed to a mouselistener
   // post: Adds a cell at the mouse location if left-clicked (1)
   // or removes the cell if right-clicked (3)
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