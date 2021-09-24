package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MouseInput extends MouseAdapter {


    private final Handler handler;
    private final Camera cam;
    private GameObject tempPlayer = null;
    private final Game game;
    private final SpriteSheet ss;
    private final BufferedImage fireball;




    public MouseInput(Handler handler, Camera cam, Game game, SpriteSheet ss, BufferedImage fireball) {
        this.handler = handler;
        this.cam = cam;
        this.game = game;
        this.ss = ss;
        this.fireball = fireball;
    }

    public void findPlayer() {
        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ID.Player) {
                tempPlayer = handler.object.get(i);
                break;
            }
        }

        //JOptionPane.showMessageDialog(null, "Could not a find a player");
    }

    public void mousePressed(MouseEvent e) {

        if (game.levelNum == 0) {
            game.levelNum = 1;

        }


            int mx = e.getX();
            int my = e.getY();

            if (tempPlayer != null) {
                if (tempPlayer.getId() == ID.Player && game.ammo >= 1) {
                    if (game.levelNum >= 1 && game.findPlayer) {
                        findPlayer();
                        game.findPlayer = false;
                    }
                    GameObject tempBullet = handler.addObject(new BasicBullet(tempPlayer.x + 16, tempPlayer.y + 16, ID.Bullet, handler, fireball));
                    game.ammo--;

                    float angle = (float) Math.atan2(my - tempPlayer.y - 16 + cam.getY(), mx - tempPlayer.x - 16 + cam.getX());
                    int bulletVel = 10;

                    tempBullet.velX = (float) ((bulletVel) * Math.cos(angle));
                    tempBullet.velY = (float) ((bulletVel) * Math.sin(angle));


                } else findPlayer();

            } else findPlayer();

        }
    }
