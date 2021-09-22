package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends GameObject{


    private final BufferedImage block_image;
    public Block(float x, float y, ID id,SpriteSheet ss) {
        super(x, y, id);

        block_image = ss.grabImage(5,2,32,32);
    }


    @Override
    public int getHp() {
        return 0;
    }

    public void tick() {

    }


    public void render(Graphics g) {
        g.drawImage(block_image,(int)x,(int)y,null);

    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,32,32);
    }
}
