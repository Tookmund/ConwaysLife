import javax.swing.JPanel;
import java.awt.*;
public class guilife extends JPanel
{
   boolean living = false;
   public void live() { living = true; }
   public void die() { living = false; }   
   
   public guilife()
   {
      super();
      setOpaque(true);
      setBackground(Color.WHITE);
   }
   @Override
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);      
      if (living) setBackground(Color.BLACK);
      else setBackground(Color.WHITE);
   }
   public void clicked()
   {
      living = !living;
   }
}