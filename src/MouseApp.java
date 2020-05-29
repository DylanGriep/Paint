import javax.swing.*;

import java.awt.*;      
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MouseApp extends JFrame
{
	private static ArrayList<String> stuff;
	private Boolean toolbarHide = false;
   private JMenuItem drawItem, openItem, saveItem,toggleToolbar,redItem,blueItem,greenItem,blackItem,customItem,undo,redo;
   private JMenuBar menubar;
   private JMenu drawMenu, toggleMenu,colorMenu;
   private JButton pencil,erase,line,point,rectangle,fillRectangle,oval,fillOval,saveButton,openButton;
   private JTextField colorText,saveText,openText,openTextFiles;
   private MouseMoves mousePanel;
   private JPanel toolPanel,savePanel,openPanel;
   private static MouseApp TOOLS,theGUI,SAVE,OPEN;
   private Color color = Color.black;

   public MouseApp(int i)
   {
	   switch(i)
	   {
	   //PaintWindow
	   case 0:
		   mousePanel = new MouseMoves();
		   drawMenu = new JMenu("File");
		   toggleMenu = new JMenu("Toggle...");
		   colorMenu = new JMenu("Colors");
		   redItem = new JMenuItem("Red");
		   blueItem = new JMenuItem("Blue");
		   greenItem = new JMenuItem("Green");
		   blackItem = new JMenuItem("Black");
		   openItem = new JMenuItem("Open");
		   saveItem = new JMenuItem("Save");
		   undo = new JMenuItem("Undo");
		   redo = new JMenuItem("Redo");
		   toggleToolbar = new JMenuItem("Toolbar Visibility");
		   
		   menubar = new JMenuBar();
		   menubar.add(drawMenu);
		   menubar.add(colorMenu);
		   menubar.add(toggleMenu);
		   drawMenu.add(openItem);
		   drawMenu.add(saveItem);
		   drawMenu.add(undo);
		   drawMenu.add(redo);
		   colorMenu.add(redItem);
		   colorMenu.add(blueItem);
		   colorMenu.add(greenItem);
		   colorMenu.add(blackItem);
		   toggleMenu.add(toggleToolbar);
		   setJMenuBar(menubar);
		   Container pane = getContentPane();
		   pane.add(mousePanel);
		   mousePanel.setBackground(Color.white);
		   
		   toggleToolbar.addActionListener( new togglerToolBar() );
		   saveItem.addActionListener( new saveItem() );
		   openItem.addActionListener( new openItem() );
		   redItem.addActionListener( new redColor() );
		   blueItem.addActionListener( new blueColor() );
		   greenItem.addActionListener( new greenColor() );
		   blackItem.addActionListener( new blackColor() );
		   redo.addActionListener( new Redo() );
		   undo.addActionListener( new Undo() );
		   break;
		   //Tools Window
	   case 1:
		   toolPanel = new JPanel(new GridLayout(5,2));
		   pencil = new JButton();
		   erase = new JButton();
		   point = new JButton();
		   line = new JButton();
		   rectangle = new JButton();
		   fillRectangle = new JButton();
		   oval = new JButton();
		   fillOval = new JButton();
		   
		   Icon pencilIcon = new ImageIcon(getClass().getResource("/resources/pencil.png"));
		   pencil.setIcon(pencilIcon);
		   Icon eraseIcon = new ImageIcon(getClass().getResource("/resources/eraser.png"));
		   erase.setIcon(eraseIcon);
		   Icon pointIcon = new ImageIcon(getClass().getResource("/resources/point.png"));
		   point.setIcon(pointIcon);
		   Icon lineIcon = new ImageIcon(getClass().getResource("/resources/line.png"));
		   line.setIcon(lineIcon);
		   Icon rectangleIcon = new ImageIcon(getClass().getResource("/resources/rectangle.png"));
		   rectangle.setIcon(rectangleIcon);
		   Icon fillRectangleIcon = new ImageIcon(getClass().getResource("/resources/rectangle_filled.png"));
		   fillRectangle.setIcon(fillRectangleIcon);
		   Icon ovalIcon = new ImageIcon(getClass().getResource("/resources/oval.png"));
		   oval.setIcon(ovalIcon);
		   Icon fillOvalIcon = new ImageIcon(getClass().getResource("/resources/oval_filled.png"));
		   fillOval.setIcon(fillOvalIcon);
		   
		   Container paneTwo = getContentPane();
		   paneTwo.add(toolPanel);
		   toolPanel.add(pencil);
		   toolPanel.add(erase);
		   toolPanel.add(point);
		   toolPanel.add(line);
		   toolPanel.add(rectangle);
		   toolPanel.add(fillRectangle);
		   toolPanel.add(oval);
		   toolPanel.add(fillOval);
		   
		   pencil.addActionListener( new pencilTool() );
		   erase.addActionListener( new eraseTool() );
		   point.addActionListener( new pointTool() );
		   rectangle.addActionListener( new rectangleTool() );
		   fillRectangle.addActionListener( new  fillRectangleTool() );
		   oval.addActionListener( new ovalTool() );
		   fillOval.addActionListener( new fillOvalTool() );
		   line.addActionListener(new lineTool() );
		   break;
	   case 2:
		   //Save window
		   savePanel = new JPanel(new GridLayout(2,1));
		   saveText = new JTextField("");
		   saveButton = new JButton("Save");
		   Container paneFour = getContentPane();
		   paneFour.add(savePanel);
		   savePanel.add(saveText);
		   savePanel.add(saveButton);
		   saveButton.addActionListener(new saveButton() );
		   break;
	   case 3:
		   //Open Window
		   openPanel = new JPanel(new GridLayout(3,1));
		   openText = new JTextField("");
		   openTextFiles = new JTextField("");
		   openButton = new JButton("Open");
		   //openTextFiles.setEditable(false);
		   Container paneFive = getContentPane();
		   paneFive.add(openPanel);
		   openPanel.add(openTextFiles);
		   openPanel.add(openText);
		   openPanel.add(openButton);
		   
		   openButton.addActionListener( new openButton() );
	   }
   }
   
   public static void main (String[] args)
   {
      theGUI = new MouseApp(0);
      TOOLS = new MouseApp(1);
      theGUI.setSize (750, 750);
      theGUI.setVisible (true);
      theGUI.setTitle("Paint");
      theGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      TOOLS.setSize(250,500);
      TOOLS.setLocation(750,0);
      TOOLS.setTitle("Toolbar");
      TOOLS.setResizable(false);
      TOOLS.setVisible(true);
   }
   
   public class togglerToolBar implements ActionListener
   {
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if(toolbarHide)
		{
			toolbarHide = false;
			TOOLS = new MouseApp(1);
			TOOLS.setSize(250,500);
		    TOOLS.setLocation(750,0);
		    TOOLS.setTitle("Toolbar");
		    TOOLS.setResizable(false);
		    TOOLS.setVisible(true);	
		}
		else if(!toolbarHide)
		{
			toolbarHide = true;
			TOOLS.dispose();	
		}
	}
   }
   
   public static class openItem implements ActionListener
   {
	   @Override
	   public void actionPerformed(ActionEvent arg0)
	   {
		   OPEN = new MouseApp(3);
		   OPEN.setSize(250, 250);
		   OPEN.setResizable(false);
		   OPEN.setVisible(true);
		   try 
		   {
			Scanner scan = new Scanner(new File("list.txt"));
			while(scan.hasNextLine())
			{
				theGUI.mousePanel.getFileList().add(scan.nextLine());
			}
			scan.close();
			} 
		   catch (FileNotFoundException e) 
		   {
			e.printStackTrace();
			}
		   for(int i=0;i<theGUI.mousePanel.getFileList().size();i++)
		   {
			   OPEN.openTextFiles.setText(OPEN.openTextFiles.getText()+theGUI.mousePanel.getFileList().get(i)+"\n");   
		   }
	   }
   }
   
   public static class openButton implements ActionListener
   {
	   @Override
	   public void actionPerformed(ActionEvent arg0)
	   {
		   if( !(OPEN.openText.getText().equals("")) )
		   {
			   if(theGUI.mousePanel.getFileList().contains(OPEN.openText.getText()))
			   {
				   try 
				   {
					Scanner scan = new Scanner(new File(OPEN.openText.getText()));
					theGUI.mousePanel.getBuildList().clear();
					while(scan.hasNextLine())
					{
						Scanner scanString = new Scanner(scan.nextLine());	
						int id = (scan.nextInt());
						int x,y,x2,y2,temp,height,width;
						Color realColor;
						switch(id)
						{
						case 0:
							//pencil
							x = (scan.nextInt());
							y = (scan.nextInt());
							temp = (scan.nextInt());
							if(temp ==  0) realColor = Color.red;
							else if(temp == 1) realColor = Color.blue;
							else if(temp == 2)realColor = Color.green;
							else realColor = Color.black;
							x2 = (scan.nextInt());
							y2 = (scan.nextInt());
							Line pencil = new Line(x, y, 0, realColor, x2, y2);
							theGUI.mousePanel.getBuildList().add(pencil);
							break;
						case 1:
							//erase
							x = (scan.nextInt());
							y = (scan.nextInt());
							temp = (scan.nextInt());
							temp = (scan.nextInt());
							if(temp ==  0) realColor = Color.red;
							else if(temp == 1) realColor = Color.blue;
							else if(temp == 2)realColor = Color.green;
							else realColor = Color.black;
							width = (scan.nextInt());
							height = (scan.nextInt());
							Oval eraser = new Oval(x,y,1, realColor, width, height);
							theGUI.mousePanel.getBuildList().add(eraser);
							break;
						case 2:
							//line
							x = (scan.nextInt());
							y = (scan.nextInt());
							temp = (scan.nextInt());
							if(temp ==  0) realColor = Color.red;
							else if(temp == 1) realColor = Color.blue;
							else if(temp == 2)realColor = Color.green;
							else realColor = Color.black;
							x2 = (scan.nextInt());
							y2 = (scan.nextInt());
							Line line = new Line(x, y, 2, realColor, x2, y2);
							theGUI.mousePanel.getBuildList().add(line);
							break;
						case 3:
							//rectangle
							//getType()+" "+getX()+" "+getY()+" "+temp+" "+getWidth()+" "+getHeight())
							x = (scan.nextInt());
							y = (scan.nextInt());
							temp = (scan.nextInt());
							temp = (scan.nextInt());
							if(temp ==  0) realColor = Color.red;
							else if(temp == 1) realColor = Color.blue;
							else if(temp == 2)realColor = Color.green;
							else realColor = Color.black;
							width = (scan.nextInt());
							height = (scan.nextInt());
							Rectangle rectangle = new Rectangle(x,y,3, realColor, width, height);
							theGUI.mousePanel.getBuildList().add(rectangle);
							break;
						case 4:
							//fillrectangle
							x = (scan.nextInt());
							y = (scan.nextInt());
							temp = (scan.nextInt());
							temp = (scan.nextInt());
							if(temp ==  0) realColor = Color.red;
							else if(temp == 1) realColor = Color.blue;
							else if(temp == 2)realColor = Color.green;
							else realColor = Color.black;
							width = (scan.nextInt());
							height = (scan.nextInt());
							FillRectangle fillRectangle = new FillRectangle(x,y,4, realColor, width, height);
							theGUI.mousePanel.getBuildList().add(fillRectangle);
							break;
						case 5:
							//oval
							//getType()+" "+getX()+" "+getY()+" "+temp+" "+getWidth()+" "+getHeight()
							x = (scan.nextInt());
							y = (scan.nextInt());
							temp = (scan.nextInt());
							temp = (scan.nextInt());
							if(temp ==  0) realColor = Color.red;
							else if(temp == 1) realColor = Color.blue;
							else if(temp == 2)realColor = Color.green;
							else realColor = Color.black;
							width = (scan.nextInt());
							height = (scan.nextInt());
							Oval oval = new Oval(x,y,5, realColor, width, height);
							theGUI.mousePanel.getBuildList().add(oval);
							break;
						case 6:
							//filloval
							x = (scan.nextInt());
							y = (scan.nextInt());
							temp = (scan.nextInt());
							temp = (scan.nextInt());
							if(temp ==  0) realColor = Color.red;
							else if(temp == 1) realColor = Color.blue;
							else if(temp == 2)realColor = Color.green;
							else realColor = Color.black;
							width = (scan.nextInt());
							height = (scan.nextInt());
							FillOval fillOval = new FillOval(x,y,6, realColor, width, height);
							theGUI.mousePanel.getBuildList().add(fillOval);
							break;
						case 7:
							//point
							//return(getType()+" "+getX()+" "+getY()+" "+temp);
							x = (scan.nextInt());
							y = (scan.nextInt());
							temp = (scan.nextInt());
							temp = (scan.nextInt());
							if(temp ==  0) realColor = Color.red;
							else if(temp == 1) realColor = Color.blue;
							else if(temp == 2)realColor = Color.green;
							else realColor = Color.black;
							Point point = new Point(x,y,7,realColor);
							theGUI.mousePanel.getBuildList().add(point);
							break;
						}
						
						//scan
					}
					scan.close();
					theGUI.mousePanel.repaint();
					
					}
				   catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				}
			   }
		   }
	   }
   }
   
   public static class saveItem implements ActionListener
   {
	   @Override
	   public void actionPerformed(ActionEvent arg0)
	   {
		   SAVE = new MouseApp(2);
		   SAVE.setSize(200, 200);
		   SAVE.setResizable(false);
		   SAVE.setVisible(true);
	   }
   }
   
   
   public static class saveButton implements ActionListener
   {
	   @Override
	   public void actionPerformed(ActionEvent arg0)
	   {
		   if(!SAVE.saveText.getText().equals(""))
		   {
			   try
				{
					PrintWriter out = new PrintWriter(SAVE.saveText.getText()+".txt");
					for(int i=0;i<theGUI.mousePanel.getBuildList().size();i++)
					{
						out.println(theGUI.mousePanel.getBuildList().get(i).toString());
					}
					out.close();
					
					Scanner scan = new Scanner(new File("list.txt"));
					ArrayList<String> temp = new ArrayList<String>();
					while(scan.hasNext())
					{
						temp.add(scan.next());
					}
					temp.add(SAVE.saveText.getText()+".txt");
					scan.close();
					theGUI.mousePanel.setFileList(temp);
					PrintWriter write = new PrintWriter("list.txt");
					for(int i=0;i<temp.size();i++)
					{
						write.println(temp.get(i));
					}
					write.close();
				}
				catch(IOException e)
				{
					System.out.println(e);
				}
			   SAVE.dispose();
		   }
	   }
   }   
   
   public static class redColor implements ActionListener
   {
	   @Override
	   public void actionPerformed(ActionEvent arg0)
	   {
		   theGUI.mousePanel.setColor(Color.red);
	   }
   }
   
   public static class blueColor implements ActionListener
   {
	   @Override
	   public void actionPerformed(ActionEvent arg0)
	   {
		   theGUI.mousePanel.setColor(Color.blue);
	   }
   }
   
   public static class greenColor implements ActionListener
   {
	   @Override
	   public void actionPerformed(ActionEvent arg0)
	   {
		   theGUI.mousePanel.setColor(Color.green);
	   }
   }
   
   public static class blackColor implements ActionListener
   {
	   @Override
	   public void actionPerformed(ActionEvent arg0)
	   {
		   theGUI.mousePanel.setColor(Color.black);
	   }
   }
   
   public static class pencilTool implements ActionListener
   {
	   @Override
	   public void actionPerformed(ActionEvent arg0)
	   {
		   theGUI.mousePanel.setType(0);
	   }
   }
   
   public static class eraseTool implements ActionListener
   {
	   @Override
	   public void actionPerformed(ActionEvent arg0)
	   {
		   theGUI.mousePanel.setType(1);
	   }
   }
   
   public static class pointTool implements ActionListener
   {
	   @Override
	   public void actionPerformed(ActionEvent arg0)
	   {
		   theGUI.mousePanel.setType(7);
	   }
   }
   
   public static class lineTool implements ActionListener
   {
	   @Override
	   public void actionPerformed(ActionEvent arg0)
	   {
		   theGUI.mousePanel.setType(2);
	   }
   }
   
   public static class rectangleTool implements ActionListener
   {
	   @Override
	   public void actionPerformed(ActionEvent arg0)
	   {
		   theGUI.mousePanel.setType(3);
	   }
   }
   public static class fillRectangleTool implements ActionListener
   {
	   @Override
	   public void actionPerformed(ActionEvent arg0)
	   {
		   theGUI.mousePanel.setType(4);
	   }
   }
   
   public static class ovalTool implements ActionListener
   {
	   @Override
	   public void actionPerformed(ActionEvent arg0)
	   {
		   theGUI.mousePanel.setType(5);
	   }
   }
   
   public static class fillOvalTool implements ActionListener
   {
	   @Override
	   public void actionPerformed(ActionEvent arg0)
	   {
		   theGUI.mousePanel.setType(6);
	   }
   }
   
   public static class Undo implements ActionListener
   {
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		theGUI.mousePanel.undo();
		theGUI.mousePanel.repaint();
	}
   }
   
   public static class Redo implements ActionListener
   {
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		theGUI.mousePanel.redo();
		theGUI.mousePanel.repaint();
	}
   }
}