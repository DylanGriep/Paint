import java.awt.Color;

	public class Point extends Shapes
	{
		public Point(int x1, int y1, int type, Color color)
		{
			super(x1,y1,type,color);
		}
		
		public String toString()
		{
			int temp = 3;
			if(getColor() == Color.red) temp = 0;
			else if(getColor() == Color.blue) temp = 1;
			else if(getColor() == Color.green) temp = 2;
			return(getType()+" "+getX()+" "+getY()+" "+temp);
		}
	}