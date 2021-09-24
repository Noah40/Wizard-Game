package main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


import static java.lang.System.out;

public class Game extends Canvas implements Runnable {

    public static int WIDTH = 800, HEIGHT = 600;
    public String title = "Game";
    private Thread thread;
    private boolean isRunning = false;

    //Instances
    public int hpBoss = 3500;
    BufferedImage fireball;
    private Handler handler;
    private KeyInput input;
    private Camera cam;
    private SpriteSheet ss;
    private PortalSheet ps;
    private ExplosionOne ex1;
    private ExplosionTwo ex2;
    private ExplosionThree ex3;
    private  BufferedImage level1 = null;
    private  BufferedImage level2 = null;
    private BufferedImage level3 = null;
    private BufferedImage level5 = null;
    private BufferedImage endScreen = null;
    private BufferedImage floor = null;
    public boolean isBossLevel = false;
    private boolean i = true;
    private boolean level1init = true;
    private boolean level3init = true;
    private boolean level5init = true;
    public boolean isDead;
    public int requiredKills = 10;
    public int levelNum = 0;
    public int kills = 0;
    public int ammo = 100;
    public int hpPlayer = 100;
    public boolean findPlayer = true;


    private void init(){

        handler = new Handler();
        input = new KeyInput();
        cam = new Camera(0,0,handler,this);


        this.addKeyListener(input);



        BufferedImageLoader loader = new BufferedImageLoader();
        level1 = loader.loadImage("/level_1.png");
        level2 = loader.loadImage("/level_2.png");
        level3 = loader.loadImage("/level_3.png");
        level5 = loader.loadImage("/level_5.png");
        BufferedImage sprite_sheet = loader.loadImage("/sprite_sheet.png");
        BufferedImage portal_images = loader.loadImage("/portal.png");
        endScreen = loader.loadImage("/end.png");
        BufferedImage explosion_one = loader.loadImage("/explosion1.png");
        BufferedImage explosion_two = loader.loadImage("/explosion2.png");
        BufferedImage explosion_three = loader.loadImage("/explosion3.png");
         fireball = loader.loadImage("/fireball.png");
        MouseInput m_input = new MouseInput(handler, cam, this, ss, fireball);

        ss = new SpriteSheet(sprite_sheet);
        ps = new PortalSheet(portal_images);
        ex1 = new ExplosionOne(explosion_one);
        ex2 = new ExplosionTwo(explosion_two);
        ex3 = new ExplosionThree(explosion_three);


        floor = ss.grabImage(4,2,32,32);
        this.addMouseListener(m_input);






           m_input.findPlayer();

    }

    public Game(){
        //Constructor
        new Window(WIDTH, HEIGHT, title,this);
        start();

        if(levelNum == 500){
            stop();
        }

        init();

    //Below here

    }

    private synchronized void start(){
        if (isRunning) return;
        isRunning = true;
        thread = new Thread(this);
        thread.start();

    }

    private synchronized void stop(){
            if (!isRunning) return;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isRunning = false;
    }

    //game loop
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(isRunning){
            if(hpPlayer <= 0){
                isDead = true;
            }

            if(isDead){
                for(int i = 0;i <handler.object.size();i +=0) {
                    GameObject tempObject = handler.object.get(i);
                    handler.removeObject(tempObject);
                }
            }

            if(levelNum == 1 && level1init) {
                loadLevel(level1);
                level1init = false;
            }


            if(levelNum == 2 && i) {
                for(int i = 0;i <handler.object.size();i +=0){
                    GameObject tempObject = handler.object.get(i);
                    handler.removeObject(tempObject);
                }
                findPlayer = true;
                loadLevel(level2);
                kills = 0;

                requiredKills = 15;
                ammo = 100;
                hpPlayer = 100;
                i = false;
            }

            if(levelNum == 3 && level3init) {
                for(int i = 0;i <handler.object.size();i +=0){
                    GameObject tempObject = handler.object.get(i);
                    handler.removeObject(tempObject);
                }
                findPlayer = true;
                loadLevel(level3);
                kills = 0;
                requiredKills = 20;
                ammo = 100;
                hpPlayer = 100;
                level3init = false;
            }


            if(levelNum == 4 && level5init) {
                for(int i = 0;i <handler.object.size();i +=0){
                    GameObject tempObject = handler.object.get(i);
                    handler.removeObject(tempObject);
                }
                findPlayer = true;
                loadLevel(level5);
                kills = 0;
                isBossLevel = true;
                ammo = 200;
                hpPlayer = 100;
                level5init = false;
            }



            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                out.println("FPS: " + frames + " TICKS: " + updates);


                frames = 0;
                updates = 0;
            }
        }

    }

    private void tick(){
        //updates the game
for(int j = 0;j<handler.object.size(); j++){
    if(handler.object.get(j).getId() == ID.Player)
            cam.tick(handler.object.get(j));
        }

        handler.tick();

    }

    private void render(){
        //renders the game
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;


        //Most of rendering


        g2d.translate(-cam.getX(),-cam.getY());

        for(int xx = 0; xx<30*72;xx+=32){
            for(int yy = 0; yy< 30*72; yy+=32){
                g.drawImage(floor,xx,yy,null);
            }

        }



        handler.render(g);

        g2d.translate(cam.getX(),cam.getY());


        //Health Bar
        g.setColor(Color.gray);
        g.fillRect(5,5,200,32);
        g.setColor(Color.green);
        g.fillRect(5,5,hpPlayer*2,32);
        g.setColor(Color.black);
        g.drawRect(5,5,200,32);
        g.setColor(Color.white);
        g.drawString( "Health: "+hpPlayer,10,25);
        
//Mana Bar
        g.setColor(Color.gray);
        g.fillRect(5,50,200,32);
        g.setColor(Color.blue);
        if(ammo > 100){
            g.fillRect(5,50,200,32);
        }else{
            g.fillRect(5,50,ammo*2,32);
        }
        g.setColor(Color.black);
        g.drawRect(5,50,200,32);
        g.setColor(Color.white);
        g.drawString("Mana "+ ammo,10,70);
        //Kills bar
        if(!isBossLevel) {
            g.setColor(Color.gray);
            g.fillRect(250, 5, 350, 32);
            g.setColor(Color.red);
            if (kills > requiredKills) {
                g.fillRect(250, 5, 350, 32);
            } else {
                g.fillRect(250, 5, 350 * kills / requiredKills, 32);
            }
            g.setColor(Color.black);
            g.drawRect(5, 50, 200, 32);
            g.setColor(Color.white);
            g.drawString("Kills " + kills + "/" + requiredKills, 255, 25);
        }
        if(isBossLevel){
            g.setColor(Color.gray);
            g.fillRect(250, 5, 350, 32);
            g.setColor(Color.red);

            g.fillRect(250, 5, hpBoss/10, 32);

            g.setColor(Color.black);
            g.drawRect(5, 50, 200, 32);
            g.setColor(Color.white);
            g.drawString("Boss Health "  + hpBoss+"/" + 3500, 255, 25);

        }
        //Death screen
        if(isDead) {

            g.setColor(Color.white);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.drawImage(endScreen, (WIDTH / 2) - 100, 100, 200, 200, null);
        }


        if(levelNum == 0){
            g.setColor(Color.white);
            g.fillRect(0,0,WIDTH,HEIGHT);
            g.setColor(Color.black);
            g.drawString("Title Screen",370,200);

        }


        g.dispose();
        bs.show();

    }

    //Loading level
    private void  loadLevel(BufferedImage image){
        int w = image.getWidth();
        int h = image.getHeight();

    for (int xx = 0; xx < w; xx++) {
        for (int yy = 0; yy < h; yy++) {
            int pixel = image.getRGB(xx, yy);
            int red = (pixel >> 16) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = (pixel) & 0xff;

            if (red == 255 && green == 255) {
                handler.addObject(new Portal(xx * 32, yy * 32, ID.Portal, ps,this));

            }
            if (red == 255 && blue == 0 && green == 0) {
                handler.addObject(new Block((float) xx * 32, (float) yy * 32, ID.Block, ss));
            }
            if (blue == 255 && green == 0 && red == 0) {
                handler.addObject(new Player(xx * 32, yy * 32, ID.Player, input, handler, this, ss));
            }
            if (green == 0 && blue == 0 && red == 200) {
                handler.addObject(new Boss(xx * 32, yy * 32, ID.Boss,ss, handler,this,ex1,ex2,ex3));
            }

            if (green == 255 && blue == 0) {
                handler.addObject(new Enemy(xx * 32, yy * 32, ID.Zombie, handler, ss, this,ex1,ex2,ex3));
            }

            if (green == 255 && blue == 255 && red == 0) {
                handler.addObject(new Crate(xx * 32, yy * 32, ID.Crate, ss));
            }

            if(green == 255 && blue == 200 && red == 200){
                handler.addObject(new FakeBlock(xx*32,yy*32,ID.FakeBlock,ss,handler));
            }


        }
    }
}



    public static void main(String[] args) {

        new Game();
    }

    
}
