package FlappyFisa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.swing.*;

import com.sun.org.apache.xerces.internal.impl.dv.xs.Base64BinaryDV;



public class FlappyFisa implements ActionListener,MouseListener,KeyListener {

	public static FlappyFisa flappyFisa;

	public final int WIDTH = 800, HEIGHT = 800;
	public Renderer renderer;
	public int columnCount=0;
	public Rectangle fisa;

	public int highscore=0;
	public ArrayList<Rectangle> columns;

	public Random rand;

	public int ticks,yMotion,score;

	public boolean gameOver,started;

	JFrame jFrame = new JFrame();
	JFrame intro = new JFrame();
	JFrame end = new JFrame();
	public void jump()
	{
		if(gameOver)
		{	
			fisa = new Rectangle(WIDTH/2 - 10, HEIGHT/2 - 10, 20, 20);
			columns.clear();
			yMotion=0;
			score=0;
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
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

	}

	public FlappyFisa() throws IOException {


		jFrame.setSize(WIDTH, HEIGHT);
		end.setSize(WIDTH, HEIGHT);
		intro.setSize(WIDTH, HEIGHT);
		jFrame.addMouseListener(this);
		jFrame.addKeyListener(this);
		intro.addMouseListener(this);
		intro.addKeyListener(this);
		end.addMouseListener(this);
		end.addKeyListener(this);
		intro.setTitle("Welcome");

		Timer timer = new Timer(20, this);
		renderer  = new Renderer();
		jFrame.add(renderer);
		BufferedImage image = ImageIO.read(new File("preview.jpg"));
		jFrame.setIconImage(image);
		intro.setIconImage(image);
		end.setIconImage(image);
		intro.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("preview.jpg")))));
		end.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("end.jpg")))));
		rand = new Random();
		jFrame.setTitle("Flappy Fisa");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		end.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		intro.setVisible(true);



		fisa = new Rectangle(WIDTH/2 - 10, HEIGHT/2 - 10, 20, 20);

		columns = new ArrayList<Rectangle>();
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		timer.start(); 
	}



	public static void main(String[] args) throws IOException {

		flappyFisa = new FlappyFisa();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		int speed = 10;
		ticks++;
		if(started){
			for(int i=0; i<columns.size() ;i++)
			{
				Rectangle column= columns.get(i);
				column.x-=speed;
			}

			if(ticks%2==0 && yMotion < 15)
			{
				yMotion+=2;
			}
			for(int i=0; i<columns.size() ;i++)
			{
				Rectangle column= columns.get(i);
				if(column.x + column.width < 0 )
				{
					columns.remove(column);
					if(column.y==0) addColumn(false);
				}
			}
			fisa.y += yMotion;

			for(Rectangle column : columns)
			{	
				if(column.y==0 && fisa.x+fisa.width/2 > column.x+column.width/2 - 10 && fisa.x + fisa.width/2 < column.x+column.width/2+10)
				{
					score++;
				}
				if (column.intersects(fisa))
				{
					gameOver = true;

					if(fisa.x <= column.x)
					{
						fisa.x = column.x - fisa.width;
					}
					else{
						if(column.y!=0)
						{
							fisa.y = column.y - fisa.height;
						}
						else if(fisa.y < column.height){
							fisa.y = column.height;
						}
					}
				}
			}
			if(fisa.y > HEIGHT-120 || fisa.y<0) // TODO Error hote pare
			{
				gameOver=true;
			}
			if(fisa.y + yMotion >= HEIGHT-120)
			{
				fisa.y = HEIGHT - 120 - fisa.height;
			}
		}

		renderer.repaint();
	}


	public void repaint(Graphics g) {


		g.setColor(Color.cyan);
		g.fillRect(0, 0, WIDTH, 100);

		g.setColor(Color.white);
		g.fillRect(0, 100, WIDTH, 200);

		g.setColor(Color.cyan);
		g.fillRect(0, 200, WIDTH, 300);

		g.setColor(Color.white);
		g.fillRect(0, 300, WIDTH, 400);

		g.setColor(Color.cyan);
		g.fillRect(0, 400, WIDTH, 500);

		g.setColor(Color.white);
		g.fillRect(0, 500, WIDTH, 600);

		g.setColor(Color.cyan);
		g.fillRect(0, 600, WIDTH, 700);

		g.setColor(Color.yellow);
		g.fillRect(0, HEIGHT - 120, WIDTH, 120);

		g.setColor(Color.green);
		g.fillRect(0, HEIGHT - 120, WIDTH, 20);


		g.setColor(Color.RED);
		g.fillRect(fisa.x, fisa.y, fisa.width, fisa.height);

		g.setColor(Color.yellow);
		g.fillRect(fisa.x+4, fisa.y+4, fisa.width-8, fisa.height-8);

		g.setColor(Color.blue);
		g.fillRect(fisa.x+8, fisa.y+8, fisa.width-16, fisa.height-16);


		for(Rectangle column : columns)
		{	
			columnCount++;
			paintColumn(g, column,columnCount);
		}
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 100));

		if(!started)
		{	
			g.setColor(Color.black);
			g.drawString("Click to start!", 75, HEIGHT/2 - 50);
		}

		if(gameOver)
		{	
			g.setColor(Color.black);
			g.drawString(" Game Over", 100, HEIGHT/2 - 50);
			g.setColor(Color.red);
			g.setFont(new Font("Arial", 1, 30));
			g.drawString("               Press esc to quit playing", 100, HEIGHT - 600);
			g.setColor(Color.black);
			g.setFont(new Font("SansSerif", 1, 30));
			highscore=Math.max(highscore, score);
			g.drawString("                         Your score is: "+score, 100, HEIGHT-350);
			g.drawString("                     Your highscore is: "+highscore, 100, HEIGHT-250);

		}


		g.setColor(Color.black);
		if(!gameOver && started)
		{	
			g.drawString(String.valueOf(score),WIDTH/2 - 25,100);
		}
	}

	public void addColumn(boolean start) {
		int space = 300;
		int width = 100;
		int height = 50 + rand.nextInt(300);

		if (start) {
			columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));
		}
		else {
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
		}
	}

	public void paintColumn(Graphics g, Rectangle column,int count) {

		
		if(count%2==0){
			g.setColor(Color.green.darker());
			g.fillRect(column.x, column.y, column.width, column.height);
		}
		else{
			g.setColor(Color.red.darker());
			g.fillRect(column.x, column.y, column.width, column.height);
		}

	}



	@Override
	public void mouseClicked(MouseEvent e) {

		jump();

	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) 
			intro.setVisible(false);
		intro.dispose();
		jFrame.setVisible(true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			jump();
		}
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
		{	
			jFrame.dispose();
			end.setVisible(true);

		}
		if(e.getKeyCode()==KeyEvent.VK_E)
		{	
			end.dispose();
			jFrame.dispose();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
