import java.awt.Color;

public class Line extends Shapes
{
	private int x2,y2;
	public Line(int x1, int y1, int type, Color color,int x2, int y2)
	{
		super(x1, y1, type, color);
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public int getX2()
	{
		return x2;
	}
	
	public int getY2()
	{
		return y2;
	}
	
	public String toString()
	{
		int temp = 3;
		if(getColor() == Color.red) temp = 0;
		else if(getColor() == Color.blue) temp = 1;
		else if(getColor() == Color.green) temp = 2;
		return (getType()+" "+getX()+" "+getY()+" "+temp+" "+x2+" "+y2);
	}
}
	