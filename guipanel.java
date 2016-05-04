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
      setLayout(new GridLayout(r,c));
      gl = new guilife[r][c];
      for (int i = 0; i < gl.length; i++)
      {
         for (int j = 0; j < gl[0].length; j++)
         {
            gl[i][j] = new guilife();
            add(gl[i][j]);
         }
      }
   }
   @Override
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      Object[][] l = con.toArray();
      for(int r = 0; r < con.numRows(); r++)
      {
         for (int c = 0; c < con.numColumns(); c++)
         {
            if (l[r][c] != null) gl[r][c].live();
            else gl[r][c].die();
         }
      }
      //con.generation();
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
         else if (c == ' ') go = !go;
   }

}