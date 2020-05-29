import java.awt.Color;

public class Rectangle extends Shapes
	{
		private int width,height;
		public Rectangle(int x1, int y1, int type, Color color,int width, int height)
		{
			super(x1, y1, type, color);
			this.width=width;
			this.height=height;
		}
		
		public int getWidth()
		{
			return width;
		}
		
		public int getHeight()
		{
			return height;
		}
		
		public String toString()
		{
			int temp = 3;
			if(getColor() == Color.red) temp = 0;
			else if(getColor() == Color.blue) temp = 1;
			else if(getColor() == Color.green) temp = 2;
			return (getType()+" "+getX()+" "+getY()+" "+temp+" "+getWidth()+" "+getHeight());
		}
	}
