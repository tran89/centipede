package com.zetcode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.ArrayList;
import java.util.List;


public class Board extends JPanel implements ActionListener 
{

	private SpaceShip spaceship;
	private final int ICRAFT_X = 150;
	private final int ICRAFT_Y = 350;
	
	private int spiderX;
	private  int spiderY;
	private int spiderHealth;
	
	private int score;
	
	
    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 400;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 10;
    private final int RAND_POS = 29;
    private final int DELAY = 50;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
    private final int health[] = new int[ALL_DOTS];
    
    private int dots;
    private int apple_x;
    private int apple_y;
    
    //private int appleArr[][] = new int[30][30];
    private List<int[]> applenum = new ArrayList<int[]>();
    
   
    
    private boolean leftDirection[] = new boolean[ALL_DOTS];
    private boolean rightDirection[] = new boolean[ALL_DOTS];
    private boolean upDirection[] = new boolean[ALL_DOTS];
    private boolean downDirection[] = new boolean[ALL_DOTS];
    

    
    private boolean inGame = true;
    
    private int lives;
    private boolean lostLife = false;
    
    private int headArr[] = new int[ALL_DOTS];
    
    private boolean flip[] = new boolean[ALL_DOTS];
    private boolean mush[] = new boolean[ALL_DOTS];
    private boolean left_right[] = new boolean[ALL_DOTS];

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;
    
    private Image spider;

    public Board() {
        
        initBoard();
        initMushrooms();
    }
    private void initMushrooms(){
    	
    	int x;
    	int y;
    	int r;
    	int x_num;
    	boolean valid;
    	
    	
    	//int x_prev[] = new int[10];
    	List<Integer>x_prev = new ArrayList<Integer>();
    	List<Integer>x_temp = new ArrayList<Integer>();
    	
    	
    	
    	for(y = 10; y < 300; y+=10)
    	{
    		//x_prev = x_temp;
    		x_prev.clear();
    		for(int t:x_temp)
    		{
    			x_prev.add(t);
    		}
    		x_temp.clear();
    		
    		valid = true;
    		 x_num = (int) (Math.random() * 10);
    		 for(int k = 0; k < x_num; k++)
    		 {
			    	r = (int) (Math.random() * RAND_POS);
			         x = ((r * DOT_SIZE));
			        
			        if(x == 0 || x == 300)
			        {
			        	valid = false;
			        }
			        
			        for(int i:x_prev)
			        {
			        	//System.out.println(y+ " "+ x+":	"+ i);
			        	if(x == (i+10) || x == (i-10)) 
			        	{
			        		valid = false;
			        		//System.out.println(y+ "	"+ i + ": illegal :" + x);
			        	}
			        }
			        
			        for(int i:x_temp)
			        {
			        	
			        	if(x == i) 
			        	{
			        		valid = false;
			        	}
			        }
			        
			        
			        
			        
			        
			        if(valid)
			        {
			        	x_temp.add(x);
			        	int coords[] = new int[3];
			        	coords[0] = x;
			        	coords[1] = y;
			        	coords[2] = 3;
			        	applenum.add(coords);
			        }
    		 }
    		 
    		
    		 
    	}
    	
  
    	
    }
    private void initBoard() {
    	headArr[0] = 10;
        leftDirection[0] = false;
        rightDirection[0] = true;
        upDirection[0] = false;
        downDirection[0] = false;
        lives = 3;
        
        score = 0;
        
        spiderX = 150;
        spiderY = 150;
        spiderHealth = 2;
       // flip[0] = false;
        
        for(boolean i:flip)
        {
        	i = false;
        }
        mush[0] = false;
        left_right[0] = false;
        
        
        
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y);
        spaceship.setVisible(true);
        
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        
        initGame();
    }

    private void loadImages() {

        ImageIcon iid = new ImageIcon("src/dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/head.png");
        head = iih.getImage();
        
        ImageIcon iij = new ImageIcon("src/explosion.png");
        spider = iij.getImage();
    }

    private void initGame() {

        dots = 10;

        for (int z = 0; z < dots; z++) {
            x[z] = 10 - z * 10;
            y[z] = 0;
            health[z] = 2;
        }
        
        //locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    private void newCreature()
    {
    	boolean newCent = true;
    	for(int x:headArr)
    	{
    		if(x != -1)
    		{
    			 newCent = false;
    			 break;
    		}
    		
    		
    	}
    	
    	if(newCent)
    	{
    		score += 600;
    		System.out.println("killed centipede");
    	}
    	if(lostLife)
    	{
    		newCent = true;
    	}
    	if(newCent)
    	{
    		leftDirection[0] = false;
            rightDirection[0] = true;
            upDirection[0] = false;
            downDirection[0] = false;
    		
    		
    		for (int z = 0; z < dots; z++) {
                x[z] = 10 - z * 10;
                y[z] = 0;
                headArr[z] = 0;
                health[z] = 2;
    		}
    		headArr[0] = 1;
    	}
    	
    }
    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        
        if (inGame) {

            //g.drawImage(apple, apple_x, apple_y, this);

        	for(int[] pair:applenum)
        	{
        		
        		g.drawImage(apple, pair[0], pair[1], this);
        	}
        	
            for (int z = 0; z < dots; z++) {
                if (headArr[z] > 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else if(headArr[z] != -1){
                    g.drawImage(ball, x[z], y[z], this);
                }
            }
            
            if (spaceship.isVisible()) {
            	
                g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(),
                        this);
            }

            List<Missile> ms = spaceship.getMissiles();

            for (Missile missile : ms) {
                if (missile.isVisible()) {
                    g.drawImage(missile.getImage(), missile.getX(), 
                            missile.getY(), this);
                }
            }
            
            if(spiderHealth > 0)
            {
            	g.drawImage(spider, spiderX, spiderY, this);
            }
            
            
            String msg = "Lives: " + lives; 
            
            Font small = new Font("Helvetica", Font.BOLD, 10);
            FontMetrics metr = getFontMetrics(small);

            g.setColor(Color.white);
            g.setFont(small);
            g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) - 20, B_HEIGHT - 20);
            
            msg = "Score: " + score;
            g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) - 20, B_HEIGHT - 10);
            
            
            
            
            

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }        
    }

    private void gameOver(Graphics g) {
        
        String msg = "Game Over. Score : " + score;
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    private void checkApple() {

    	for (int z = 0; z < 10; z++) 
    	{	
	        //if ((x[z] == apple_x) && (y[z] == apple_y)) {
    		int collisionflank = 0;
    		
    		for(int[] k: applenum)
    		{
    			
    			if(lostLife)
    			{
    				k[2] = 3;
    			}
	    		if ((x[z] <= (k[0] + 10) && x[z] >= (k[0] - 10)) && (y[z] == k[1])) {
		            //dots++;
		            //locateApple();
		            
		            mush[z] = true;
		            collisionflank++;
		            
		            if(rightDirection[z] == true)
		            {
		            	left_right[z] = false;
		            	//System.out.println(left_right[z]);
		            	
		            }
		            else if(leftDirection[z] == true)
		            {
		            	left_right[z] = true;
		            	//System.out.println(left_right[z]);
		            }
		            else if(collisionflank == 2 && headArr[z] > 0)
		            {
		            	mush[z] = false;
		            }
		            else if(headArr[z]>0 && (x[z] >= B_WIDTH - DOT_SIZE || x[z] <= 0 + DOT_SIZE))
		            {
		            	flip[z] = false;
		            	
		            	if(x[z] <= 0 + DOT_SIZE) {
		            		left_right[z] = true;
		            	}
		            	else
		            	{
		            		left_right[z] = false;
		            	}
		            }
 		            else if(downDirection[z] && headArr[z] > 0) 
		            {
		            	left_right[z] = (x[z] == k[0] + 10);
		            	//System.out.println("BLEHHH");
		            }
		            
		            
		        }
    		}
    		
    	    if(downDirection[z] && !mush[z])
	        {
	        	if(x[z] >= B_WIDTH - DOT_SIZE)
	        	{
	        		change_direction(1,z);
	        	}
	        	else
	        	{
	        		change_direction(2,z);
	        	}
	        }
    		
    		
    		
    		
    		
    	}
    	
    	lostLife = false;
    	
    }

    private void move() {

        for (int z = dots-1; z > 0; z--) {
        	if(headArr[z] == 0)
        	{
	            x[z] = x[(z - 1)];
	            y[z] = y[(z - 1)];
	            
	            leftDirection[z] = leftDirection[z-1];
	            rightDirection[z] = rightDirection[z-1];
        	}
        }
        for(int i = 0; i < 10; i++)
        {	
        	if(headArr[i] > 0)
        	{
		        if (leftDirection[i]) {
		            x[i] -= DOT_SIZE;
		        }
		
		        if (rightDirection[i]) {
		            x[i] += DOT_SIZE;
		        }
		
		        if (upDirection[i]) {
		            y[i] -= DOT_SIZE;
		        }
		
		        if (downDirection[i]) {
		            y[i] += DOT_SIZE;
		        }
        	}
        }
    }

    private void checkCollision() {
    	
    	if(!(lives > 0))
    	{inGame = false;}

    	for (int z = 0; z < 10; z++) {

    		int x2 = spaceship.getX();
    		int y2 = spaceship.getY();
            if ((x2 >= x[z] - 5 && x2 <= x[z]) && (y2 >= y[z] - 10 && y2 <= y[z] +10) ) {
                lostLife = true;
                lives--;
                
                spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y);
            }
            
            else if((x2 >= spiderX - 5 && x2 <= spiderX) && (y2 >= spiderY - 10 && y2 <= spiderY +10) ) {
                lostLife = true;
                lives--;
                spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y);
            }
            
            
            
            
        }
        
    	for (int z = 0; z < 10; z++) {
        	
  
	        if (y[z] >= 310) {
	        	y[z] = 310;
	        	//System.out.println("barrier");
	            if(x[z] >= B_WIDTH - DOT_SIZE)
	            {
	            	change_direction(1,z);
	            	rightDirection[z] = false;
	            	downDirection[z] = false;
	            }
	            else if(x[z] < 0 + DOT_SIZE )
	            {
	            	change_direction(2,z);
	            	leftDirection[z] = false;
	            	downDirection[z] = false;
	            }
	        }
	        else if(headArr[z] > 0)
        	{
			        if (y[z] < 0) {
			            inGame = false;
			        }
			       
			        
			
			        else if (x[z] >= B_WIDTH - DOT_SIZE || (mush[z] && !left_right[z])) {
			            //inGame = false;
			        	if(!flip[z])
			        	{
			        		change_direction(4,z);
			        		flip[z] = true;
			        	
			        	}
			        	else
			        	{
			        		change_direction(1,z);
			        		flip[z] = false;
			        		mush[z] = false;
			        	}
			        }
			
			        else if (x[z] < 0 + DOT_SIZE || (mush[z] && left_right[z])) {
			            //inGame = false;
			            if(!flip[z])
			        	{
			        		change_direction(4,z);
			        		flip[z] = true;
			        	}
			        	else
			        	{
			        		change_direction(2,z);
			        		flip[z] = false;
			        		mush[z] = false;
			        	}
			        }
			        
			        
			    
			        
			        
			        if (!inGame) {
			            timer.stop();
			        }
        	}
    	}
    }
/*
    private void locateApple() {

        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }
*/
    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

        	updateShip();
        	//updateMissiles();
        	
            
            checkMissileCollisions();
            checkApple();
            updateMissiles();
            checkCollision();
            
            move();
            newCreature();
            moveSpider();
            
        }

        repaint();
    }
    
    private void updateShip() {

        if (spaceship.isVisible()) {
           
            spaceship.move();
        }
    }
    
    private void updateMissiles() {

        List<Missile> ms = spaceship.getMissiles();

        for (int i = 0; i < ms.size(); i++) {

            Missile m = ms.get(i);
            if(m.y <= 0)
            {
            	m.setVisible(false);
            }
            
            if (m.isVisible()) {
                m.move();
            } else {
                ms.remove(i);
                i--;
                //System.out.println("removed ");
            }
          
            	
        }
    }
    
    public void moveSpider()
    {
    	
    	 if(lostLife)
    	 {
    		 spiderX = 150;
    		 spiderY = 150;
    		 spiderHealth = 2;
    		 //lostLife = false;
    		 
    	 }
    	 int move = (int) (Math.random() * 4);
    	 switch (move) {
	         case 0:  spiderX += 10;
	                  break;
	         case 1:  spiderX -= 10;
	                  break;
	         case 2:  spiderY += 10;
	                  break;
	         case 3:  spiderY -= 10;
	                  break;
	     
    	 }
    	 
    	 
    	 if(spiderX < 20)
    	 {
    		 spiderX = 20;
    	 }
    	 if(spiderX > 280)
    	 {
    		 spiderX = 280;
    	 }
    	 
    	 if(spiderY < 20)
    	 {
    		 spiderY = 20;
    	 }
    	 
    	 if(spiderY > 370)
    	 {
    		 spiderY = 370;
    	 }
    	 
	  lostLife = false;
    	 
    }
    
    public void checkMissileCollisions() 
    {
    	int[] c = new int[2];

    	
    	
    	
    	List<Missile> ms = spaceship.getMissiles();
    	for (Missile m : ms) 
    	{
    		
    		if(spiderHealth > 0 && (spiderX >= m.getX() - 5 && spiderX <= m.getX()+5) && (spiderY >= m.getY() - 10 && spiderY <= m.getY() +10))
    		{
    			spiderHealth--;
    			score += 100;
    			System.out.println("hit spider");
    			if(spiderHealth == 0)
    			{
    				score += 500;
    				System.out.println("killed Spider");
    			}
    			m.setVisible(false);
    		}
    		
    		//System.out.println("size " + ms.size());
    		 for(int j = 0; j < applenum.size(); j++)
    		 {
    			 c = applenum.get(j);
    			 if((c[0] >= m.getX() - 5 && c[0] <= m.getX()+5) && (c[1] >= m.getY() - 10 && c[1] <= m.getY() +10))
    			 {
    				 c[2] = c[2] - 1;
    				 score += 1;
    				 
    				 System.out.println("hit mushroom");
    				 m.setVisible(false);
    				 //System.out.println("collision");
    				 
    				 if(c[2] <=0)
    				 {
    					 applenum.remove(j);
    					 score += 4;
    					 System.out.println("killed mushroom");
    					 
    				 }
    				 
    				 break;
    			 }
    		 }
    		
    		
        	 for(int i = 0; i < 10; i++)
        	 {
        		 if(headArr[i] > -1)
        		 {
	        		 if((x[i] >= m.getX() - 5 && x[i] <= m.getX()+5) && (y[i] >= m.getY() - 10 && y[i] <= m.getY() +10))
	        		 //if(x[i] == m.getX() && y[i] == m.getY())
        			 {
	        			//System.out.println("Collision with " + i);
	        			health[i] = health[i] - 1;
	        			score += 2;
	        			
	        			System.out.println("hit segment");
	        			m.setVisible(false);
	        			
	        			if(health[i]<1)
	        			{
	        				score += 3;
	        				System.out.println("killed segment");
		        			for(int z = i+1; z < 10; z++)
		        			{
		        				if(headArr[z] == 0)
		        				{
		        					headArr[z] = 1;
		        					z = 10;
		        				}
		        			}
		      
		                     int coords[] = new int[3];
		                     
		                     
		                     coords[0] = x[i];
		                     coords[1] = y[i];
		                     coords[2] = 3;
		                     
		                     if(!downDirection[i] && y[i] < 300)
		                     {
		                    	 applenum.add(coords);
		                     }
		                     
		                     headArr[i] = -1;
	        			}
	        			 
	        		 }
	        	
	        	    }
        	 }
    	 }
    }
    
    public void change_direction(int x, int hx) {

        if (x == 1) {
            leftDirection[hx] = true;
            upDirection[hx] = false;
            downDirection[hx] = false;
        }

        if (x == 2) {
            rightDirection[hx] = true;
            upDirection[hx] = false;
            downDirection[hx] = false;
        }

        if (x == 3) {
            upDirection[hx] = true;
            rightDirection[hx] = false;
            leftDirection[hx] = false;
        }

        if (x == 4) {
            downDirection[hx] = true;
            rightDirection[hx] = false;
            leftDirection[hx] = false;
        }
    }
    
    
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            spaceship.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            spaceship.keyPressed(e);
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//}
    
/*
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            
            if (key == KeyEvent.VK_LEFT) {
                headArr[4] = 4;
                headArr[0] = 5;
 
                int coords[] = new int[2];
                coords[0] = x[3];
                coords[1] = y[3];
                
                applenum.add(coords);
                
                headArr[3] = -1;
                //x[5] = 0;
                //y[5] = 0;
            }
            
            if (key == KeyEvent.VK_UP) {
            	 //headArr[4] = 4;
                 //headArr[0] = 5;
                 headArr[8] = 2;
                 
                 apple_x = x[7];
                 apple_y = y[7];
                 headArr[7] = -1;
            }
            if (key == KeyEvent.VK_UP) {
                
            }
            /*
            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
            
        }
    }*/
}
