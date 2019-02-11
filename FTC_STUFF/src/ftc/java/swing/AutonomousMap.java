package ftc.java.swing;

import java.util.*;
import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.awt.geom.Line2D;
import java.io.*;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;

public class AutonomousMap extends JFrame{
  static JLabel background;	
  static boolean startClicked = false;
  static double startX, finishX, startY, finishY, currentX, currentY;
  static double leg1, leg2, distance, trueDistance, rotations;
  static double ratio = (144.0/740.0);
  static double WHEEL_DIAM = 4.0;	
  static double WHEEL_CIRC = WHEEL_DIAM * Math.PI;
  
  public static void main(String[] args) {
	AutonomousMap aMap = new AutonomousMap();    
  }
  
  public AutonomousMap() {
	  setTitle("Autonomous Mapping");
	  setSize(760, 760);
	  setLayout(null);
	  setVisible(true);
      setLocationRelativeTo(null);
	  setResizable(false);
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  
	  try {
		setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:\\Users\\rkola\\Desktop\\eclipse\\FTC_STUFF\\src\\ftc\\java\\swing\\ftc2019.JPG")))));
		pack();
	  } 
	  
	  catch (Exception e) {
		e.printStackTrace();  
	  }

	  addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
			 if (startClicked == false) {
			   startClicked = true;
			   
			   startX = e.getX();
			   startY = e.getY();	 
			 }	
			 
			 else if (startClicked == true) {
			   startClicked = false;
			   
			   finishX = e.getX();			  
			   finishY = e.getY();
				  
			   leg1 = Math.abs(finishX-startX);
			   leg2 = Math.abs(finishY-startY);
			   distance = Math.hypot(leg1, leg2);
			   
			   trueDistance = (distance*ratio);
			   rotations = trueDistance/WHEEL_CIRC;
			   
			   trueDistance = Math.round(trueDistance*100.0)/100.0;
			   rotations = Math.round(rotations*100.0)/100.0;
			   repaint();
			   
			   try {
			    runDistanceNotification();
			   }
			   
			   catch (Exception ex) {
				 System.out.print("Distance: " + trueDistance + " inches, Rotations: " + rotations);   
			   }
			 }
			}

		});
  }
  
  public void paint(Graphics g) {  
    super.paint(g);
    Graphics2D g2 = (Graphics2D) g;
    int X1 = (int)(startX);
    int X2 = (int)(finishX);
    int Y1 = (int)(startY);
    int Y2 = (int)(finishY);
    
    g2.setColor(Color.GREEN);
    g2.setStroke(new BasicStroke(9));
    g2.drawLine(X1, Y1, X2, Y2);
  }
  
  public static void runDistanceNotification() throws AWTException, MalformedURLException {
  	if (SystemTray.isSupported()) {
          //Obtain only one instance of the SystemTray object
          SystemTray tray = SystemTray.getSystemTray();

          //If the icon is a file
          Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
          //Alternative (if the icon is on the classpath):
          //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

          TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
          //Let the system resize the image if needed
          trayIcon.setImageAutoSize(true);
          //Set tooltip text for the tray iconu
          trayIcon.setToolTip("System tray icon demo");
          tray.add(trayIcon);

          trayIcon.displayMessage("Distance: " + trueDistance + " inches, Rotations: " + rotations, "Autonomous Mapping", MessageType.INFO);
   
      } else {
          System.err.println("System tray not supported!");
      }	
  }
 }
