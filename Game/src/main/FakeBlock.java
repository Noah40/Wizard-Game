package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FakeBlock extends GameObject{
    Handler handler;
    private boolean draw = true;



    private final BufferedImage block_image;
    public FakeBlock(float x, float y, ID id,SpriteSheet ss,Handler handler) {
        super(x, y, id);
        this.handler = handler;

        block_image = ss.grabImage(5,2,32,32);
    }


    @Override
    public int getHp() {
        return 0;
    }

    public void tick() {
        for (int i = 0; i < handler.object.size(); i++) {

            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Player && getBounds().intersects(tempObject.getBounds())){
                draw = false;

            }
            if (tempObject.getId() == ID.Player && !getBounds().intersects(tempObject.getBounds())){
                draw = true;
            }


        }
    }


    public void render(Graphics g) {
        if (draw) {
            g.drawImage(block_image, (int) x, (int) y, null);
        }

    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,32,32);
    }

}
