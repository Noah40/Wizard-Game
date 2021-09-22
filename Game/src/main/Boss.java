package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


public class Boss extends GameObject{
    private final Game game;
    private final Handler handler;
    private boolean damageAnim = false;
    private int amountDamage;
    private int b = 0;
    private final BufferedImage[] explosion_one = new BufferedImage[28];
    private final BufferedImage[] enemy_image = new BufferedImage[3];
    Animation anim1;
    Animation anim;
    int choose = 0;

    Random r = new Random();



    public Boss(float x, float y, ID id, SpriteSheet ss,Handler handler, Game game,ExplosionOne ex1,ExplosionTwo ex2,ExplosionThree ex3) {
        super(x, y, id);
        this.handler=handler;
        this.game = game;
        enemy_image[0] = ss.grabImage(4,1,32,32);
        enemy_image[1] = ss.grabImage(5,1,32,32);
        enemy_image[2] = ss.grabImage(6,1,32,32);
        anim = new Animation(3,enemy_image,0,this);

        explosion_one[0] = ex1.grabImage(1,1,64,150);
        explosion_one[1] = ex1.grabImage(2,1,64,150);
        explosion_one[2] = ex1.grabImage(3,1,64,150);
        explosion_one[3] = ex1.grabImage(4,1,64,150);
        explosion_one[4] = ex1.grabImage(5,1,64,150);
        explosion_one[5] = ex1.grabImage(6,1,64,150);
        explosion_one[6] = ex1.grabImage(7,1,64,150);
        explosion_one[7] = ex1.grabImage(8,1,64,150);
        explosion_one[8] = ex1.grabImage(9,1,64,150);
        explosion_one[9] = ex1.grabImage(10,1,64,150);
        explosion_one[10] =ex1.grabImage(11,1,64,150);
        explosion_one[11] =ex1.grabImage(12,1,64,150);

        explosion_one[12] = ex2.grabImage(1,1,64,150);
        explosion_one[13] = ex2.grabImage(2,1,64,150);
        explosion_one[14] = ex2.grabImage(3,1,64,150);
        explosion_one[15] = ex2.grabImage(4,1,64,150);
        explosion_one[16] = ex2.grabImage(5,1,64,150);
        explosion_one[17] = ex2.grabImage(6,1,64,150);
        explosion_one[18] = ex2.grabImage(7,1,64,150);
        explosion_one[19] = ex2.grabImage(8,1,64,150);

        explosion_one[20] = ex3.grabImage(1,1,64,160);
        explosion_one[21] = ex3.grabImage(2,1,64,160);
        explosion_one[22] = ex3.grabImage(3,1,64,160);
        explosion_one[23] = ex3.grabImage(4,1,64,160);
        explosion_one[24] = ex3.grabImage(5,1,64,160);
        explosion_one[25] = ex3.grabImage(6,1,64,160);
        explosion_one[26] = ex3.grabImage(7,1,64,160);
        explosion_one[27] = ex3.grabImage(8,1,64,160);

        anim1 = new Animation(0.5,explosion_one,28,this);

    }

    private void setDamage(int amount){
        if(damageAnim == true) {
            amountDamage += amount;
        } else {
            amountDamage = amount;
        }

    }
    private int getDamage(){
        return amountDamage;
    }

    @Override
    public int getHp() {
        return game.hpBoss;
    }

    @Override
    public void tick() {
        if(game.hpBoss != 0) {
            x += velX;
            y += velY;


            if(damageAnim == true && b < getDamage()){
                game.hpBoss -= 3;
                b+= 3;
                if(b == getDamage() ){

                    damageAnim = false;
                    b = 0;
                }
            }


            choose = r.nextInt(10);



            for (int i = 0; i < handler.object.size(); i++) {

                GameObject tempObject = handler.object.get(i);



                if (tempObject.getId() == ID.Block) {
                    if (getBoundsBig().intersects(tempObject.getBounds())) {
                        x += (velX * 5) * -1;
                        y += (velY * 5) * -1;
                        velX *= -1;
                        velY *= -1;

                    } else if (choose == 0) {
                        velX = (r.nextInt(4 +4)  -4);
                        velY = (r.nextInt(4 +4)  -4);
                    }
                }

                if (tempObject.getId() == ID.Bullet) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                   //     game.hpBoss-=35;
                        setDamage(35);
                        damageAnim = true;
                        handler.removeObject(tempObject);
                    }
                }
            }
            if (game.hpBoss < 0) {
                    game.hpBoss = 0;


            }

            anim.runAnimation();
            //   if (game.hpBoss<= 0) {
        //        anim1.runAnimation();
       //     }

        }

    }


    @Override
    public void render(Graphics g) {
        if (game.hpBoss != 0){
            anim.drawAnimation(g, (int) x, (int) y, 128, 128, 0);
    }
        if(game.hpBoss == 0){
      //      anim1.drawAnimation(g,(int) x,(int)y,200,240,0);
            g.drawImage(enemy_image[0],(int)x,(int)y,128,128,null );
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,128,128);
    }

    public Rectangle getBoundsBig(){

        return new Rectangle((int)x-48,(int)y-48,192,192);
    }
}
