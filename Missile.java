package com.zetcode;

public class Missile extends Sprite {

    private final int BOARD_WIDTH = 300;
    private final int MISSILE_SPEED = 20;

    public Missile(int x, int y) {
        super(x, y);

        initMissile();
    }
    
    private void initMissile() {
        
        loadImage("src/missile.png");
        getImageDimensions();        
    }

    public void move() {
        
        y -= MISSILE_SPEED;
        
        if (x > BOARD_WIDTH)
            visible = false;
    }
}