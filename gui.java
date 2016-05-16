import javax.swing.JFrame;
import java.awt.event.*;
public class gui
{
   private static guipanel gp;
   public static void main(String[]args)
   {
      JFrame frame = new JFrame("(p)wrap(SPACE)start/stop(w/r)write/read file(c)clear(n)new(+/-)change size");
      frame.setSize(520,540);
      frame.setLocation(100, 50);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      gp = new guipanel();
      frame.setContentPane(gp);
      frame.addKeyListener(new keylisten());
      frame.setVisible(true);
   }
   public static class keylisten implements KeyListener 
   {
      public void keyTyped(KeyEvent e) { }
         
      public void keyPressed(KeyEvent e) { }
   
      public void keyReleased(KeyEvent e)
      {
         gp.processkeys(e.getKeyChar());
      }
   }
}