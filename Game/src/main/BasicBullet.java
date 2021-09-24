package main;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BasicBullet extends GameObject{

    private final BufferedImage fireball;

    private final Handler handler;
    private int speed = 30;

    public BasicBullet(float x, float y, ID id, Handler handler, BufferedImage fireball) {
        super(x, y, id);
        this.handler = handler;
        this.fireball = fireball;

    }

    @Override
    public int getHp() {
        return 0;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        collision();




    }




@Override
    public void render(Graphics g) {

        g.drawImage(fireball,(int)x,(int)y,16,16,null);

    }


    private void collision(){


        for(int i = 0; i< handler.object.size();i++) {

            GameObject tempObject = handler.object.get(i);



        if (tempObject.getId() == ID.Block) {
            if (getBounds().intersects(tempObject.getBounds())) {
                handler.removeObject(this);

            }
        }
    }



    }

@Override
    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,8,8);
    }

}