package main;

import java.awt.*;
import java.util.ArrayList;

public class Handler {
    public ArrayList<GameObject> object = new ArrayList<>();
    public void tick() {
        for(int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            tempObject.tick();
        }
    }

    public void render(Graphics g) {
        for(int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            tempObject.render(g);
        }
    }


    public GameObject addObject(GameObject tempObject){
        object.add(tempObject);


        return tempObject;
    }

    public GameObject removeObject(GameObject tempObject){
        object.remove(tempObject);

        return tempObject;


    }
}
