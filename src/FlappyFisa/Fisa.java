package FlappyFisa;

import java.awt.*;

public class Fisa extends Rectangle {
	
	public int yMotion;
	public boolean gameOver,started;
	
	public final static int WIDTH = 800;
	public final static int HEIGHT = 800;
	
	public Fisa() {
		super(WIDTH/2 - 10, HEIGHT/2 - 10, 20, 20);
	}
	
	
	public customArrayList jump(boolean gameOver, boolean started, customArrayList columns)
	{
		if(gameOver)
		{	
			//fisa = new Rectangle(WIDTH/2 - 10, HEIGHT/2 - 10, 20, 20);
			x = WIDTH/2 - 10; y = HEIGHT/2 - 10; width = height = 20;
			columns.clear();
			yMotion=0;
			//score=0;
			
			columns.addColumn(true);
			columns.addColumn(true);
			columns.addColumn(true);
			columns.addColumn(true);
			gameOver=false;
		}
		if(!started)
		{
			started=true;
		}
		else if(!gameOver){
			if(yMotion>0)
			{
				yMotion=0;
			}
			yMotion-=10;
		}
		
		return columns;

	}
	
	public Rectangle GetRect() {
		return new Rectangle(x,y, width,height);
	}

}
