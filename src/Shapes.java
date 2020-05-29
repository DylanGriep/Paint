import java.awt.Color;

public class Shapes 
{
	private int x1,y1,type;
	private Color color;
	
	public Shapes(int x1,int y1,int type, Color color)
	{
		this.color=color;
		this.x1=x1;
		this.y1=y1;
		this.type=type;
	}
	
	public int getType()
	{
		return type;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public int getX()
	{
		return x1;
	}
	
	public int getY()
	{
		return y1;
	}
}