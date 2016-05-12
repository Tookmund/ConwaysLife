import javax.swing.JFrame;
import java.awt.event.*;
public class gui
{
   private static guipanel gp;
   public static void main(String[]args)
   {
      JFrame frame = new JFrame("Conway's Game of Life");
      frame.setSize(450, 250);
      frame.setLocation(100, 50);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      gp = new guipanel();
      frame.setContentPane(gp);
      frame.addKeyListener(new keylisten());
      frame.addMouseListener(new mouselisten());
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
   public static class mouselisten implements MouseListener
   {
      public void mouseClicked(MouseEvent e) { }
      public void	mouseEntered(MouseEvent e) { }
      public void	mouseExited(MouseEvent e)  { }
      public void	mousePressed(MouseEvent e) { }
      public void	mouseReleased(MouseEvent e)
      {
         gp.processmouse(e.getPoint(),e.getButton());
      }
   }
   public static class componentlisten implements ComponentListener
   {
      public void	componentHidden(ComponentEvent e) { }
      public void	componentMoved(ComponentEvent e)  { }
      public void	componentResized(ComponentEvent e)
      {
         gp.resized();
      }
      public void	componentShown(ComponentEvent e)  { }
   }

}