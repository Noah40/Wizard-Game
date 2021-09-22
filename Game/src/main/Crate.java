package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Crate extends GameObject{
    private final BufferedImage crate_image;
    public Crate(float x, float y, ID id,SpriteSheet ss) {
        super(x, y, id);
        crate_image = ss.grabImage(6,2,32,32);
    }

    @Override
    public int getHp() {
        return 0;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(crate_image,(int)x,(int)y,null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,32,32);
    }
}
