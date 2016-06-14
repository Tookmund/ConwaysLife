import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class guipanel extends JPanel
{
   private static Conways con;
   private Timer t;
   private static final int DELAY = 100;  // How long (in milliseconds) to wait 
                                                // before redrawing the screen
   private static boolean go;             // Whether simulation should go
   private static byte size;              // Size of a cell
   private boolean border;                // Whether cells should have a border
   private boolean one;                   // Whether the simulation should go one step at a time
   private String rulefile;               // Name of rulefile
   private String message;                // Message to print to screen
   private byte mestime;                  // How long (in number of repaints) to display message
   private boolean matrix;                // Which backend to use (true for sparse, false for array)
   private byte viewmode;                 // How to render cells. 0 for color, 1 for allblack, 2 for gradients
   
   public guipanel()
   {
      int r = 50;
      int c = 50;
      size = 10;
      t = new Timer(DELAY, new Listener());
      con = new Conways(r,c,rulefile);
      con.populate();
      go = false;
      mouselisten m = new mouselisten();
      addMouseListener(m);
      addMouseMotionListener(m);
      border = false;
      one = false;
      matrix = true;
      viewmode = 0;
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
            if (viewmode == 0) // color
            {
               if(con.get(r,c) == null && viewmode == 0)
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
            }
            else if (viewmode == 1) // all black
            {
               if (con.get(r,c) != null) g.setColor(Color.BLACK);
               else g.setColor(Color.WHITE);
            }
            else if (viewmode == 2) // gradient
            {
               if (con.get(r,c) != null)
               {
                  int rgb = (250/8)*n;
                  g.setColor(new Color(rgb,rgb,rgb));
               }
               else g.setColor(Color.WHITE);
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
      // Where error messages should be placed
      y = 30;
      if (!con.getwrap())
      {
         Graphics2D g2 = (Graphics2D)g;
         g2.setColor(Color.BLUE);
         g2.setStroke(new BasicStroke(5));
         g2.drawRect(5,5,size*con.numColumns()+5,size*con.numRows()+5);
      }
      // Draw Controls
      g.setFont(new Font("Serif", Font.BOLD, 20));
      g.setColor(Color.BLACK);
      int mx = x+10;
      int my = y+20;
      String [] controls = { "Controls",
                             "space - Start/stop simulation",
                             "+/= - Increase size of board",
                             "-/_ - Decrease size of board",
                             "p - Disable/enable wraparound",
                             "c - Clear the board",
                             "w - Write board to file",
                             "r - Read board from file",
                             "n - Generate new random board",
                             "s - Resize board and regenerate",
                             "b - Toggle cell borders",
                             "./> - Advance one generation at a time",
                             ",/< - Automatically advance generations",
                             "e - Change ruleset",
                             "a - Switch backend",
                             "t - Toggle viewmode" };
      for (int i = 0; i < controls.length; i++)
      {
         g.drawString(controls[i],mx,my+(i*20));
      }
      if (mestime > 0)
      {
         mestime--;
         g.drawString(message,mx,y);
         if (mestime <= 0) message = null;
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
   // See README or controls variable for what it should do
   public void processkeys(char c)
   {
         if (c == 'r')
         {
            go = false;
            JFileChooser fc = new JFileChooser(new File("saves"));
            int rv = fc.showOpenDialog(this);
            String s;
            if (rv == JFileChooser.APPROVE_OPTION) s = "saves/"+fc.getSelectedFile().getName();
            else return;
            con = concommon.fromFile(s,con.numRows(),con.numColumns(),rulefile);
            if (con.error != null) 
            {
               setMessage(con.error);
               con.error = null;
            }
            repaint();
         }
         else if (c == 'w')
         {
            go = false;
            JFileChooser fc = new JFileChooser(new File("saves"));
            int rv = fc.showSaveDialog(this);
            String s;
            if (rv == JFileChooser.APPROVE_OPTION) s = "saves/"+fc.getSelectedFile().getName();
            else return;
            concommon.toFile(con,s);
            if (con.error != null) 
            {
               setMessage(con.error);
               con.error = null;
            }
         }
         else if (c == 'p') con.wrap();
         else if (c == 'c') con.clear();
         else if (c == 'n')
         {
            go = false;
            con.populate();
            repaint();
            go = true;
         }
         else if (c == 's')
         {
            go = false;
            String s = JOptionPane.showInputDialog("New Dimensions");
            String[] z = s.split(" ");
            con = new Conways(Integer.parseInt(z[0]),Integer.parseInt(z[1]),rulefile);
            con.populate();
            repaint();
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
            JFileChooser fc = new JFileChooser(new File("rulesets"));
            String newrulefile; 
            int rv = fc.showOpenDialog(this);
            if (rv == JFileChooser.APPROVE_OPTION) newrulefile = "rulesets/"+fc.getSelectedFile().getName();
            else return;
            con = new Conways(con.numRows(),con.numColumns(),newrulefile);
            if (con.rulesValid && newrulefile != null) rulefile = newrulefile;
            else 
            {
               setMessage("Invalid ruleset");
               rulefile = null;
            }
            con.populate();
            repaint();
         }
         else if (c == 'a')
         {
            go = false;
            matrix = !matrix;
            if (matrix) 
            {
               con.SparseMatrix();
               setMessage("SparseMatrix");
            }
            else 
            {
               con.ArrayMatrix();
               setMessage("ArrayMatrix");
            }
            con.populate();
            go = true;
         }
         else if (c == 't')
         {
            if (viewmode >= 2) viewmode = 0;
            else viewmode++;
            if (viewmode == 0) setMessage("Color");
            else if (viewmode == 1) setMessage("All Black");
            else if (viewmode == 2) setMessage("Gradient");
         }
   }
   // pre: e is the MouseEvent passed to a mouselistener
   // post: Adds a cell at the mouse location if left-clicked (1)
   // or removes the cell if right-clicked (3)
   public static void processmouse(MouseEvent e,int b)
   {
      int mouseR = (e.getY()/size);
      int mouseC = (e.getX()/size);
      
      if(mouseR >=0 && mouseC >= 0 && mouseR < con.numRows() && mouseC < con.numColumns())
      {
         if (b == 1) con.set(mouseR,mouseC,new life());
         else if (b == 3) con.set(mouseR,mouseC,null);
      }
   }
   // pre: m is a message to print to the screen
   // post: message and mestime are set to appropriate values
   private void setMessage(String m)
   {
      message = m;
      mestime = 20;
   }
   public static class mouselisten implements MouseListener,MouseMotionListener
   {
      // int to store mouse button
      private int mb = 0;
      public void mouseClicked(MouseEvent e) { }
      public void	mouseEntered(MouseEvent e) { }
      public void	mouseExited(MouseEvent e)  { }
      public void	mouseReleased(MouseEvent e)
      {
         mb = 0;
      }
      public void	mousePressed(MouseEvent e)
      {
         mb = e.getButton();
         processmouse(e,mb);
      }
      public void mouseMoved(MouseEvent e)  { }
      public void mouseDragged(MouseEvent e)
      {
         processmouse(e,mb);  
      }
   }

}