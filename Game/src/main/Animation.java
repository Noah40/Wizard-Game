package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {

    private double speed;
    private Enemy enemy;
    private Boss boss;
    private int frames;
    private int index = 0;
    private int count = 0;
    private int lenght;

    int i = 0;

    private BufferedImage[] img = new BufferedImage[30];
    private BufferedImage currentImg;

    //14 frame animation
    public Animation(double speed, BufferedImage[] img,int lenght){
        this.lenght = lenght;
        this.speed = speed;
        for(int i = 0; i < img.length; i++) {
            this.img[i] = img[i];
        }
        frames = img.length;
    }

    public Animation(double speed, BufferedImage[] img,int lenght,Enemy enemy){
        this.lenght = lenght;
        this.speed = speed;
        this.enemy = enemy;
        for(int i = 0; i < img.length; i++) {
            this.img[i] = img[i];
        }
        frames = img.length;
    }

    public Animation(double speed, BufferedImage[] img,int lenght,Boss boss){
        this.lenght = lenght;
        this.speed = speed;
        this.boss = boss;
        for(int i = 0; i < img.length; i++) {
            this.img[i] = img[i];
        }
        frames = img.length;
    }


    public void runAnimation(){
        index++;
            if (index > speed) {
                index = 0;
                if(i < lenght || lenght == 0) {
                    nextFrame();
                    if(lenght != 0) {
                        i++;

                    }
                } else {
                    enemy.deathAnimation = false;
                }

            }


        }


    public void nextFrame(){

        for(int i = 2; i < img.length; i++) {
            if(frames == i) {
                for(int j = 0; j < i; j++) {
                    if(count == j)
                        currentImg = img[j];
                }
                count++;
                if(count > frames)
                    count = 0;
                break;
            }
        }
    }

    public void drawAnimation(Graphics g, double x, double y,int width,int heigth, int offset){
        g.drawImage(currentImg, (int)x - offset, (int)y,width,heigth, null);
    }




    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return count;
    }
    public double getSpeed(){
        return speed;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }
}
