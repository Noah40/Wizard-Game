package main;
public class Camera {

    private float x, y;
    private final Handler handler;
    private GameObject tempPlayer = null;
    private Game game;

    public Camera(float x, float y, Handler handler,Game game) {
        this.x = x;
        this.y = y;
        this.handler = handler;
        this.game = game;

        findPlayer();
    }

    public void findPlayer() {
        for(int i = 0; i < handler.object.size(); i++) {
            if(handler.object.get(i).getId() == ID.Player) {
                tempPlayer = handler.object.get(i);
                break;
            }
        }

        //JOptionPane.showMessageDialog(null, "Could not a find a player");
    }

    public void tick(GameObject object) {

        if(game.levelNum == 1) {
            x += ((object.getX() - x) - 1000 / 2) * 0.05f;
            y += ((object.getY() - y) - 563 / 2) * 0.05f;

            if (x <= 0) x = 0;
            if (x >= 1247) x = 1247;
            if (y <= 0) y = 0;
            if (y >= 563 + 10) y = 563 + 10;
        }
        if(game.levelNum == 2 || game.levelNum == 3 || game.levelNum == 4) {
            x += ((object.getX() - x) - 1000 / 2) * 0.05f;
            y += ((object.getY() - y) - 563 / 2) * 0.05f;

            if (x <= 0) x = 0;
            if (x >= 1247) x = 1247;
            if (y <= 0) y = 0;
            if (y >= 1460 + 10) y = 1460 + 10;
        }


     //   if(tempPlayer != null) {
    //        x = (int) tempPlayer.x - Game.WIDTH/2 + 16;
    //        y = (int) tempPlayer.y - Game.HEIGHT/2 + 16;
    //    }else findPlayer();


    }

    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }



}
