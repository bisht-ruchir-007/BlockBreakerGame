package BlockBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener,ActionListener {

	private static final long serialVersionUID = 1L;
	private boolean play = false;
	private int score =0;
	private int totalbricks = 21;
	private Timer timer;
	private int delay=8;

	private int PlayerX=310;
	private int BallposX = 100;
	private int BallposY = 300;
	private int BalldirX = -1;
	private int BalldirY = -2;

	private MapGenerator map;

	public GamePlay(){

		map = new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		timer = new Timer(delay,this);
		timer.start();
	}

	public void paint(Graphics g) {
		//background
		g.setColor(Color.BLACK);
		g.fillRect(1, 1, 692, 592);
		//blocks 
		map.draw((Graphics2D)g);
		//scores
		g.setColor(Color.WHITE);
		g.setFont(new Font("serf",Font.BOLD,25));
		g.drawString("Score : "+score, 550, 30);
		//border
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(691, 0, 3, 592);
		//paddle
		g.setColor(Color.GREEN);
		g.fillRect(PlayerX, 550, 100, 8);
		//ball
		g.setColor(Color.RED);
		g.fillOval(BallposX, BallposY, 20, 20);

		if(totalbricks <= 0) {
			play = false;
			BalldirX=0;
			BalldirY=0;
			g.setColor(Color.RED);
			g.setFont(new Font("serf",Font.BOLD,30));
			g.drawString("You WON !! Score : "+score, 160, 300);
			
			g.setFont(new Font("serf",Font.BOLD,25));
			g.drawString("Press ENTER to Restart", 210, 350);
			
		}
		
		if(BallposY > 570 ) {
			play = false;
			BalldirX=0;
			BalldirY=0;
			g.setColor(Color.RED);
			g.setFont(new Font("serf",Font.BOLD,30));
			g.drawString("GAME OVER !! Score : "+score, 160, 300);
			
			g.setFont(new Font("serf",Font.BOLD,25));
			g.drawString("Press ENTER to Restart", 210, 350);
		}
		g.dispose();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			if(new Rectangle(BallposX,BallposY,20,20).intersects(new Rectangle(PlayerX,550,100,8))) {
				BalldirY = -BalldirY;
			}
			A : for(int i = 0 ; i < map.map.length;i++) {
				for(int j = 0;j<map.map[0].length;j++) {
					if(map.map[i][j] > 0) {
						int brickX = map.brickW*j+80;
						int brickY = map.brickH*i+50;
						int brickWidth = map.brickW;
						int brickHeight = map.brickH;

						Rectangle rect =new Rectangle(brickX,brickY,brickWidth,brickHeight);
						Rectangle ballrect = new Rectangle(BallposX,BallposY,20,20);
						Rectangle brickrect = rect;

						if(ballrect.intersects(brickrect)) {
							map.setBrickValue(0, i, j);
							totalbricks --;
							score +=10;

							if(BallposX + 19 > brickrect.x || BallposX + 1 >brickrect.x+brickrect.width) {
								BalldirX = -BalldirX;
							}
							else {
								BalldirY = -BalldirY;
							}
							break A;
						}
					}
				}
			}

			BallposX += BalldirX;
			BallposY += BalldirY;

			//left
			if(BallposX < 0 ) {
				BalldirX = -BalldirX;
			}
			//top
			if(BallposY < 0 ) {
				BalldirY = -BalldirY;
			}
			//right
			if(BallposX > 670 ) {
				BalldirX = -BalldirX;
			}

		}
		repaint(); // re call the paint() function so as to produce the updated positions of different objects
	}

	@Override
	public void keyPressed(KeyEvent e){

		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(PlayerX >= 600) {
				PlayerX = 600;
			}
			else {
				moveright();
			}
		}

		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(PlayerX < 10) {
				PlayerX = 10;
			}
			else {
				moveleft();
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				BallposX=120;
				BallposY=350;
				BalldirX=-1;
				BalldirY=-2;
				PlayerX=310;
				score=0;
				totalbricks=21;
				map = new MapGenerator(3,7);
				
				repaint();
				
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	private void moveleft() {
		play = true;
		PlayerX -= 25;

	}

	private void moveright() {
		play = true;
		PlayerX += 25;

	}

}








