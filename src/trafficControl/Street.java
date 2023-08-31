/*
Oluwaseyi Ariyo
*/
package src.trafficControl;

import java.awt.*;
import java.util.ArrayList;

public abstract class Street {
    int length;
    ArrayList<Car> cars;
    ArrayList<Light> lights;

    public abstract void draw(Graphics g);

    public void addCar(Car c) {
        cars.add(c);
    }

    public ArrayList<Car> getCars() {
        return this.cars;
    }

    public void addLight(Light l) {
        lights.add(l);
    }

    public ArrayList<Light> getLights() {
        return lights;
    }

    
}
