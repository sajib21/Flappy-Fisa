package FlappyFisa;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public  class customArrayList extends ArrayList<Rectangle> {
	
	public final int WIDTH = 800, HEIGHT = 800;
	
	Random random;
	
	public customArrayList() {
		super();
		random = new Random();
		
	}
	
	public void addColumn(boolean start) {
		int space = 300;
		int width = 100;
		int height = 50 + random.nextInt(300);

		if (start) {
			this.add(new Rectangle(WIDTH + width + size() * 300, HEIGHT - height - 120, width, height));
			this.add(new Rectangle(WIDTH + width + (size() - 1) * 300, 0, width, HEIGHT - height - space));
		}
		else {
			this.add(new Rectangle(get(size() - 1).x + 600, HEIGHT - height - 120, width, height));
			this.add(new Rectangle(get(size() - 1).x, 0, width, HEIGHT - height - space));
		}
	}

}
