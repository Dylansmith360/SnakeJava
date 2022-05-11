import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{
	//Screen and staging for full build
	static final int SCREEN_LENGTH =	600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_LENGTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int dotsEaten;
	int dotX;
	int dotY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;

	//Screen background
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_LENGTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
		
	// Gets the game started 
	public void startGame(){
		
		newDot();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	// GUI 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
	public void draw(Graphics g) {
		if(running){		
		/* grid for screen
		for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
			g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
			g.drawLine(0, i*UNIT_SIZE, SCREEN_LENGTH, i*UNIT_SIZE);
		}
		*/
		//Dot color and size
		g.setColor(Color.red);
		g.fillOval(dotX, dotY, UNIT_SIZE, UNIT_SIZE);
		//Snake color and size
		for(int i=0;i<bodyParts;i++) {
			g.setColor(Color.green);
			g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
		}}
		else {
			gameOver(g);
			}
	
		}
	// New dot
	public void newDot(){
		dotX = random.nextInt((int)(SCREEN_LENGTH/UNIT_SIZE))*UNIT_SIZE;
		dotY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	//Snake movement 
	public void move() {
		for(int i = bodyParts;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
	}
		switch(direction) {
		case 'U': //up
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D': //down
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L': //left
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R': //right
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	//Dot collision 
	public void checkDot() {
		if((x[0] == dotX)&&(y[0])==dotY) {
			bodyParts++;
			dotsEaten++;
			newDot();
		}
	}
	//other collision
	public void checkCollisions() {
		for(int i=bodyParts;i>0;i--) {
			if((x[0]==x[i])&&(y[0]==y[i])){
				running=false;
			}
	}
	if(x[0]>SCREEN_LENGTH) {
		running=false;
	}
	if(x[0] > SCREEN_LENGTH) {
		running = false;
	}
	if(y[0] < 0) {
		running = false;
	}
	if(y[0] > SCREEN_HEIGHT) {
		running = false;
	}
	if(!running) {
		timer.stop();
	}
}
	//Game over screen
	public void gameOver(Graphics g) {
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+dotsEaten, (SCREEN_LENGTH - metrics1.stringWidth("Score: "+dotsEaten))/2, g.getFont().getSize());
		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_LENGTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkDot();
			checkCollisions();
		}
		repaint();
	}
	//Keys for movement
	public class MyKeyAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
}