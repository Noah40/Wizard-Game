package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject{

    private boolean j = true;
    private final KeyInput input;
    private boolean damageAnim = false;
    private int b = 0;
    private int amountDamage = 0;

    private boolean can_be_damaged = true;
    private long timerDamage = 0;
    Handler handler;
    Game game;
    private final BufferedImage[] wizard_image = new BufferedImage[3];
    Animation anim;

    public Player(float x, float y, ID id,KeyInput input,Handler handler,Game game,SpriteSheet ss) {
        super(x, y, id);
        this.handler = handler;
        this.input = input;
        this.game = game;
        wizard_image[0] = ss.grabImage(1,1,32,48);
        wizard_image[1] = ss.grabImage(2,1,32,48);
        wizard_image[2] = ss.grabImage(3,1,32,48);
        anim = new Animation(3,wizard_image,0);



    }



private int getDamage(){

        return amountDamage;
}
private void setDamage(int amount){
        amountDamage = amount;

}

    @Override
    public int getHp() {
        return 0;
    }

    public void tick() {
        x += velX;
        y += velY;

        if(input.keys[4]){
            game.kills++;
        }

        if(damageAnim && b < getDamage()){
            game.hpPlayer--;
            b++;
            if(b == getDamage() ){

                damageAnim = false;
                b = 0;
            }
        }

        if(!can_be_damaged){

            if(timerDamage >= 66){
                can_be_damaged = true;
                timerDamage = 0;
            }
            timerDamage++;


        }

       collision();






            //Horizontal


        float _acc = 1f;
        float _dcc = 0.5f;
        if (input.keys[0] ) velX += _acc;
            else if (input.keys[1]) velX -= _acc;
            else if (!input.keys[0] && !input.keys[1]) {

                if (velX > 0) velX -= _dcc;
                else if (velX < 0) velX += _dcc;

            }


        //Vertical
        if(input.keys[2]) velY -= _acc;
        else if(input.keys[3]) velY += _acc;
        else if(!input.keys[2] && !input.keys[3]) {

            if (velY > 0) velY -= _dcc;
            else if (velY < 0) velY += _dcc;
        }

        velX = clamp(velX,5,-5);
        velY = clamp(velY,5,-5);
        anim.runAnimation();

    }

    private float clamp(float value,float max, float min){
        if (value>max) value = max;
        else if (value <= min) value = min;

        return value;
    }
    long timer = System.currentTimeMillis();

    private void collision(){

        for(int i = 0; i< handler.object.size();i++){

            GameObject tempObject = handler.object.get(i);


            if(tempObject.getId() == ID.Zombie ){
                if(getBounds().intersects(tempObject.getBounds()) && can_be_damaged && tempObject.getHp() !=0){
                    i = 0;
                    damageAnim = true;
                    setDamage(15);


                    //    game.hp -= 15;
                    can_be_damaged = false;
                }
            }

            if(tempObject.getId() == ID.Boss && game.hpBoss != 0){
                if(getBounds().intersects(tempObject.getBounds()) && can_be_damaged){
                    i = 0;
                    damageAnim = true;
                    setDamage(30);


                    //    game.hp -= 15;
                    can_be_damaged = false;
                }
            }


            if(tempObject.getId() == ID.Block){
                if(getBounds().intersects(tempObject.getBounds())){
                    x += velX * -1;
                    y += velY *-1;

                }
            }

            if(tempObject.getId() == ID.Crate){
                if(getBounds().intersects(tempObject.getBounds())){
                    game.ammo += 10;
                    handler.removeObject(tempObject);

                }
            }




            if(tempObject.getId() == ID.Portal && j && game.kills >= game.requiredKills || tempObject.getId() == ID.Portal && j && game.hpBoss == 0 ){
                if(getBounds().intersects(tempObject.getBounds())){
                    handler.removeObject(this);
                    handler.removeObject(tempObject);
                    game.levelNum++;
                    j = false;


                }
            }




        }


    }

    public void render(Graphics g) {
        if(can_be_damaged) {
            if (velX == 0 && velY == 0) {
                g.drawImage(wizard_image[0], (int) x, (int) y, null);

            } else {
                anim.drawAnimation(g, (int) x, (int) y, 32, 48, 0);
            }
        }

        if(!can_be_damaged) {
            if (velX == 0 && velY == 0) {
                if(timerDamage >= 0 && timerDamage <= 10 || timerDamage >= 33 && timerDamage <= 43|| timerDamage >= 66 && timerDamage<=76) {

                } else{
                    g.drawImage(wizard_image[0], (int) x, (int) y, null);
                }

            } else {
                if(timerDamage >= 0 && timerDamage <= 10 || timerDamage >= 33 && timerDamage <= 43) {

                } else{
                    anim.drawAnimation(g, (int) x, (int) y, 32, 48, 0);
                }
            }
        }



        
    }
    


    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,32,48);
    }
}
