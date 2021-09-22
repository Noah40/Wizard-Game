package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Portal extends GameObject{
    private final Game game;

    Animation anim;

    public Portal(float x, float y, ID id, PortalSheet ps,Game game) {
        super(x, y, id);
        this.game = game;
        BufferedImage[] portal_image = new BufferedImage[4];
        portal_image[0] = ps.grabImage(1,1,205,541);
        portal_image[1] = ps.grabImage(2,1,205,541);
        portal_image[2] = ps.grabImage(3,1,205,541);
        portal_image[3] = ps.grabImage(4,1,205,541);
        anim = new Animation(8, portal_image,0);

    }

    @Override
    public int getHp() {
        return 0;
    }

    @Override
    public void tick() {
anim.runAnimation();
    }

    @Override
    public void render(Graphics g) {
        if(game.kills >= game.requiredKills && !game.isBossLevel) {
            anim.drawAnimation(g, x, y, 32, 48, 0);
        }
        if(game.isBossLevel && game.hpBoss == 0){
            anim.drawAnimation(g, x, y, 32, 48, 0);

        }

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,32,48);
    }
}
