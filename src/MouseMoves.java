import javax.swing.*;
import java.awt.*;     
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.event.MouseInputAdapter;

public class MouseMoves extends JPanel
{
	private int prevX,prevY;
	private ArrayList<Shapes> build = new ArrayList<Shapes>();
	private ArrayList<Shapes> destroyed = new ArrayList<Shapes>();
	private ArrayList<String> info = new ArrayList<String>();
	private ArrayList<String> files = new ArrayList<String>();
	private int type;
   private Color color = Color.black;
   private int mouseX = 0, mouseY = 0, mouseSPX = 0, mouseSPY = 0;
   public MouseMoves()
   {
		MyListener myListener = new MyListener();
    	addMouseListener(myListener);
    	addMouseMotionListener(myListener);
   }
   
   public void undo()
   {
	   if(build.size() != 0)
	   {
		   destroyed.add(build.get(build.size()-1));
		   build.remove(build.size()-1);
	   }
   }
   
   public void redo()
   {
	   if(destroyed.size() != 0)
	   {
		   build.add(destroyed.get(destroyed.size()-1));
		   destroyed.remove(destroyed.size()-1);
	   }
   }
   
   public ArrayList<Shapes> getBuildList()
   {
	   return build;
   }
   
   public void setBuildList(ArrayList<Shapes> list)
   {
	   build = (ArrayList<Shapes>) list.clone();
   }
   
   public void setFileList(ArrayList<String> list)
   {
	   files = (ArrayList<String>) list.clone();
   }
   
   public ArrayList<String> getFileList()
   {
	   return files;
   }

   public void setType(int type)
   {
	   this.type = type;
   }
   
   public void setColor(Color color)
   {
	   this.color = color;
   }
   
   public void paintComponent (Graphics g)
   {
      super.paintComponent(g);
      for(int i=0;i<build.size();i++)
      {
    	  Shapes shape = build.get(i);
      g.setColor(shape.getColor());
      switch(shape.getType())
  		{
  			case 0:
  				g.drawLine(shape.getX(), shape.getY(), ((Line)shape).getX2(), ((Line)shape).getY2() );
  				break;
  			case 1:
  				g.fillOval(shape.getX(), shape.getY(), 30, 30);
  				break;
  			case 2:
  				g.drawLine(shape.getX(), shape.getY(),((Line)shape).getX2() , ((Line)shape).getY2());
  				break;
  			case 3:
  				g.drawRect(shape.getX(), shape.getY(), ((Rectangle)shape).getWidth(), ((Rectangle)shape).getHeight());
  				break;
  			case 4:
  				g.fillRect(shape.getX(), shape.getY(), ((FillRectangle)shape).getWidth(), ((FillRectangle)shape).getHeight());
  				break;
  			case 5:
  				g.drawOval(shape.getX(), shape.getY(), ((Oval)shape).getWidth(), ((Oval)shape).getHeight());
  				break;
  			case 6:
  				g.fillOval(shape.getX(), shape.getY(), ((FillOval)shape).getWidth(), ((FillOval)shape).getHeight());
  				break;
  			case 7:
  				g.fillOval(shape.getX(), shape.getY(), 10, 10);
  		}      
      }
   }

   public void showMouse(Color color)
   {
      this.color = color;
   }

   private class MyListener extends MouseInputAdapter 
   {
    	public void mousePressed(MouseEvent e) 
    	{	
        	mouseSPX = e.getX();
        	mouseSPY = e.getY();
        	prevX = e.getX();
        	prevY =  e.getY();
        
        	switch(type)
        	{
        	case 0:
        		Line pencil = new Line(mouseSPX,mouseSPY,0,color, mouseSPX, mouseSPY);
        		break;
        	case 1:
        		Oval erase = new Oval(mouseSPX-15,mouseSPY-15,1,color.white,50,50);
        		build.add(erase);
        		break;
        	case 2:
        		Line line = new Line( mouseSPX,  mouseSPY,  2, color, mouseSPX, mouseSPY);
          		build.add(line);
          		break;
        	case 3:
        		Rectangle rectangle = new Rectangle(mouseSPX, mouseSPY, 3, color, 0, 0);
        		build.add(rectangle);
        		break;
        	case 4:
        		FillRectangle fillRect = new FillRectangle(mouseSPX,mouseSPY,4,color,0,0);
        		build.add(fillRect);
        		break;
        	case 5:
        		Oval oval = new Oval(mouseSPX, mouseSPY, 5, color, 0,0);
        		build.add(oval);
        		break;
        	case 6:
        		FillOval fillOval = new FillOval(mouseSPX,mouseSPY,6,color,0,0);
        		build.add(fillOval);
        		break;
        	case 7:
        		Point point = new Point(mouseSPX-5,mouseSPY-5,7,color);
        		build.add(point);
        		break;
        	}
      		repaint();
    	}
    	 
    	public void mouseDragged(MouseEvent e) 
    	{
        	mouseX = e.getX();
        	mouseY = e.getY();
        	
        	switch(type)
        	{
        	case 0:
        		Line pencil = new Line(prevX, prevY, 0, color,mouseX, mouseY);
        		prevX = mouseX;
        		prevY = mouseY;
        		build.add(pencil);
        		break;
        	case 1:
        		Oval erase = new Oval(mouseX-15,mouseY-15,1,color.white,30,30);
        		build.add(erase);
        		break;
        	case 2:
        		Line line = new Line( mouseSPX,  mouseSPY,  2, color, mouseX, mouseY);
          		build.set(build.size()-1,line);
          		break;
        	case 3:
        		if(mouseX > mouseSPX && mouseY > mouseSPY)
        		{
        			Rectangle rectangle = new Rectangle(mouseSPX, mouseSPY,3,color,(mouseX-mouseSPX),(mouseY-mouseSPY));
        			build.set(build.size()-1, rectangle);
        		}
        		else if(mouseX < mouseSPX && mouseY < mouseSPY)
        		{
        			Rectangle rectangle = new Rectangle(mouseX,mouseY,3,color,(mouseSPX-mouseX),(mouseSPY-mouseY));
        			build.set(build.size()-1, rectangle);
        		}
        		else if(mouseX > mouseSPX && mouseY < mouseSPY)
        		{
        			Rectangle rectangle = new Rectangle(mouseSPX, mouseY,3,color,(mouseX-mouseSPX),(mouseSPY-mouseY) );
        			build.set(build.size()-1, rectangle);
        		}
        		else if(mouseX < mouseSPX && mouseY > mouseSPX)
        		{
        			Rectangle rectangle = new Rectangle(mouseX,mouseSPY,3,color,(mouseSPX-mouseX),(mouseY-mouseSPY));
        			build.set(build.size()-1, rectangle);
        		}
        		break;
        	case 4:
        		if(mouseX > mouseSPX && mouseY > mouseSPY)
        		{
        			FillRectangle rectangle = new FillRectangle(mouseSPX, mouseSPY,4,color,(mouseX-mouseSPX),(mouseY-mouseSPY));
        			build.set(build.size()-1, rectangle);
        		}
        		else if(mouseX < mouseSPX && mouseY < mouseSPY)
        		{
        			FillRectangle rectangle = new FillRectangle(mouseX,mouseY,4,color,(mouseSPX-mouseX),(mouseSPY-mouseY));
        			build.set(build.size()-1, rectangle);
        		}
        		else if(mouseX > mouseSPX && mouseY < mouseSPY)
        		{
        			FillRectangle rectangle = new FillRectangle(mouseSPX, mouseY,4,color,(mouseX-mouseSPX),(mouseSPY-mouseY) );
        			build.set(build.size()-1, rectangle);
        		}
        		else if(mouseX < mouseSPX && mouseY > mouseSPX)
        		{
        			FillRectangle rectangle = new FillRectangle(mouseX,mouseSPY,4,color,(mouseSPX-mouseX),(mouseY-mouseSPY));
        			build.set(build.size()-1, rectangle);
        		}
        		break;
        	case 5:
        		if(mouseX > mouseSPX && mouseY > mouseSPY)
        		{
        			Oval oval = new Oval(mouseSPX, mouseSPY,5,color,(mouseX-mouseSPX),(mouseY-mouseSPY));
        			build.set(build.size()-1, oval);
        		}
        		else if(mouseX < mouseSPX && mouseY < mouseSPY)
        		{
        			Oval oval = new Oval(mouseX,mouseY,5,color,(mouseSPX-mouseX),(mouseSPY-mouseY));
        			build.set(build.size()-1, oval);
        		}
        		else if(mouseX > mouseSPX && mouseY < mouseSPY)
        		{
        			Oval oval = new Oval(mouseSPX, mouseY,5,color,(mouseX-mouseSPX),(mouseSPY-mouseY) );
        			build.set(build.size()-1, oval);
        		}
        		else if(mouseX < mouseSPX && mouseY > mouseSPX)
        		{
        			Oval oval = new Oval(mouseX,mouseSPY,5,color,(mouseSPX-mouseX),(mouseY-mouseSPY));
        			build.set(build.size()-1, oval);
        		}
        		break;
        	case 6:
        		if(mouseX > mouseSPX && mouseY > mouseSPY)
        		{
        			FillOval oval = new FillOval(mouseSPX, mouseSPY,6,color,(mouseX-mouseSPX),(mouseY-mouseSPY));
        			build.set(build.size()-1, oval);
        		}
        		else if(mouseX < mouseSPX && mouseY < mouseSPY)
        		{
        			FillOval oval = new FillOval(mouseX,mouseY,6,color,(mouseSPX-mouseX),(mouseSPY-mouseY));
        			build.set(build.size()-1, oval);
        		}
        		else if(mouseX > mouseSPX && mouseY < mouseSPY)
        		{
        			FillOval oval = new FillOval(mouseSPX, mouseY,6,color,(mouseX-mouseSPX),(mouseSPY-mouseY) );
        			build.set(build.size()-1, oval);
        		}
        		else if(mouseX < mouseSPX && mouseY > mouseSPX)
        		{
        			FillOval oval = new FillOval(mouseX,mouseSPY,6,color,(mouseSPX-mouseX),(mouseY-mouseSPY));
        			build.set(build.size()-1, oval);
        		}
        		break;
        	case 7:
        		Point point = new Point(mouseSPX-5,mouseSPY-5,7,color);
        		build.set(build.size()-1, point);
        		break;
        	}
        	repaint();
    	}

    	public void mouseReleased(MouseEvent e) 
    	{
    		mouseX = e.getX();
        	mouseY = e.getY();
        	repaint();
    	}  	
   }
}